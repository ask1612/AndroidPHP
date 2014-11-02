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
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/********************************************************************
 * 
 * Main  activity.
 * 
 * 
 ********************************************************************/
public class MainActivity extends Activity implements AsyncTaskListener, OnClickListener{
    private static  Resources res;
    private Button btnLogin,btnRegister;
    private EditText edtName, edtPassword;
    private static JSONObject jsnObj;
    
    //JSON
    private  String TAG_BTN;//tag "button" 
    private  String VAL_BTNLOG;//value "login" 
    private  String VAL_BTNREG;//value "register"
    private  String TAG_MESSAGE;//tag "message" 
    private  String VAL_MESSAGELOG; //value "Login...Please wait" 
    private  String VAL_MESSAGEREG;//value "Register new  User...Please wait" 
    private  String TAG_SUCCESS;//tag "success"
    private  String TAG_NAME;//tag "name"
    private  String TAG_PWD;//tag "password"
    
 
/********************************************************************
 * 
 * onCreate()
 * gets called when the activity is first  created.
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
        getResourcesStrings();
      }
    
/********************************************************************
 * 
 * onClick()
 * Method is invoked when pressed one of the buttons "Register" or "Login",
 * creates the JSON object and pass it to the async task HttpIO via 
 * the AsyncTaskListener interface method onTaskStarted(). 
 * @param v
 * 
 ********************************************************************/
    
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            //Button Login  pressed
            case R.id.btnLogin:
                try{
                    jsnObj=new JSONObject();
                    jsnObj.put(TAG_NAME,edtName.getText().toString());
                    jsnObj.put(TAG_PWD,edtPassword.getText().toString());
                    jsnObj.put(TAG_BTN,VAL_BTNLOG);
                    jsnObj.put(TAG_MESSAGE,VAL_MESSAGELOG);
                    }
                catch (JSONException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
            //Button Register pressed    
            case R.id.btnRegister:
                try {
                    jsnObj=new JSONObject();
                    jsnObj.put(TAG_NAME,edtName.getText().toString());
                    jsnObj.put(TAG_PWD,edtPassword.getText().toString());
                    jsnObj.put(TAG_BTN,VAL_BTNREG);
                    jsnObj.put(TAG_MESSAGE,VAL_MESSAGEREG);
                    }
                catch (JSONException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
            }
        new HttpIO(MainActivity.this).execute();
        }

/********************************************************************
 * 
 * onTaskStarted()
 * implements the  AsyncTaskListener interface  method   
 * @return: outputs an JSON Object. 
 * 
 ********************************************************************/
    public JSONObject onTaskStarted() {
        return jsnObj;
        }

/********************************************************************
 * 
 * onTaskFinished()
 * implements the  AsyncTaskListener interface  method   
 * @param response:  inputs a string of an  JSON object
 * 
 ********************************************************************/
    public void onTaskFinished(String response) {        
        try {
            JSONObject jsnObjResponse=new JSONObject(response);
            if(jsnObjResponse.getInt(TAG_SUCCESS)==1
                &&jsnObj.getString(TAG_BTN).compareTo(VAL_BTNLOG)==0){
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
 * getResourcesStrings()
 * 
 ********************************************************************/
    public  void getResourcesStrings(){
        res = getResources();
       //Getting  strings from resources
       this.TAG_SUCCESS=res.getString(R.string.tag_success);
       this.VAL_BTNLOG=res.getString(R.string.val_btnlog);
       this.VAL_BTNREG=res.getString(R.string.val_btnreg);
       this.TAG_BTN=res.getString(R.string.tag_btn);
       this.TAG_NAME=res.getString(R.string.tag_name);
       this.TAG_PWD=res.getString(R.string.tag_pwd);
       this.TAG_MESSAGE=res.getString(R.string.tag_message);
       this.VAL_MESSAGELOG=res.getString(R.string.val_messagelog);
       this.VAL_MESSAGEREG=res.getString(R.string.val_messagereg);
        
        }
    }
