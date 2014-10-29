
/**
 * AskJson.java
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
 */
package  ask.droid.php;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class AskJson extends Activity
{
    private EditText edtText1, edtText2;
    private TextView myText;
    private Button btnSave;

  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_input);
        edtText1 = (EditText)findViewById(R.id.edtName);
        myText=(TextView)findViewById(R.id.txtHeader);
       btnSave=(Button)findViewById(R.id.btnSave);
         
            //The register button click handler
        btnSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
/**
 * try{
                //Create new JSON object; 
                JSONObject jsnRecord=new JSONObject();
                jsnRecord.put("name", edtName.getText().toString()); 
                jsnRecord.put("password", edtPassword.getText().toString());
                String record = "record"+index;
                jsnObj.put(record,jsnRecord );
                }
               catch(JSONException expt )
               {
               expt.printStackTrace();
                }
          if(index.equals("1"))
                {
                index="0";
  //           Toast.makeText(MainActivity.this, jsnObj.toString(), Toast.LENGTH_LONG).show();
  //             new MainActivity.RegisterUser().execute();
                }
          else{
              index="1";
          }*/
              
           }
        }); 
     
         
         
         
        edtText1.setOnKeyListener(new View.OnKeyListener()
        {
        public boolean onKey(View v, int keyCode, KeyEvent event)
            {
	    if(event.getAction() == KeyEvent.ACTION_DOWN && 
		    (keyCode == KeyEvent.KEYCODE_ENTER))
			{
			    // save text 
			    String strCatName = edtText1.getText().toString();
				return true;
			}
		return false;
            }
        });
    }
    
        
 
 

};