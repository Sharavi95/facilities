package info.androidhive.loginandregistration.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppConfig;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

import static info.androidhive.loginandregistration.activity.BookGroupClassActivity.newDate;

public class BookGroupClass2Activity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private String jsonResponse;
	private String date;
    private Map<String, String> mParams;
    private String URL;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_group_class2);
      
		// Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

		date = BookGroupClassActivity.dateFormatter.format(newDate.getTime());
        URL= AppConfig.URL_SCHEDULE + "?date=" + date;


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
			// Tag used to cancel the request
			String tag_string_req = "req_get_sch";

			pDialog.setMessage("Loading Schedule...");
			showDialog();

			StringRequest strReq = new StringRequest(Request.Method.POST,
					URL, new Response.Listener<String>() {

               	@Override
				public void onResponse(String response) {
					Log.d(TAG, "Schedule Response: " + response.toString());
					hideDialog();

					try {
						JSONObject jObj = new JSONObject(response);
						boolean error = jObj.getBoolean("error");

						// Check for error node in json
						if (!error) {
												   
							String session1 = jObj.getString("session1");
							String session2 = jObj.getString("session2");
							
							//Display on screen
                            TextView txt1 = (TextView) findViewById(R.id.textView);
                            TextView txt2 = (TextView) findViewById(R.id.textView);
                            txt1.setText(session1);
                            txt2.setText(session2);
							
						} else {
							// Error in Getting Schedule. Get the error message
							String errorMsg = jObj.getString("error_msg");
							Toast.makeText(getApplicationContext(),
									errorMsg, Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						// JSON error
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
					}

				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, "Schedule Error: " + error.getMessage());
					Toast.makeText(getApplicationContext(),
							error.getMessage(), Toast.LENGTH_LONG).show();
					hideDialog();
				}
			}) {

                @Override
                public Map<String, String> getParams() throws com.android.volley.AuthFailureError{
                    //Map<String, String> params = new HashMap<String, String>();
                    //mParams.put("date", date);
                    return mParams;
                }

			};

			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
				
        }
        else{
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(BookGroupClass2Activity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
	}

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
