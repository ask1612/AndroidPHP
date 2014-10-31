/**
 * I do not sleep tonight... I may not ever...
 * JSONParser.java
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
*/
package ask.droid.php;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;

/**
 *  @Class JSONParser
 * {  
 * Class to sent http request to http Server,
 * receive  response from http Server,
 * convert  received response to String
 * create new JSON object from String
 * parse JSON object,
 * return JSON Object
 * 
 * @private members of class
 *          @static InputStream;
 *          @static JSONObject;
 *          @static String;
 *          @static final String;
 *          @static final String;
 * 
 * 
 * @public method of class:
 *           @JSONParser(); 
 *           @JSONObject makeHttpRequest(String,String,List<>);
 * }  
 * */

 
public class JSONParser {
 
    private static InputStream inputstream = null;
    private static JSONObject jObj = null;
    private static String json = "";
    private static final String TAG_JSONPARSER = "JSON Parser"; 
    private static final String TAG_HTTPREQUEST = "Http Request"; 
    private  static HttpResponse httpResponse;
    // constructor
    public JSONParser() {
    }
 
    // This function make  http request by  POST or GET method,
    //passes to url   parameters  TAG_JSON and JSON object 
    // and return JSON object    from url  .
    public JSONObject
        makeHttpRequest(String url, String method,List<NameValuePair> params) {
        // Making HTTP request
        try {
            //DefaultHttpClient is  used to make remote HTTP requests.
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //Connecting via POST method
            if(method == "POST"){
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                httpResponse = httpClient.execute(httpPost);
                }
            //Connecting via GET method
            else if(method == "GET"){
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet =  new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
                }
            HttpEntity httpEntity = httpResponse.getEntity();
            if(httpEntity!=null) {
                inputstream = httpEntity.getContent();
                //InputStream class is used to read data from a  network.
                //BufferedReader class  wraps the input stream reader
                //and buffers the input. 
                BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputstream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    }
                inputstream.close();
                json = sb.toString();
                }
        }
        catch (Exception e) {
            // if any error, then print the stack trace.
           Log.e(TAG_HTTPREQUEST, "Error in http connection" + e.toString());
            }       
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
            } 
        catch (JSONException e) {
            Log.e(TAG_JSONPARSER, "Error parsing data " + e.toString());
            }
 
        // return JSON object
        return jObj;
 
    }
} 
