package info.androidhive.loginandregistration.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import info.androidhive.loginandregistration.R;

public class BookTennisActivity extends AppCompatActivity implements View.OnClickListener {

    //UI References
    public EditText sessionDateTennis;
    public String level;
    private Button submitLevelTennis;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private RadioGroup playerLevelTennis;
    private RadioButton tennisPlayerLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tennis);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();
        setPlayerLevel();
    }

    private void findViewsById() {
        sessionDateTennis = (EditText) findViewById(R.id.selectDateTennis);
        sessionDateTennis.setInputType(InputType.TYPE_NULL);
        submitLevelTennis = (Button) findViewById(R.id.submitLevelTennis);
        playerLevelTennis = (RadioGroup) findViewById(R.id.playerLevelTennis);
    }

    private void setDateTimeField() {
        sessionDateTennis.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                sessionDateTennis.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setPlayerLevel() {
        submitLevelTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = playerLevelTennis.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                tennisPlayerLevel = (RadioButton) findViewById(selectedId);
                level = (String) tennisPlayerLevel.getText();
                Toast.makeText(BookTennisActivity.this, tennisPlayerLevel.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BookTennisActivity.this, BookTennis2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == sessionDateTennis) {
            datePickerDialog.show();
        }
    }
}