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
import java.io.IOException;
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
 * Class to transfer    user data as an JSON object to a http server,
 * works as an asynchronous task,  
 * gets created     when the register or login button is pressed.
 *
 *--------------------------------------------------------------------
 * @data
 *--------------------------------------------------------------------
 *@Activity                 ;point to activity initialized by the constructor,   
 *                          ;takes the value of activity  that makes this
 *                          ;async task executed       
 *@AsyncTaskListener        ;point to interface initialized by the constructor,
 *                          ;takes the value of AsyncTaskListener   interface
 *                          ;
 *@ProgressDialog           ;progress dialog that appears when async task started
 *@Resources                ;object  to get strings from resources 
 *@JSONObject               ;JSON object received from an activity    
 *@String TAG_MESSAGE       ;message tag
 *@String VAL_URL           ;url
 *@String TAG_JSON          ;JSON object tag
 *@JSONParser               ;object to parse JSON object  and make http request    
 *--------------------------------------------------------------------
 * @Methods
 *--------------------------------------------------------------------
 *@void OnPreExecute()              ;called before starting the background thread
 *@String doInBackground(String...) ;launchs background thread
 *@void onPostexecute(Sting);       ;called when background thread  is finished 
 *@void imageToast()                ;shows message dialog
 *@void getResourcesStrings()       ;gets strings from resources
 ********************************************************************/
   
class HttpIO extends AsyncTask<String, String, String> {
    //data
    
    private final  Activity activity;
    private final  AsyncTaskListener callback; 
    private  ProgressDialog pDialog;
    private static  Resources res;// 
    private  JSONObject jsnObj;//
    private  String TAG_MESSAGE;//message 
    private  String VAL_URL;//url
    private  String TAG_JSON;//askJSON
    private final JSONParser jsonParser;
    
    //methods
/********************************************************************
 * 
 * constructor HttpIO()
 * inputs   Activity  as parameter.
 * This parameter is an activity  that  launched  this asynchronous thread.
 * 
 * @param   act Activity  
 * 
 ********************************************************************/
        public HttpIO(Activity act) {
            this.activity=act;
            this.callback = (AsyncTaskListener)act;
            jsonParser = new JSONParser();
        }
/********************************************************************
 * 
 * getResourcesString()
 * gets strings from resources.
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
 * gets  called before starting the background thread,
 * gets  an JSON object from an  activity thread via the method onTaskStarted
 * of AsyncTaskListener  interface .
 * shows Progress Dialog 
 * 
 ********************************************************************/
        @Override
        protected  void onPreExecute() {
            super.onPreExecute();
            getResourcesStrings();//get strings from resources
            jsnObj=new JSONObject();
            jsnObj=callback.onTaskStarted();//get JSON object
            pDialog = new ProgressDialog(this.activity);
            try {
                pDialog.setMessage(jsnObj.getString(TAG_MESSAGE));
                }
            catch (JSONException ex) {
                Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();//shows progress dialog
            jsnObj.remove(TAG_MESSAGE);
            }
/******************************************************************** 
  *  
  * doInBackground() 
  * gets called on the background  main thread when execute() is 
  * invoked on an instance of AsyncTask HttpIO,
  * gets an  JSON object as the parameter ,
  * makes the  request  to a http server
  * returns the JSON object as an string
  *  
  * @param args 
  * @return String 
  *  
  ********************************************************************/ 
 	@Override 
 	protected String doInBackground(String... args) { 
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_JSON, jsnObj.toString()));
            jsnObj = jsonParser.makeHttpRequest(VAL_URL,"POST",params);
            // finish();
            } 
        catch (IOException ex) {
            Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        catch (JSONException ex) {
            Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return jsnObj.toString();
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
 * imageToast()
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
