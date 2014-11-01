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
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.logging.Level;
import java.util.logging.Logger;

/********************************************************************
 * 
 * Main  activity.
 * 
 ********************************************************************/
public class MainActivity extends Activity implements AsyncTaskListener, OnClickListener{
    
    private Button btnLogin,btnRegister;
    private EditText edtName, edtPassword;
    private String url,msg;
    private ProgressDialog pDialog;
    private JSONObject jsnObj;
    private String index="0";  
    JSONParser jsonParser = new JSONParser();
    private static final String URL = "http://192.168.1.3:7070/askJson/askjson_input.php";
    private static final String REG_MESSAGE = "Register new  User...Please wait";
    private static final String LOG_MESSAGE = "Login...Please wait";
    private static final String TAG_MESSAGE = "message"; 
    private static final String TAG_JSON = "askJSON";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_LOG = "login"; 
    private static final String TAG_REG = "register"; 
    private static final String TAG_BTN = "button"; 
    //felds of database User
    private static final String TAG_NAME = "name"; 
    private static final String TAG_PWD = "password";
    int success=0;
    HttpIO mHttpIO;
   
 
/********************************************************************
 * 
 * onCreate()
 * gets called when the activity is first created.
 * @param icicle
 * 
 ********************************************************************/
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
        btnLogin= (Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        edtName= (EditText)findViewById(R.id.edtUserName);
        edtPassword= (EditText)findViewById(R.id.edtUserPassword);
    
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        }
    
/********************************************************************
 * 
 * onClick()
 * gets  called  when any button is pressed
 * @param v
 * 
 ********************************************************************/
    
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            //Button Login is pressed
            case R.id.btnLogin:
                try{
                    jsnObj=new JSONObject();
                    jsnObj.put(TAG_NAME,edtName.getText().toString());
                    jsnObj.put(TAG_PWD,edtPassword.getText().toString());
                    jsnObj.put(TAG_BTN,TAG_LOG);
                    msg=LOG_MESSAGE;
                    HttpIO astask= new HttpIO(this);
                    astask.execute();// = new JsonViaHttpIO();
                   // while(astask.isAsTaskRunning()){};//Wait for the end of the async task
                    }
                catch(JSONException e ){
                    e.printStackTrace();
                    }
                break;
            //Button Register pressed    
            case R.id.btnRegister:
                
                try{
                    jsnObj=new JSONObject();
                    jsnObj.put(TAG_NAME,edtName.getText().toString());
                    jsnObj.put(TAG_PWD,edtPassword.getText().toString());
                    jsnObj.put(TAG_BTN,TAG_REG);
                     msg=REG_MESSAGE;
                    HttpIO astask= new HttpIO(this);
                    astask.execute();// = new JsonViaHttpIO();
                    }
                catch(JSONException e ){
                    e.printStackTrace();
                    }
                
                break;
            
            }
        }
/********************************************************************
 * 
 * interface AsyncTaskListener
 * onTaskStarted()
 * 
 ********************************************************************/

    public void onTaskStarted() {
        }
/********************************************************************
 * 
 * interface AsyncTaskListener
 * onTaskFinished()
 * 
 ********************************************************************/

    public void onTaskFinished(String response) {        
        try {
            jsnObj=new JSONObject(response);
            success = jsnObj.getInt(TAG_SUCCESS);
            ImageToast(jsnObj.getString(TAG_MESSAGE));
    //      ImageToast(response);
            if(success==1&&jsnObj.getString(TAG_BTN).compareTo(TAG_LOG)==0){
                Intent personIntent = new Intent(MainActivity.this, AskJson.class);
                MainActivity.this.startActivity(personIntent);
            }
        } 
        catch (JSONException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

/********************************************************************
 *
 * HttpIO{}
 * Class to transfer    user data as an JSON object to the server,   
 * gets created     when the register or login button is pressed.
 * @protected method of class:
 *            @void OnPreExecute();
 *            @String doInBackground(String...);
 *            @void onPostexecute(Sting);
 *            @AsyncTaskListener listener;
 *  
 ********************************************************************/
   
    class HttpIO extends AsyncTask<String, String, String> {
        private AsyncTaskListener callback;
        //constructor
        public HttpIO(MainActivity act) {
            this.callback = (AsyncTaskListener)act;
            
            }
   
/********************************************************************
 * 
 * onPreExecute()
 * gets  called before starting the background thread.
 * Show Progress Dialog 
 * 
 ********************************************************************/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(msg);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            }

        
/********************************************************************
 * 
 * doInBackground()
 * gets called on the background  main thread when execute() is
 * invoked on an instance of AysncTask.
 * 
 * @param args
 * @return String
 * 
 ********************************************************************/
	@Override
	protected String doInBackground(String... args) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(TAG_JSON, jsnObj.toString()));
                String btnname=jsnObj.getString(TAG_BTN);
                JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
                 // finish();
                json.put(TAG_BTN,btnname);
                return json.toString();
                }
            catch (Exception e) {
                e.printStackTrace();
                } 
            return null;
            }
        
/********************************************************************
 * 
 * onPostExecute()
 * gets called when doInBackground() is finished 
 * @param response
 * 
 ********************************************************************/
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                }
            if (response != null){
                callback.onTaskFinished(response);
                }
            } 
    
        }
    
/********************************************************************
 * 
 * ImageToast()
 * 
 * @param response
 * 
 ********************************************************************/
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
