package info.androidhive.loginandregistration.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppConfig;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SessionManager;
import info.androidhive.loginandregistration.sch.Schedule;
import info.androidhive.loginandregistration.sch.ScheduleAdapter;

import static info.androidhive.loginandregistration.activity.BookGroupClassActivity.newDate;

public class BookGroupClass2Activity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private Map<String, String> mParams;

    public static SimpleDateFormat dateFormatter2;
    private String date;

    private String URL, session1, session2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_group_class2);


		// Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        //change date format from d-m-y to y-m-d and convert calendar obj newDate to String date
        dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        date = dateFormatter2.format(newDate.getTime());

        //create appended url with date selected
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
												   
							session1 = jObj.getString("session1");
							session2 = jObj.getString("session2");

                            displaySch();

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

    private void displaySch(){
        // Create an ArrayList of Schedule objects
        final ArrayList<Schedule> schedule = new ArrayList<Schedule>();

        if(!session1.equals("null")){
            schedule.add(new Schedule(session1, "5:00-6:00"));
        }
        if(!session2.equals("null")){
            schedule.add(new Schedule(session2, "6:00-7:00"));
        }

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, schedule);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.listview_sch);
        listView.setAdapter(scheduleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text

                Schedule schedule1 = schedule.get(position);

                Toast.makeText(BookGroupClass2Activity.this, "Session" + schedule1.getSessionName(),
                    Toast.LENGTH_SHORT).show();
            }
        });

    }

}
