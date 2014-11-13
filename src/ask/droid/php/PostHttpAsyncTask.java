/**
 * Niemand ist perfekt. I do not sleep tonight... I may not ever...
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to exchange data between the Android app and the http server. It works as
 * an asynchronous task.
 * 
 */
class PostHttpAsyncTask extends AsyncTask<String, String, String> {

    private static final String TAG = "AskJson";
    private final Activity activity;
    private final AsyncTaskListener callback;
    private ProgressDialog pDialog;
    private static Resources res;// 
    private JSONObject jsnObj;//
    private String TAG_MESSAGE;//message 
    private String TAG_HEAD;//message 
    private String VAL_URL;//url
    private String TAG_JSON;//askJSON

    ;
    /**
     * 
     * Gonstructor  gets   Activity  as a parameter,assigns the value of this 
     * parameter  to  its member  variable activity. It  casts the given value
     * Activity to  the AsyncTaskListener interface and assigns it to the member
     * variable callback. 
     * 
     * @param   act    activity created this object and  invoced execute() 
     *                 method 
     */
    public PostHttpAsyncTask(Activity act) {
        this.activity = act;
        this.callback = (AsyncTaskListener) act;
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
        this.TAG_HEAD = res.getString(R.string.tag_head);
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
            pDialog.setMessage(jsnObj.getJSONObject(TAG_HEAD).getString(TAG_MESSAGE));
        } catch (JSONException ex) {
            Logger.getLogger(PostHttpAsyncTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();//shows progress dialog
        try {
            //do not send this dialog message to the server
            jsnObj.getJSONObject(TAG_HEAD).remove(TAG_MESSAGE);
        } catch (JSONException ex) {
            Logger.getLogger(PostHttpAsyncTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        Log.d(TAG, jsnObj.toString());
    }

    /**
     *
     * Gets called on the background main thread when execute() is invoked on an
 instance of AsyncTask PostHttpAsyncTask, gets an JSON object as the parameter ,
 makes the request to a http server returns the JSON object as an string
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
            jsnObj = this.doHttpRequest(VAL_URL, params);
            // finish();
        } catch (IOException ex) {
            Logger.getLogger(PostHttpAsyncTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(PostHttpAsyncTask.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PostHttpAsyncTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This function makes a http request by POST method, passes to a given
     * server parameters TAG_JSON and JSON object as string , gets a string from
     * http server, converts it to a JSON object and return the JSON object. The
     * url argument must specify an absolute {@link URL}.
     *
     * @param url an absolute URL
     * @param params tag JSON  and JSON object as string
     * @return JSON object
     */
    public JSONObject doHttpRequest(String url,
            List<NameValuePair> params) throws IOException, JSONException {
        InputStream inputstream;
        HttpResponse httpResponse;
            // Making HTTP request
        //DefaultHttpClient is  used to make remote HTTP requests.
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //Connecting via POST method
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            inputstream = httpEntity.getContent();
                //InputStream class is used to read data from a  network.
            //BufferedReader class  wraps the input stream reader
            //and buffers the input. 
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputstream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            inputstream.close();
            jsnObj = new JSONObject(sb.toString());
            // return JSON object
            return jsnObj;
        }
        return null;
    }

    /**
     * Shows messges.
     *
     * @param string
     */
    protected void imageToast(String response) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.image_toast,
                (ViewGroup) activity.findViewById(R.id.layoutImageToast));
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.icon);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(response);
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
