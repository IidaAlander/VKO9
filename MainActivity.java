package com.example.week9;



import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Singleton
    MovieTheater mt = MovieTheater.getInstance();
    com.example.week9.DatePicker dp = com.example.week9.DatePicker.getInstance();

    //MovieTheater mt = new MovieTheater();

    Context context = null;

    Spinner spinnerTheater;
    ListView listView;
    TextView tvDate;
    Button btDatePicker;
    DatePickerDialog datePickerDialog;
    int index;
    String date;

    // Haetaan lista teattereista ja elokuvista
    ArrayList<String> arrayList = mt.returnTheatreList();
    ArrayList<String> arrayList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        spinnerTheater = findViewById(R.id.spinnerTeather);
        listView = findViewById(R.id.listView);
        tvDate = findViewById(R.id.tvDate);
        btDatePicker = findViewById(R.id.btDatePicker);
        btDatePicker.setText(dp.getTodaysDate());
        initDatePicker();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheater.setAdapter(arrayAdapter);


        // Spinner, choosing the theater
        spinnerTheater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                index = position;
                setMovieList();
                String getValue = String.valueOf(adapterView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void openDatePicker (View v) {
        datePickerDialog.show();
    }

    private void initDatePicker() {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month +1;
                    date = dp.makeDateString(day, month, year);
                    btDatePicker.setText(date);
                }
            };
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(this,dateSetListener,year,month,day);

        }


    // ListView, setting the movie list to appear on list vies once theater is selected
    public void setMovieList() {
        try {
            arrayList1 = mt.returnMovieList(MovieTheater.theatre_list.get(index), date);
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList1);
            listView.setAdapter(arrayAdapter1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

}