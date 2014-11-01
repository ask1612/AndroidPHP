/**
 * I do not sleep tonight... I may not ever...
 * HttpIO.java
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
 * 
 */
package ask.droid.php;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
    protected AsyncTaskListener callback;
    protected Activity activity;
    protected ProgressDialog pDialog;
    protected JSONObject jsnObj;
    protected JSONObject jsnObjHttp;
    private static final String TAG_MESSAGE = "message"; 
    private static final String TAG_JSON = "askJSON";
    private static final String TAG_BTN = "button"; 
    private static final String TAG_URL = "url";
        //felds of database User
    private static final String TAG_NAME = "name"; 
    private static final String TAG_PWD = "password";

    JSONParser jsonParser = new JSONParser();

    //constructor
        public HttpIO(Activity act) {
            this.callback = (AsyncTaskListener)act;
            this.activity=act;
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
            pDialog = new ProgressDialog(this.activity);
        try {
            jsnObj=callback.onTaskStarted();
            jsnObjHttp=new JSONObject();
            jsnObjHttp.put(TAG_NAME,jsnObj.getString(TAG_NAME));
            jsnObjHttp.put(TAG_PWD,jsnObj.getString(TAG_PWD));
            jsnObjHttp.put(TAG_BTN,jsnObj.getString(TAG_BTN));
            pDialog.setMessage(jsnObj.getString(TAG_MESSAGE));
            }
        catch (JSONException ex) {
            Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                params.add(new BasicNameValuePair(TAG_JSON, jsnObjHttp.toString()));
                JSONObject json = jsonParser.makeHttpRequest(jsnObj.getString(TAG_URL),
                "POST", params);
                json.put(TAG_BTN, jsnObjHttp.getString(TAG_BTN));
                 // finish();
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
