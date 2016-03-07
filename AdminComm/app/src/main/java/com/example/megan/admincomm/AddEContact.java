package com.example.megan.admincomm;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddEContact extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText phoneNo;
    private EditText ext;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_econtact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add E/M Contact");

        radioGroup = (RadioGroup) findViewById(R.id.contactChoice);
        phoneNo= (EditText) findViewById(R.id.number);
        ext= (EditText)findViewById(R.id.ext);
        submit= (Button) findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleData();
            }
        });

    }

    private void handleData() {

        int selected =radioGroup.getCheckedRadioButtonId();

        if(selected==-1){
            Toast.makeText(getApplicationContext(),"Please select appropriate Category.",
                    Toast.LENGTH_SHORT).show();
        }

        if(phoneNo.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Phone Number not entered",Toast.LENGTH_SHORT).show();
        }
        else
        if (!isValidPhoneNo(phoneNo.getText().toString())){
                Toast.makeText(getApplicationContext(),"Invaid phone number",Toast.LENGTH_SHORT).show();
        }
        else
        if(!ext.getText().toString().isEmpty() && !isValidExt(ext.getText().toString()))
            Toast.makeText(getApplicationContext(),"Invalid extension to short",Toast.LENGTH_SHORT).show();

        else{

            radioButton= (RadioButton) findViewById(selected);
            Log.d("ME", "Can make json object and send based on URI");
            if(radioButton.getText().toString().equals("Emergency Contact")){
                //URL=
                //make json object to send to server
            }else{
                //URL=
                //same as above
            }

        }

    }

    private boolean isValidPhoneNo(String num) {
        return Patterns.PHONE.matcher(num).matches();
    }

    public boolean isValidExt(String e){
        return e.length()>4;
    }

    @Override
    public void onBackPressed(){
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
        //startActivity(intent);
        this.finish();
        return true;

        // return super.onOptionsItemSelected(item);
    }
}
