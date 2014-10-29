/**
 * MainActivity.java
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
 */



package ask.droid.php;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import android.util.Log;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.TextView;








public class MainActivity extends Activity {
    private Button btnLogin,btnRegister;
    private EditText edtName, edtPassword;
    private String username,password;
    private ProgressDialog pDialog;
    private JSONObject jsnObj=new JSONObject();
    private String index="0";  
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://192.168.1.3:7070/askJson/askjson_register.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message"; 
    private static final String TAG_JSON = "askJSON";
    //felds of database User
     private static final String TAG_NAME = "name"; 
     private static final String TAG_PWD = "password";
     
 
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        btnLogin= (Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        edtName= (EditText)findViewById(R.id.edtUserName);
        edtPassword= (EditText)findViewById(R.id.edtUserPassword);
        
        //The login button click handler
        btnLogin.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
                Intent delItemIntent = new Intent(MainActivity.this, AskJson.class);
                MainActivity.this.startActivity(delItemIntent);
                }
            }); 
        //The register button click handler
        btnRegister.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
                try{
                    jsnObj.put(TAG_NAME,edtName.getText().toString());
                    jsnObj.put(TAG_PWD,edtPassword.getText().toString());
                    new RegisterUser().execute();
                    }
                catch(JSONException e ){
                    e.printStackTrace();
                    }
                }
            }); 
        }
    
    
    /**
    * @Class RegisterUser
    *{
    * Class to transfer registered  user data as an JSON object to the server.   
    * Class is created     when the register button is pressed.
    * 
    *@protected method of class:
    *            @void OnPreExecute();
    *            @String doInBackground(String...);
    *            @void onPostexecute(Sting);
    *}  
    */
    class RegisterUser extends AsyncTask<String, String, String> {
        //Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Register new  User...Please wait");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            }
		
	@Override
	protected String doInBackground(String... args) {
	    int success;
            try {
                // Building Parameters
                 List<NameValuePair> params = new ArrayList<NameValuePair>();
                 params.add(new BasicNameValuePair(TAG_JSON, jsnObj.toString()));
                 Log.d("request!", "starting");
                //The user is posting  data to script
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                // full json response
                Log.d("Register new user  attempt", json.toString());
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("New user registered!", json.toString());              	
                    return json.getString(TAG_MESSAGE);
                    }
                else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                    }
                }
            catch (JSONException e) {
                e.printStackTrace();
                }   
            return null;
            }
        //After completing backgroun`d task Dismiss the progress dialog
        @Override
        protected void onPostExecute(String response) {
            pDialog.dismiss();
            if (response != null){
                ImageToast(response);
                }
            } 
   
        }   
        protected void ImageToast(String response){
            // get your custom_toast.xml ayout
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.image_toast,
            (ViewGroup) findViewById(R.id.custom_toast_layout_id));
            // set a dummy image
            ImageView image = (ImageView) layout.findViewById(R.id.image);
            image.setImageResource(R.drawable.icon);
            // set a message
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText(response);
            // Toast...
            Toast toast = new Toast(getApplicationContext() );
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            }
    }
