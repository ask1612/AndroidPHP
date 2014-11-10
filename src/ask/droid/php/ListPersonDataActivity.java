/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ask.droid.php;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ASK
 */
public class ListPersonDataActivity extends Activity {

    private JSONArray jsnArr;
    private JSONObject jsnObj;
    private static Resources res;
    private String json;
    private String TAG_JSON;
    private String TAG_PSNNAME;
    private String TAG_SURNAME;
    private String TAG_ADDRESS;
    private String TAG_CITY;
    private String TAG_STREET;
    private String TAG_BUILD;
    private String TAG_FLAT;

    String[] strArr = new String[2];

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
        Log.d(TAG_JSON, json);
        try {
            jsnArr = new JSONArray(json);
            jsnObj = new JSONObject();
            jsnObj = jsnArr.getJSONObject(0);
            strArr[0] = jsnObj.getString(TAG_PSNNAME) + " "
                    + " " + jsnObj.getString(TAG_SURNAME) + "\n"
                    + "ADDRESS:  "
                    + jsnObj.getString(TAG_CITY) +"  "
                    + jsnObj.getString(TAG_STREET);
            
            jsnObj = jsnArr.getJSONObject(1);
            strArr[1] = jsnObj.getString(TAG_PSNNAME) + " "
                    + " " + jsnObj.getString(TAG_SURNAME) + "\n"
                    + "ADDRESS:   "
                    + jsnObj.getString(TAG_CITY)+"  "
                    + jsnObj.getString(TAG_STREET);

        } catch (JSONException ex) {
            Logger.getLogger(ListPersonDataActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        this.TAG_ADDRESS = res.getString(R.string.tag_address);
        this.TAG_CITY = res.getString(R.string.tag_city);
        this.TAG_STREET = res.getString(R.string.tag_street);
        this.TAG_BUILD = res.getString(R.string.tag_build);
        this.TAG_FLAT = res.getString(R.string.tag_flat);

    }

}
/**
 * List<HashMap<String, String>> PersonList = new
 * ArrayList<HashMap<String, String>>(); HashMap<String, String> PersonData =
 * new HashMap<String, String>(); PersonData.put("personname", "Semen");
 * PersonData.put("Kazun", ""); PersonList.add(0, PersonData);
 *
 */
