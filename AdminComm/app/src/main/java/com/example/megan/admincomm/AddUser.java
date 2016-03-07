package com.example.megan.admincomm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddUser extends AppCompatActivity {
    private static final String URL ="" ;
    private EditText dobDate;
    private Button calendarBtn;
    private Button addUserBtn;
    private EditText id;
    private EditText fname;
    private EditText lname;
    private EditText faculty;
    private EditText dept;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add User");

        dobDate=(EditText) findViewById(R.id.dob);
        calendarBtn= (Button) findViewById(R.id.calendarBtn);
        addUserBtn= (Button) findViewById(R.id.addBtn);
        id= (EditText) findViewById(R.id.uwiID);
        fname= (EditText) findViewById(R.id.fname);
        lname= (EditText) findViewById(R.id.lname);
        faculty= (EditText) findViewById(R.id.faculty);
        dept= (EditText) findViewById(R.id.dept);
        radioGroup= (RadioGroup) findViewById(R.id.gender);



        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calDialog();
            }
        });

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               getData();

           }
        });


    }

    @Override
    public void onBackPressed(){
        this.finish();
    }

    public void getData(){
        //check for empty fields
        if (fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() ||
                faculty.getText().toString().isEmpty() || dept.getText().toString().isEmpty() ||
                id.getText().toString().isEmpty()) {
            //display to user to fill all fields
            Toast.makeText(getApplicationContext(), "Please fill all text fields",
                    Toast.LENGTH_SHORT).show();
        }
        else
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please select a gender",
                    Toast.LENGTH_SHORT).show();
        } else {
            // get gender
            getGender();


            //check field data validity
            if (!isValidID(id.getText().toString())) {
                Toast.makeText(getApplicationContext(), "ID Number is too short",
                        Toast.LENGTH_SHORT).show();

            } else if (!isValidDateFormat(dobDate.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Invalid date format. (YYYY/MM/DD)",
                        Toast.LENGTH_SHORT).show();

            } else if (!isValidText(fname.getText().toString()) ||
                    !isValidText(lname.getText().toString()) ||
                    !isValidText(faculty.getText().toString()) ||
                    !isValidText(dept.getText().toString())) {

                Toast.makeText(getApplicationContext(), "Text fields can't contain numbers.",
                        Toast.LENGTH_SHORT).show();

            } else {
                //valid date    Toast.makeText(getApplicationContext(),"Valid date format",Toast.LENGTH_SHORT).show();
                // else send json objects
                Log.d("ME", "Send data as json objects to server ");

            }

        }


}


    public void getGender(){
        int selected= radioGroup.getCheckedRadioButtonId();
        radioButton= (RadioButton) findViewById(selected);
        if(selected!=-1)
            Log.d("ME", "Gender: " + radioButton.getText());

    }

    public void calDialog() {

        final Calendar cal = Calendar.getInstance();
        int calYear = cal.get(Calendar.YEAR);
        int calMonth = cal.get(Calendar.MONTH);
        int calDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
                Date date=null;
                try {

                    String dateString=""+year+"/"+""+(monthOfYear+1)+"/"+dayOfMonth;
                    date=sdf.parse(dateString);
                    //setting it in edit text view
                    String d8=sdf.format(date);
                    dobDate.setText(sdf.format(date));
                    Log.d("ME", "date string : " + sdf.format(date));




                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, calYear, calMonth, calDay);

        datePickerDialog.show();
    }

    public boolean isValidText(String txt){
        //checks to see if it contains a number
         return  !txt.matches(".*\\d.*");

    }

    public boolean isValidID(String num){
        return num.length()>4;
    }


    public boolean isValidDateFormat(String dateFromUser){
        Date date=null;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");

        try {

            date=sdf.parse(dateFromUser);
            if(!dateFromUser.equals(sdf.format(date))){
               date=null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date!=null;
    }

    public void webService(JSONObject obj){

        JsonObjectRequest jsonObjectReq= new JsonObjectRequest(Request.Method.POST, URL, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ME", " response from server: " + response.toString());

                        if(response.toString().equals("ok")){
                            Toast.makeText(getApplicationContext(), "priviledged page", Toast.LENGTH_LONG).show();

                        }
                        // hideprogress dialog();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public  void onErrorResponse(VolleyError error){
                        // hide progress dialog
                        VolleyLog.d("ME", "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_LONG);
                    }




                });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

      //  Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
        //startActivity(intent);
        this.finish();
        return true;

        // return super.onOptionsItemSelected(item);
    }
}
