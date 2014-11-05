/**
 * I do not sleep tonight... I may not ever...
 *
 * JSONParser.java Copyright (C) 2014 The Android Open Source Project
 *
 * @author ASK https://github.com/ask1612/AndroidPHP.git
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to send a request to a http Server,receive a response from the http
 * Server, convert the received response to a String , create a new JSON object
 * from the String, parse the JSON object, return the JSON Object
 */
public class JSONParser {

    private static InputStream inputstream = null;
    private static JSONObject jObj = null;
    private static String json = "";
    private static HttpResponse httpResponse;

    // constructor

    public JSONParser() {
    }

    /**
     * This function makes a http request by POST or GET method, passes to a
     * given server parameters TAG_JSON and JSON object as string , gets a
     * string from http server, converts it to a JSON object and return the JSON
     * object. The url argument must specify an absolute {@link URL}.
     *
     * @param url an absolute URL
     * @param method POST or GET http request method
     * @param params tag and JSON object as string
     * @return JSON object
     */
    public JSONObject makeHttpRequest(String url,
            String method, List<NameValuePair> params) throws IOException, JSONException {
        try {
            // Making HTTP request
            //DefaultHttpClient is  used to make remote HTTP requests.
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //Connecting via POST method
            if (method == "POST") {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                httpResponse = httpClient.execute(httpPost);
            } //Connecting via GET method
            else if (method == "GET") {
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                inputstream = httpEntity.getContent();
                //InputStream class is used to read data from a  network.
                //BufferedReader class  wraps the input stream reader
                //and buffers the input. 
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputstream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                inputstream.close();
                json = sb.toString();
            }
            jObj = new JSONObject(json);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JSONParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(HttpIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return JSON object
        return jObj;

    }
}
