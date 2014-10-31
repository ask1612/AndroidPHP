/**
 * I do not sleep tonight... I may not ever...
 * MainActivity.java
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
 * 
 */



package ask.droid.php;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
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
    private String url,msg;
    private ProgressDialog pDialog;
    private JSONObject jsnObj;
    private String index="0";  
    JSONParser jsonParser = new JSONParser();
    private static final String REG_URL = "http://192.168.1.3:7070/askJson/askjson_register.php";
    private static final String LOG_URL = "http://192.168.1.3:7070/askJson/askjson_login.php";
    private static final String REG_MESSAGE = "Register new  User...Please wait";
    private static final String LOG_MESSAGE = "Login...Please wait";
    private static final String TAG_MESSAGE = "message"; 
    private static final String TAG_JSON = "askJSON";
    private static final String TAG_SUCCESS = "success";
    int success=0;
     AsyncTask.Status status;
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
        success=0;
        //The login button click handler
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    jsnObj=new JSONObject();
                    jsnObj.put(TAG_NAME,edtName.getText().toString());
                    jsnObj.put(TAG_PWD,edtPassword.getText().toString());
                    url=LOG_URL;msg=LOG_MESSAGE;
                    //new LogRegUser().execute();
                    LogRegUser astask=new LogRegUser();
                    astask.execute();
                    while(astask.getExecuted()){};
                    }
                catch(JSONException e ){
                    e.printStackTrace();
                    }
                if(success==1){
                    Intent delItemIntent = new Intent(MainActivity.this, AskJson.class);
                    MainActivity.this.startActivity(delItemIntent);
                    }
                }
            }); 
        //The register button click handler
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    jsnObj=new JSONObject();
                    jsnObj.put(TAG_NAME,edtName.getText().toString());
                    jsnObj.put(TAG_PWD,edtPassword.getText().toString());
                    url=REG_URL;msg=REG_MESSAGE;
                    new LogRegUser().execute();
                    }
                catch(JSONException e ){
                    e.printStackTrace();
                    }
                }
            }); 
        }
    
    
    /**
    *
    * Class to transfer    user data as an JSON object to the server.   
    * Class is created     when the register or login button is pressed.
    * 
    *@protected method of class:
    *            @void OnPreExecute();
    *            @String doInBackground(String...);
    *            @void onPostexecute(Sting);
    *  
    */
    
    class LogRegUser extends AsyncTask<String, String, String> {
        private boolean executed=true;
        public boolean getExecuted(){
         return executed;   
        };
        /**
         *Before starting background thread Show Progress Dialog 
         * Preexecuted function
         */
        @Override
        protected void onPreExecute() {
            executed=true;
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(msg);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            }
        
        /**
         * Function executed in background of  the main thread
         * @param args
         * @return String
         */
	@Override
	protected String doInBackground(String... args) {
            try {
                 List<NameValuePair> params = new ArrayList<NameValuePair>();
                 params.add(new BasicNameValuePair(TAG_JSON, jsnObj.toString()));
                JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
                 // finish();
                success = json.getInt(TAG_SUCCESS);
                executed=false;
                return json.getString(TAG_MESSAGE);
                }
            catch (Exception e) {
                e.printStackTrace();
                } 
            executed=false;
            return null;
            }
        
        /**
         * Post executed function
         * @param response 
         */
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                }
            if (response != null){
                ImageToast(response);
                }
            } 
    
        }
    
        /**
         * New Toast function
         * @param response 
         */
        protected void ImageToast(String response){
            // get your image_toast.xml ayout
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.image_toast,
            (ViewGroup) findViewById(R.id.layoutImageToast));
            // set an  image
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
