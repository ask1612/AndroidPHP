/**
 * I do not sleep tonight... I may not ever...
 *
 * HttpIO.java Copyright (C) 2014 The Android Open Source Project
 *
 * @author ASK https://github.com/ask1612/AndroidPHP.git
 */
package ask.droid.php;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to transfer user data as an JSON object to a http server. It works as
 * an asynchronous task, gets created when the register or login button is
 * pressed.
 */
class HttpIO extends AsyncTask<String, String, String> {
  
    private static final String TAG = "AskJson";
    private final Activity activity;
    private final AsyncTaskListener callback;
    private ProgressDialog pDialog;
    private static Resources res;// 
    private JSONObject jsnObj;//
    private String TAG_MESSAGE;//message 
    private String VAL_URL;//url
    private String TAG_JSON;//askJSON
    private final JSONParser jsonParser;

    ;
    /**
     * 
     * Gonstructor  gets   Activity  as a parameter,assigns the value of this 
     * parameter  to  its member  variable activity. It  castings the given value
     * Activity to  the AsyncTaskListener interface and assigns it to the member
     * variable callback. It creates JSONparser object.
     * 
     * @param   act    activity created this object and  invoced execute() 
     *                 method 
     */
    public HttpIO(Activity act) {
        this.jsonParser = new JSONParser();
        this.activity = act;
        this.callback = (AsyncTaskListener) act;
        //jsonParser = new JSONParser();
    }

    /**
     *
     * Gets strings from resources.
     *
     */
    void getResourcesStrings() {
        res = activity.getResources();
        this.VAL_URL = res.getString(R.string.val_url);
        this.TAG_MESSAGE = res.getString(R.string.tag_message);
        this.TAG_JSON = res.getString(R.string.tag_json);
    }

    /**
     *
     * Gets called before starting the background thread, gets an JSON object
     * from an activity thread via the method onTaskStarted of AsyncTaskListener
     * interface , shows Progress Dialog
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        getResourcesStrings();//get strings from resources
        jsnObj = new JSONObject();
        jsnObj = callback.onTaskStarted();//get JSON object
        pDialog = new ProgressDialog(this.activity);
        try {
            pDialog.setMessage(jsnObj.getString(TAG_MESSAGE));
        } catch (JSONException ex) {
            Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();//shows progress dialog
        jsnObj.remove(TAG_MESSAGE);//do not send message to the server
        Log.d(TAG, jsnObj.toString());
    }

    /**
     *
     * Gets called on the background main thread when execute() is invoked on an
     * instance of AsyncTask HttpIO, gets an JSON object as the parameter ,
     * makes the request to a http server returns the JSON object as an string
     *
     * @param args null
     * @return JSON object converted to a string
     *
     */
    @Override
    protected String doInBackground(String... args) {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_JSON, jsnObj.toString()));
            jsnObj = jsonParser.makeHttpRequest(VAL_URL, "POST", params);
            // finish();
        } catch (IOException ex) {
            Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsnObj.toString();
    }

    /**
     *
     * Gets called when doInBackground() is finished, gets an JSON object as a
     * string, closes the progress dialog, shows a message from the http server,
     * sends the JSON object as a string to the calling activity via the
     * AsyncTaskListener interface
     *
     * @param response an JSON object converted to the string
     *
     */
    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        // Dismiss the progress dialog
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (response != null) {
            try {
                jsnObj = new JSONObject(response);
                imageToast(jsnObj.getString(TAG_MESSAGE));
                callback.onTaskFinished(response);
            } catch (JSONException ex) {
                Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Shows messges.
     *
     * @param string
     */
    protected void imageToast(String response) {
        // get your image_toast.xml ayout
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.image_toast,
                (ViewGroup) activity.findViewById(R.id.layoutImageToast));
        // set an  image
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.icon);
        // set a message
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(response);
        // Toast...
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
