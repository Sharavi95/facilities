package info.androidhive.loginandregistration.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import info.androidhive.loginandregistration.R;

public class BookGroupClassActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BookGroupClassActivity.class.getSimpleName();

    //UI References
    public EditText sessionDateGroupClass;
    private Button submitLevelGroupClass;
    private DatePickerDialog datePickerDialog;
    public static SimpleDateFormat dateFormatter;
    public static Calendar newDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_group_class);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setDateTimeField();
    }

    private void findViewsById() {
        sessionDateGroupClass = (EditText) findViewById(R.id.selectDateGroupClass);
        sessionDateGroupClass.setInputType(InputType.TYPE_NULL);
        submitLevelGroupClass  = (Button) findViewById(R.id.submitLevelGroupClass);
    }

    private void setDateTimeField() {
        sessionDateGroupClass.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                sessionDateGroupClass.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        Log.d(TAG, "Date for Group class selected");
        submitLevelGroupClass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(BookGroupClassActivity.this,BookGroupClass2Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == sessionDateGroupClass) {
            datePickerDialog.show();
        }
    }
}