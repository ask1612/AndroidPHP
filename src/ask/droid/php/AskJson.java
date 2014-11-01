
/**
 * I do not sleep tonight... I may not ever...
 * AskJson.java
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
 */
package  ask.droid.php;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View.OnClickListener;

public class AskJson extends Activity implements AsyncTaskListener, OnClickListener {
    private EditText edtName,edtSurname,edtCity,edtStreet,edtBuild,edtFlat;
    private Button btnSave;
    private JSONObject jsnObj;
    private Person person;
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
 * @param savedInstanceState
 * 
 ********************************************************************/
  @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_input);
        edtName = (EditText)findViewById(R.id.edtName);
        edtSurname = (EditText)findViewById(R.id.edtSurname);
        edtCity = (EditText)findViewById(R.id.edtCity);
        edtStreet = (EditText)findViewById(R.id.edtStreet);
        edtBuild = (EditText)findViewById(R.id.edtBuild);
        edtFlat = (EditText)findViewById(R.id.edtFlat);
        
        btnSave=(Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
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
            case R.id.btnSave:
                person.setName(edtName.getText().toString());
                person.setSurname(edtSurname.getText().toString());
                person.getAddress().setCity(edtCity.getText().toString());
                person.getAddress().setStreet(edtStreet.getText().toString());
               // person.getAddress().setBuild(Integer());
                person.getAddress().getFlat();
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
        
 
 

};