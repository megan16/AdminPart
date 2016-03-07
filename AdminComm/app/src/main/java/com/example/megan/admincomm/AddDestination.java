package com.example.megan.admincomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDestination extends AppCompatActivity {
    private Button submit;
    private EditText loc;

    /*
    can refine by if only one destination text dialog
    if more open this activity and generate text view based on a number entered
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Destination");

        loc= (EditText) findViewById(R.id.loc);
        submit= (Button) findViewById(R.id.go);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loc.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty Destination",Toast.LENGTH_SHORT).show();

                }
                else {
                    // do web service
                }
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
