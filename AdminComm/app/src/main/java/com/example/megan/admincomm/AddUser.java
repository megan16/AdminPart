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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddUser extends AppCompatActivity {
    private static final String URL ="https://projectcomp3990.herokuapp.com/signup";
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
                Log.d("MEG", "Send data to server "+removeDateFormat(dobDate.getText().toString().trim()));
//TODO: make department n faculty a drop down list

                RequestParams params= new RequestParams();
                params.put("username",id.getText().toString().trim());
                params.put("password",removeDateFormat(dobDate.getText().toString().trim()) );
                params.put("fname",fname.getText().toString().trim());
                params.put("lname",lname.getText().toString().trim());
                params.put("dob",dobDate.getText().toString().trim());
                params.put("gender",radioButton.getText().toString());
                params.put("faculty",faculty.getText().toString().trim());
                params.put("department",dept.getText().toString().trim());

                webService(params);

            }

        }


}


    public void getGender(){
        int selected= radioGroup.getCheckedRadioButtonId();
        radioButton= (RadioButton) findViewById(selected);
        if(selected!=-1)
            Log.d("MEG", "Gender: " + radioButton.getText());

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
                    Log.d("MEG", "date string : " + sdf.format(date));




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

    public void webService(RequestParams params){

      AsyncHttpClient client= new AsyncHttpClient();

            client.addHeader("Content-Type", "application/x-www-form-urlencoded");
            client.post(getApplicationContext(),URL, params,new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("MEG", "Success Status Code " + statusCode);
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    try {
                        String response=new String(responseBody,"UTF-8");
                        Log.d("MEG", "Success Status Code " +response);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // go to homepage
                  //  Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                    //startActivity(intent);
                    closeActivity();


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    String response= null;
                    try {
                        response = new String(responseBody,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),""+statusCode+".."+response,Toast.LENGTH_SHORT).show();
                    Log.d("MEG", "Failure Status Code " + statusCode+".."+response);

                    if(statusCode==500){
                        Toast.makeText(getApplicationContext(),"Ensure that ID doesnt already exist",Toast.LENGTH_SHORT).show();
                    }
                }
            });



    }

    public String removeDateFormat(String date){
        return date=date.replaceAll("/", "");
    }


    public void closeActivity() {

            this.finish();
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
