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
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
    private static  Resources res;
    protected AsyncTaskListener callback;
    protected Activity activity;
    protected ProgressDialog pDialog;
    protected JSONObject jsnObj;
    
    //JSON
    private  String TAG_MESSAGE;//message 
    private  String VAL_URL;//url
    private  String TAG_JSON;
 
    JSONParser jsonParser = new JSONParser();

/********************************************************************
 * 
 * constructor
 * 
 ********************************************************************/
        public HttpIO(Activity act) {
            this.activity=act;
            this.callback = (AsyncTaskListener)act;
            }
/********************************************************************
 * 
 * getResourcesString()
 * 
 ********************************************************************/
       void getResourcesStrings(){
           res = activity.getResources();
           this.VAL_URL=res.getString(R.string.val_url);
           this.TAG_MESSAGE=res.getString(R.string.tag_message);
           this.TAG_JSON=res.getString(R.string.tag_json);
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
            getResourcesStrings();
            jsnObj=new JSONObject();
            jsnObj=callback.onTaskStarted();
            pDialog = new ProgressDialog(this.activity);
            try {
                pDialog.setMessage(jsnObj.getString(TAG_MESSAGE));
                }
            catch (JSONException ex) {
                Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            jsnObj.remove(TAG_MESSAGE);
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
                 List<NameValuePair> params = new ArrayList<NameValuePair>(); 
                 params.add(new BasicNameValuePair(TAG_JSON, jsnObj.toString())); 
                 JSONObject json = jsonParser.makeHttpRequest(VAL_URL,"POST",params); 
                  // finish(); 
                 return json.toString(); 
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
            try {
                jsnObj=new JSONObject(response);
                imageToast(jsnObj.getString(TAG_MESSAGE));
                callback.onTaskFinished(response);
                }
            catch (JSONException ex) {
                Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
 /********************************************************************
 * 
 * ImageToast()
 * 
 * @param response
 * 
 ********************************************************************/
        protected void imageToast(String response){
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
            Toast toast = new Toast(activity.getApplicationContext() );
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            }
   
     }
