/**
 * I do not sleep tonight... I may not ever...
 *
 * MainActivity.java Copyright (C) 2014 The Android Open Source Project
 *
 * @author ASK https://github.com/ask1612/AndroidPHP.git
 */
package ask.droid.php;

import org.json.JSONException;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * MainActivity is the first activity that appears when the application is
 * launched. This activity consists of 3 text labels, 2 edit views and 2
 * buttons. MainActivity is intended to register a new user or login with a
 * registered user to a MySql database. When the button <Register> or <Login>
 * is pressed the application creates an JSON object,reads data from edit views
 * and writes it into this JSON object , creates an async task object HttpIO and
 * invokes its execute() method . At this moment MainActivity launches the async
 * task as a background thread. This background thread interacts with the
 * MainActivity thread by the AsyncTaskListener interface. MainActivity thread
 * transfers the JSON object to the background thread via the OnTaskStarted()
 * method of this interface. Async task sends this data to a http server. The
 * http server attemps to connect to the MySql server. When the connection is
 * success data is written or read in/out the database. When the database I/O
 * operations are ended the http server returns back to the Android application
 * a block of information as a string of an JSON object . This block consists of
 * 2 fields:<success> and  <message>. The field  <success>
 * indicates whether the I/O operation was successful or no . If the field
 * <success> is 1 the database I/O operation was successful otherwise no.
 * Depending on this result the server generates a message and returns it to the
 * async task. The async task transfers it via interface method onTaskFinished()
 * to the MainActivity thread.Depending on this information MainActivity starts
 * or does not a new Activity to enter data of a person.
 *
 */
public class MainActivity extends Activity implements AsyncTaskListener, OnClickListener {

    private static Resources res;
    private Button btnLogin, btnRegister;
    private EditText edtName, edtPassword;
    private String username, password;

    //JSON
    private String TAG_BTN;//tag "button" 
    private String TAG_HEAD;//tag "button" 
    private String TAG_DATA;//tag "button" 
    private String VAL_BTNLOG;//value "login" 
    private String VAL_BTNREG;//value "register"
    private String TAG_MESSAGE;//tag "message" 
    private String VAL_MESSAGELOG; //value "Login...Please wait" 
    private String VAL_MESSAGEREG;//value "Register new  User...Please wait" 
    private String TAG_SUCCESS;//tag "success"
    private String TAG_NAME;//tag "name"
    private String TAG_PWD;//tag "password"
    private static JSONObject jsnObj;

    /**
     *
     * Gets called when the activity is first created.
     *
     * @param icicle
     *
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtUserPassword);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        getResourcesStrings();
    }

    /**
     * Method is invoked when one of the buttons <Register> or <Login> pressed,
     * creates an JSON object and pass it to the async task via the
     * AsyncTaskListener interface .
     *
     * @param v View
     */
    @Override
    public void onClick(View v) {
        username = edtName.getText().toString();
        password = edtPassword.getText().toString();
        switch (v.getId()) {
            //Button Login  pressed
            case R.id.btnLogin:
                putJSON(VAL_BTNLOG,VAL_MESSAGELOG);
                break;
            //Button Register pressed    
            case R.id.btnRegister:
                putJSON(VAL_BTNREG,VAL_MESSAGEREG);
                break;
        }
        new HttpIO(MainActivity.this).execute();
    }

    /**
     *
     * Implements the AsyncTaskListener interface method
     *
     * @return: outputs an JSON Object.
     *
     */
    public JSONObject onTaskStarted() {
        return jsnObj;
    }

    /**
     *
     * Implements the AsyncTaskListener interface method
     *
     * @param response: inputs a string of an JSON object
     *
     */
    public void onTaskFinished(String response) {
        try {
            JSONObject jsnObjResponse = new JSONObject(response);
            if (jsnObjResponse.getInt(TAG_SUCCESS) == 1
                    && jsnObj.getJSONObject(TAG_HEAD).getString(TAG_BTN).compareTo(VAL_BTNLOG) == 0) {
                edtName.getText().clear();
                edtPassword.getText().clear();
                Intent personIntent = new Intent(MainActivity.this, AskJson.class);
                personIntent.putExtra(TAG_NAME, username);
                MainActivity.this.startActivity(personIntent);
            }
        } catch (JSONException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * Get strings from resources
     *
     */
    public void getResourcesStrings() {
        res = getResources();
        this.TAG_HEAD = res.getString(R.string.tag_head);
        this.TAG_DATA = res.getString(R.string.tag_data);
        this.TAG_SUCCESS = res.getString(R.string.tag_success);
        this.VAL_BTNLOG = res.getString(R.string.val_btnlog);
        this.VAL_BTNREG = res.getString(R.string.val_btnreg);
        this.TAG_BTN = res.getString(R.string.tag_btn);
        this.TAG_NAME = res.getString(R.string.tag_name);
        this.TAG_PWD = res.getString(R.string.tag_pwd);
        this.TAG_MESSAGE = res.getString(R.string.tag_message);
        this.VAL_MESSAGELOG = res.getString(R.string.val_messagelog);
        this.VAL_MESSAGEREG = res.getString(R.string.val_messagereg);
    }
     /**
     * Writing to JSON object 
     */
    void putJSON(String val_btn,String val_message) {
        try {
            jsnObj = new JSONObject();
            //Header
            JSONObject jsnHead = new JSONObject();
            jsnHead.put(TAG_BTN, val_btn);
            jsnHead.put(TAG_MESSAGE, val_message);
            jsnObj.put(TAG_HEAD, jsnHead);
            //Data
            JSONObject jsnData = new JSONObject();
            jsnData.put(TAG_NAME, username);
            jsnData.put(TAG_PWD, password);
            jsnObj.put(TAG_DATA, jsnData);
        } catch (JSONException ex) {
            Logger.getLogger(AskJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
