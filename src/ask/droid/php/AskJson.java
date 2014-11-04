/**
 * I do not sleep tonight... I may not ever
 *
 * AskJson.java Copyright (C) 2014 The Android Open Source Project
 *
 * @author ASK https://github.com/ask1612/AndroidPHP.git
 *
 */
package ask.droid.php;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/**
 * Class AskJson is an Activity that intendet to input data of any person. This
 * data is written into a  MySql database
 */
public class AskJson extends Activity implements AsyncTaskListener, OnClickListener {
    private static Resources res;
    private static final String TAG = "AskJson"; 
    private EditText edtName, edtSurname, edtCity, edtStreet, edtBuild, edtFlat;
    private Button btnSave;
    private JSONObject jsnObj;
    JSONArray jsnArr ;
   // private Person[] person;
    private int index;
    private static final int SIZE = 2;
    private final  Person person;
  //  List<Person> personList; 
     //JSON
    private String TAG_BTN;//tag "button" 
    private String TAG_PSNNAME; 
    private String TAG_SURNAME; 
    private String TAG_ADDRESS; 
    private String TAG_CITY; 
    private String TAG_STREET; 
    private String TAG_BUILD; 
    private String TAG_FLAT; 
    private String VAL_BTNSAVE; 
    private String VAL_MESSAGESAVE; 
    private String TAG_MESSAGE; 
    private String TAG_DATA; 
    
    /**
     * constructor
     */
    public AskJson() {
        this.person = new Person();
//        this.personList = new ArrayList<Person>();
        this.jsnArr =new JSONArray();
        this.index=0;
    }

    /**
     *
     * Gets called when the activity is first created.
     *
     * @param savedInstanceState
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_input);
        edtName = (EditText) findViewById(R.id.edtName);
        edtSurname = (EditText) findViewById(R.id.edtSurname);
        edtCity = (EditText) findViewById(R.id.edtCity);
        edtStreet = (EditText) findViewById(R.id.edtStreet);
        edtBuild = (EditText) findViewById(R.id.edtBuild);
        edtFlat = (EditText) findViewById(R.id.edtFlat);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        Log.d( TAG, "onCreate started" ); 
        getResourcesStrings();
   }

    /**
     *
     * Gets called when a button <Save> is pressed
     *
     * @param v View
     *
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
 //Button Save is pressed 
             case R.id.btnSave: 
                 Log.d( TAG, "onClick Button started" ); 
                 person.setName(edtName.getText().toString()); 
                 person.setSurname(edtSurname.getText().toString()); 
                 person.getAddress().setCity(edtCity.getText().toString()); 
                 person.getAddress().setStreet(edtStreet.getText().toString());
                 String s=edtBuild.getText().toString();
                 if(s.isEmpty()){
                 person.getAddress().setBuild(0); 
                 }
                 else {
                 person.getAddress().setBuild(Integer.parseInt(s)); 
                     
                 }
                 s=edtFlat.getText().toString();
                 if(s.isEmpty()){
                 person.getAddress().setFlat(0); 
                 }
                 else {
                 person.getAddress().setFlat(Integer.parseInt(s)); 
                 }
                 
//                 person.getAddress().setFlat(Integer.parseInt(edtFlat.getText().toString())); 
                 Log.d( TAG, "Save  button pressed."+ 
                      " Person name   :"+person.getName()+"\n"+  
                      " Person surname: "+person.getSurname()+"\n"+ 
                      " Person address __________________________\n" +    
                      " City    :"+person.getAddress().getCity()+"\n"+  
                      " Street  :"+person.getAddress().getStreet()+"\n"+  
                      " Build   :"+Integer.toString(person.getAddress().getBuild())+"\n"+  
                      " Flat    :"+Integer.toString(person.getAddress().getFlat())  
                 );  
                 break; 


        }
        Log.d( TAG, "onClick Button Ended" ); 
        putJSONArr();
        index++;
        if (index == SIZE) {
            try {
                //attach to the JCSON object  tags <button> and <message> 
                jsnObj.put(TAG_BTN, VAL_BTNSAVE);
                jsnObj.put(TAG_MESSAGE, VAL_MESSAGESAVE);
                //attach tag <data>
                jsnObj.put(TAG_DATA, jsnArr);
                this.jsnArr = new JSONArray();
                index = 0;
                Log.d(TAG, jsnObj.toString());
                new HttpIO(AskJson.this).execute();//request to http
            } catch (JSONException ex) {
                Logger.getLogger(AskJson.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     *
     * Implements the AsyncTaskListener interface method
     *
     * @return an JSON Object.
     *
     */
    public JSONObject onTaskStarted() {
        return jsnObj;
    }

    /**
     *
     * Implements the AsyncTaskListener interface method.
     *
     * @param response gets a string of an JSON object
     *
     */
    public void onTaskFinished(String response) {
        jsnObj = new JSONObject();
    }

   
    
    /**
     *
     * Get strings from resources
     *
     */
    public void getResourcesStrings() {
        res = getResources();
        this.TAG_PSNNAME = res.getString(R.string.tag_psnname);
        this.TAG_SURNAME = res.getString(R.string.tag_surname);
        this.TAG_ADDRESS = res.getString(R.string.tag_address);
        this.TAG_CITY = res.getString(R.string.tag_city);
        this.TAG_STREET = res.getString(R.string.tag_street);
        this.TAG_BUILD = res.getString(R.string.tag_build);
        this.TAG_FLAT = res.getString(R.string.tag_flat);
        this.TAG_BTN = res.getString(R.string.tag_btn);
        this.VAL_BTNSAVE = res.getString(R.string.val_btnsave);
        this.TAG_MESSAGE = res.getString(R.string.tag_message);
        this.VAL_MESSAGESAVE = res.getString(R.string.val_messagesave);
        this.TAG_DATA = res.getString(R.string.tag_data);

    }
    /**
     * Writing to JSON Array [ {personname: surname:
     * address:{city:street:build:flat:}} ]
     */
    void putJSONArr() {
        try {
            JSONObject jsnData = new JSONObject();
            jsnData.put(TAG_PSNNAME, person.getName());
            jsnData.put(TAG_SURNAME, person.getSurname());
            
            //Address
            JSONObject jsnAddress = new JSONObject();
            jsnAddress.put(TAG_CITY, person.getAddress().getCity());
            jsnAddress.put(TAG_STREET, person.getAddress().getStreet());
            jsnAddress.put(TAG_BUILD, person.getAddress().getBuild());
            jsnAddress.put(TAG_FLAT, person.getAddress().getFlat());
            
            jsnData.put(TAG_ADDRESS, jsnAddress);
            //put data into the JSONarray
            jsnArr.put(jsnData);
        } catch (JSONException ex) {
            Logger.getLogger(AskJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

};
