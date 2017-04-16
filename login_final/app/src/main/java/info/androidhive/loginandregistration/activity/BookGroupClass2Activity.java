package info.androidhive.loginandregistration.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppConfig;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

public class BookGroupClass2Activity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_group_class2);
        GridView gridView;
		
		// Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {

            //HttpClient httpclient = new DefaultHttpClient();
            //HttpPost httppost = new HttpPost(URL_SCHEDULE);
            //ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //String responseBody = httpclient.execute(httppost, responseHandler);

            JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_SCHEDULE, new Response.Listener<JSONArray>() {
                
				@Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, response.toString());
 
                    try {
                        // Parsing json array response
                        // loop through each json object
                        jsonResponse = "";
                        for (int i = 0; i < response.length(); i++) {
 
                            JSONObject Day = (JSONObject) response
                                    .get(i);
 
                            String date = Day.getString("date");
                            String session1 = Day.getString("session1");
                            String session2 = Day
                                    .getString("session2");
                            
                            jsonResponse += "Date: " + date + "\n\n";
                            jsonResponse += "session1: " + session1 + "\n\n";
                            jsonResponse += "session2: " + session2 + "\n\n";
                        }
                        Log.d(TAG, jsonResponse.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
 
                    hideDialog();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            });
 
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req);

            gridView = (GridView) findViewById(R.id.gridView1);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Toast.makeText(getApplicationContext(),
                            ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                }
            });
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
