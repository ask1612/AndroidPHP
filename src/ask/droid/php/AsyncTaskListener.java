/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ask.droid.php;

import org.json.JSONObject;

/**
 * I do not sleep tonight... I may not ever...
 * AsyncTaskListener.java
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
*/
public interface AsyncTaskListener {
    JSONObject onTaskStarted();
    void onTaskFinished(String result);
 }
