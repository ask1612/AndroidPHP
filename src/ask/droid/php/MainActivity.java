/**
 * I do not sleep tonight... I may not ever...
 * MainActivity.java
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
 * 
 */



package ask.droid.php;

import org.json.JSONException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
    private JSONObject jsnObj;
    private static final String URL = "http://192.168.1.3:7070/askJson/askjson_input.php";
    private static final String REG_MESSAGE = "Register new  User...Please wait";
    private static final String LOG_MESSAGE = "Login...Please wait";
    private static final String TAG_MESSAGE = "message"; 
    private static final String TAG_SUCCESS = "success";
    private static final String BTN_LOG = "login"; 
    private static final String BTN_REG = "register"; 
    private static final String TAG_BTN = "button"; 
    private static final String TAG_URL = "url"; 
    //felds of database User
    private static final String TAG_NAME = "name"; 
    private static final String TAG_PWD = "password";
   
 
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
                    jsnObj.put(TAG_BTN,BTN_LOG);
                    jsnObj.put(TAG_MESSAGE, LOG_MESSAGE);
                    jsnObj.put(TAG_URL, URL);
                    HttpIO astask= new HttpIO(MainActivity.this);
                    astask.execute();// = new JsonViaHttpIO();
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
                    jsnObj.put(TAG_BTN,BTN_REG);
                    jsnObj.put(TAG_MESSAGE, REG_MESSAGE);
                    jsnObj.put(TAG_URL, URL);
                    HttpIO astask= new HttpIO(MainActivity.this);
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

    public JSONObject onTaskStarted() {
        return jsnObj;
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
            ImageToast(jsnObj.getString(TAG_MESSAGE));
    //      ImageToast(response);
            if(jsnObj.getInt(TAG_SUCCESS)==1&&jsnObj.getString(TAG_BTN).compareTo(BTN_LOG)==0){
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
