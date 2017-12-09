package com.example.trip.tripster.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip.tripster.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.Place;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class AddPlace extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    TextView nameTextF;
    TimePickerDialog startTimePicker;
    TimePickerDialog endTimePicker;
    DatePickerDialog startDatePicker;
    DatePickerDialog endDatePicker;
    GregorianCalendar start;
    GregorianCalendar end;
    SimpleDateFormat dateFormat;
    SimpleDateFormat timeFormat;
    Button startTimeButton;
    Button endTimeButton;
    Button startDateButton;
    Button endDateButton;
    Toolbar toolbar;

    Place place;
    private static final String TAG = AddPlace.class.getSimpleName();
    int status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        nameTextF = (TextView) findViewById(R.id.placeName);
        startTimeButton = (Button) findViewById(R.id.startTime);
        startDateButton = (Button) findViewById(R.id.startDate);
        endTimeButton = (Button) findViewById(R.id.endTime);
        endDateButton = (Button) findViewById(R.id.endDate);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        String[] idArray = TimeZone.getAvailableIDs(-8 * 80 * 60 * 1000); //Pacific Standard Time Zone
        if (idArray.length == 0) {
            System.exit(0);
        }
        //create new PacificST
        SimpleTimeZone pacificStandardTime = new SimpleTimeZone(-8 * 60 * 60 * 1000, idArray[0]);
        pacificStandardTime.setStartRule(Calendar.APRIL, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        pacificStandardTime.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);


        Calendar calendar = new GregorianCalendar(pacificStandardTime);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        Date date = new Date();
        calendar.setTime(date);

        timeFormat = new SimpleDateFormat("hh:mm aa");
        dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");

        start = new GregorianCalendar(year, month, day, hour, minute);
        end = new GregorianCalendar(year, month, day, hour, minute);

        startTimeButton.setText(showDate(timeFormat, start));
        startDateButton.setText(showDate(dateFormat, start));

        endTimeButton.setText(showDate(timeFormat, end));
        endDateButton.setText(showDate(dateFormat, end));

        startTimePicker = TimePickerDialog.newInstance(this, false);
        startTimePicker.setVersion(TimePickerDialog.Version.VERSION_2);
        startDatePicker = DatePickerDialog.newInstance(this, year, month, day);
        startDatePicker.setVersion(DatePickerDialog.Version.VERSION_2);

        endTimePicker = TimePickerDialog.newInstance(this, false);
        endTimePicker.setVersion(TimePickerDialog.Version.VERSION_2);
        endDatePicker = DatePickerDialog.newInstance(this, year, month, day);
        endDatePicker.setVersion(DatePickerDialog.Version.VERSION_2);

        status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                GooglePlayServicesUtil.getErrorDialog(status, this, 100).show();
            }
        }
    }

    //Create place
    public boolean addPlace(MenuItem menuItem) {
        if(nameTextF.getText().toString().matches("")) {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        finish();
        return true;
    }
    //Open Picker
    public void openPlacePicker(View v) {

        if (status == ConnectionResult.SUCCESS) {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(intentBuilder.build(this), 1);
            } catch (GooglePlayServicesRepairableException e) {
                Log.e(TAG, e.getMessage());
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    //HTTP request
    protected void onActivityResult(int request, int result, Intent intent) {
        if (request == 1) {
            if (result == RESULT_OK) {
                place = PlacePicker.getPlace(this, intent);
                ((TextView)findViewById(R.id.placeName)).setText(place.getName());
                ((TextView)findViewById(R.id.placeLocation)).setText(place.getAddress());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_place, menu);

        return true;
    }

    @Override
    public void finish() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);

        if (!nameTextF.getText().toString().matches("")) {
            intent.putExtra("Name",nameTextF.getText().toString());
            intent.putExtra("StartDate", start);
            intent.putExtra("EndDate", end);
            intent.putExtra("Address", place.getAddress());

            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
        }
        super.finish();
    }

    //helper method to show the date
    public String showDate(SimpleDateFormat format, GregorianCalendar calendar) {
        format.setCalendar(calendar);

        return format.format(calendar.getTime());
    }

    public void openTimePicker(View v) {
        startDatePicker.show(getFragmentManager(), "StartTimePickerDialog");
    }

    public void closeTimePicker(View v) {
        endTimePicker.show(getFragmentManager(), "EndTimePickerDialog");
    }

    public void openDatePicker(View v) {
        startDatePicker.show(getFragmentManager(), "StartDatePickerDialog");
    }

    public void closeDatePicker(View v) {
        endDatePicker.show(getFragmentManager(), "EndDatePickerDialog");
    }


    //Override the Date Dialog for the picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (view == startDatePicker) {
            start.set(Calendar.YEAR, year);
            start.set(Calendar.MONTH, monthOfYear);
            start.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            startDateButton.setText(showDate(dateFormat, start));
        } else if (view == endDatePicker) {
            end.set(Calendar.YEAR, year);
            end.set(Calendar.MONTH, monthOfYear);
            end.set(Calendar.DAY_OF_MONTH, monthOfYear);

            endDateButton.setText(showDate(dateFormat, end));
        }
    }

    //Override the Time Dialog for the picker
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if (view == startTimePicker) {
            start.set(Calendar.HOUR_OF_DAY, hourOfDay);
            start.set(Calendar.MINUTE, minute);

            startTimeButton.setText(showDate(timeFormat, start));
        } else if (view == endTimePicker) {
            end.set(Calendar.HOUR_OF_DAY, hourOfDay);
            end.set(Calendar.MINUTE, minute);

            endTimeButton.setText(showDate(timeFormat, end));
        }
    }
}
