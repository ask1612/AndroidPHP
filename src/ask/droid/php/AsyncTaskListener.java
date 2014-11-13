/**
 * Niemand ist perfekt. I do not sleep tonight... I may not ever...
 *
 * AsyncTaskListener.java Copyright (C) 2014 The Android Open Source Project
 *
 * @author ASK https://github.com/ask1612/AndroidPHP.git
 */
package ask.droid.php;

import org.json.JSONObject;


/**
 * Defines the AsyncTaskListener interface.
 *
 */
public interface AsyncTaskListener {

    JSONObject onTaskStarted();

    void onTaskFinished(String result);
}
