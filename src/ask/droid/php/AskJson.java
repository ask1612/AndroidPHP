
/**
 * I do not sleep tonight... I may not ever...
 * AskJson.java
 * Copyright (C) 2014 The Android Open Source Project 
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
 */
package  ask.droid.php;


import android.app.Activity;
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
/**
 * 
 * Class AskJson
 * 
 */
public class AskJson extends Activity implements AsyncTaskListener, OnClickListener {
    private EditText edtName,edtSurname,edtCity,edtStreet,edtBuild,edtFlat;
    private Button btnSave;
    private JSONObject jsnObj;
    private Person person;
    private static final String TAG_MESSAGE = "message"; 
    /**
     * 
     * Gets called when the activity is first created.
     * @param savedInstanceState
     * 
     */
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
    /**
    * 
    * Gets  called  when any button is pressed
    * @param v      View 
    * 
    */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            //Button Save is pressed
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
    /**
     * 
     * Implements the  AsyncTaskListener interface  method   
     * @return           an JSON Object. 
     * 
     */
    public JSONObject onTaskStarted() {
        return jsnObj;
    }
    /**
     * 
     * Implements the  AsyncTaskListener interface  method.
     * @param response      gets a string of an  JSON object
     * 
     */
    public void onTaskFinished(String response) {        
        try {
            jsnObj=new JSONObject(response);
            ImageToast(jsnObj.getString(TAG_MESSAGE));
        } 
        catch (JSONException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * 
     * Shows message
     * @param response    the respose from a server as a string
     * 
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
        
 
 

};