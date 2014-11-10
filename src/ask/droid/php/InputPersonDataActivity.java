/**
 * Niemand ist perfekt. I do not sleep tonight... I may not ever
 *
 * InputPersonDataActivity.java Copyright (C) 2014 The Android Open Source Project
 *
 * @author ASK https://github.com/ask1612/AndroidPHP.git
 *
 */
package ask.droid.php;

import android.app.Activity;
import android.content.Intent;
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

/**
 * Class InputPersonDataActivity is an Activity that intendet to input data of any person. This
 * data is written into a MySql database
 */
public class InputPersonDataActivity extends Activity implements AsyncTaskListener, OnClickListener {

    private static Resources res;
    private static final String TAG = "AskJson";//for Log.d
    private EditText edtName, edtSurname, edtCity, edtStreet, edtBuild, edtFlat;
    private Button btnSave;
    private JSONObject jsnObj;
    private final Person person;
    private String username;
    private int count;

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
    private String TAG_HEAD;
    private String TAG_NAME;//tag "name"
    private String TAG_CNT;//tag "name"
    private String TAG_SUCCESS;//tag "success"
   private String TAG_JSON;//tag "success"

    /**
     * constructor
     */
    public InputPersonDataActivity() {
        this.jsnObj = new JSONObject();
        this.person = new Person();
        this.count = 1;
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
        getResourcesStrings();
        Intent intent = getIntent();
        username = intent.getStringExtra(TAG_NAME);
        Log.d(TAG, "onCreate started");
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
                Log.d(TAG, "onClick Button SAVE  started");
                setPersonData();
                break;

        }
        Log.d(TAG, "onClick Button Ended");
        putJSON();
        //Log.d(TAG, jsnArr.toString() + "\n");
        new HttpIO(InputPersonDataActivity.this).execute();//request to http

    }

    /**
     *
     * Implements the AsyncTaskListener interface method
     *
     * @return an JSON Object.
     *
     */
    public JSONObject onTaskStarted() {
        return this.jsnObj;
    }

    /**
     *
     * Implements the AsyncTaskListener interface method.
     *
     * @param response gets a string of an JSON object
     *
     */
    
    public void onTaskFinished(String response) {
        try {
            JSONObject jsnObjResponse = new JSONObject(response);
            this.count = jsnObjResponse.getInt(TAG_SUCCESS);
            if(this.count==1){
                Log.d(TAG_JSON,"InputPersonDataActivity "+ jsnObjResponse.getString(TAG_MESSAGE));
                  Intent personListIntent = new Intent(InputPersonDataActivity.this, ListPersonDataActivity.class);
                personListIntent.putExtra(TAG_JSON, jsnObjResponse.getString(TAG_MESSAGE));
                InputPersonDataActivity.this.startActivity(personListIntent);            
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputPersonDataActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        clearEdt();
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
        this.TAG_HEAD = res.getString(R.string.tag_head);
        this.TAG_NAME = res.getString(R.string.tag_name);
        this.TAG_CNT = res.getString(R.string.tag_cnt);
        this.TAG_SUCCESS = res.getString(R.string.tag_success);
        this.TAG_JSON = res.getString(R.string.tag_json);

    }

    /**
     * Writing to JSON object [ {name:button:message:personname: surname:
     * address:{city:street:build:flat:}} ]
     */
    void putJSON() {
        try {
            jsnObj = new JSONObject();
            //Header
            JSONObject jsnHead = new JSONObject();
            jsnHead.put(TAG_NAME, username);
            jsnHead.put(TAG_BTN, VAL_BTNSAVE);
            jsnHead.put(TAG_MESSAGE, VAL_MESSAGESAVE);
            jsnHead.put(TAG_CNT, count);
            jsnObj.put(TAG_HEAD, jsnHead);
            //Data
            JSONObject jsnData = new JSONObject();
            //Person data
            jsnData.put(TAG_PSNNAME, person.getName());
            jsnData.put(TAG_SURNAME, person.getSurname());
            //Address
            JSONObject jsnAddress = new JSONObject();
            jsnAddress.put(TAG_CITY, person.getAddress().getCity());
            jsnAddress.put(TAG_STREET, person.getAddress().getStreet());
            jsnAddress.put(TAG_BUILD, person.getAddress().getBuild());
            jsnAddress.put(TAG_FLAT, person.getAddress().getFlat());
            jsnData.put(TAG_ADDRESS, jsnAddress);
            jsnObj.put(TAG_DATA, jsnData);
        } catch (JSONException ex) {
            Logger.getLogger(InputPersonDataActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set person data
     */
    void setPersonData() {
        person.setName(edtName.getText().toString());
        person.setSurname(edtSurname.getText().toString());
        person.getAddress().setCity(edtCity.getText().toString());
        person.getAddress().setStreet(edtStreet.getText().toString());
        String s = edtBuild.getText().toString();
        if (s.isEmpty()) {
            person.getAddress().setBuild(0);
        } else {
            person.getAddress().setBuild(Integer.parseInt(s));

        }
        s = edtFlat.getText().toString();
        if (s.isEmpty()) {
            person.getAddress().setFlat(0);
        } else {
            person.getAddress().setFlat(Integer.parseInt(s));
        }

    }

    /**
     * Clear edit view
     */
    void clearEdt() {
        edtName.getText().clear();
        edtSurname.getText().clear();
        edtCity.getText().clear();
        edtStreet.getText().clear();
        edtBuild.getText().clear();
        edtFlat.getText().clear();
    }

};
