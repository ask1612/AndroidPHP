/**
 *
 * ListPersonDataActivity.java Copyright (C) 2014 The Android Open Source
 * Project
 *
 * @author ASK https://github.com/ask1612/AndroidPHP.git
 */
package ask.droid.php;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ListPersonDataActivity intendet to output list of records;
 *
 */
public class ListPersonDataActivity extends Activity {

    private JSONArray jsnArr;
    private JSONObject jsnObj;
    private static Resources res;
    private String TAG_JSON;
    private String TAG_PSNNAME;
    private String TAG_SURNAME;
    private String TAG_CNT;
    private String TAG_CITY;
    private String TAG_STREET;
    private String TAG_BUILD;
    private String TAG_FLAT;

    private String json;
    private int cnt_records;
    private String[] strArr;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.list);
        // ToDo add your GUI initialization code here 
        getResourcesStrings();
        Intent intent = getIntent();
        json = intent.getStringExtra(TAG_JSON);
        String s = intent.getStringExtra(TAG_CNT);
        cnt_records = Integer.parseInt(s);
        strArr = new String[cnt_records];

        Log.d(TAG_JSON, "  " + Integer.toString(cnt_records));
        doRecordsToStrings();
        ListView lvName = (ListView) findViewById(R.id.lvPersonData);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strArr);

        lvName.setAdapter(adapter);

    }

    /**
     *
     * Get strings from resources
     *
     */
    public void getResourcesStrings() {
        res = getResources();
        this.TAG_JSON = res.getString(R.string.tag_json);
        this.TAG_PSNNAME = res.getString(R.string.tag_psnname);
        this.TAG_SURNAME = res.getString(R.string.tag_surname);
        this.TAG_CNT = res.getString(R.string.tag_cnt);
        this.TAG_CITY = res.getString(R.string.tag_city);
        this.TAG_STREET = res.getString(R.string.tag_street);
        this.TAG_BUILD = res.getString(R.string.tag_build);
        this.TAG_FLAT = res.getString(R.string.tag_flat);

    }

    /**
     *
     * Output 2 records
     *
     */
    public void doRecordsToStrings() {
        try {
            jsnArr = new JSONArray(json);
            jsnObj = new JSONObject();
            for (int i = 0; i < this.cnt_records; i++) {
                jsnObj = jsnArr.getJSONObject(i);
                strArr[i] = jsnObj.getString(TAG_PSNNAME) + " "
                        + " " + jsnObj.getString(TAG_SURNAME) + "\n"
                        + "ADDRESS:  "
                        + TAG_CITY + ": " + jsnObj.getString(TAG_CITY) + "  "
                        + TAG_STREET + ": " + jsnObj.getString(TAG_STREET) + "  "
                        + TAG_BUILD + ": " + Integer.toString(jsnObj.getInt(TAG_BUILD)) + " "
                        + TAG_FLAT + ": " + Integer.toString(jsnObj.getInt(TAG_FLAT));

            }
        } catch (JSONException ex) {
            Logger.getLogger(ListPersonDataActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
/**
 * List<HashMap<String, String>> PersonList = new
 * ArrayList<HashMap<String, String>>(); HashMap<String, String> PersonData =
 * new HashMap<String, String>(); PersonData.put("personname", "Semen");
 * PersonData.put("Kazun", ""); PersonList.add(0, PersonData);
 *
 */
