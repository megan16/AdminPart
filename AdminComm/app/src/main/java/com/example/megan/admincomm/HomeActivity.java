package com.example.megan.admincomm;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    String[] web={
            "Add User","Add Emergency/Medical Contact","Add Destination"
    };

    Integer[] imageID= {R.drawable.plus,R.drawable.plus,R.drawable.plus};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MyCustomList listAdapter=new MyCustomList(HomeActivity.this,web,imageID);
        ListView listView= (ListView) findViewById(R.id.choiceList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent,View view,int pos,long id){
                Toast.makeText(HomeActivity.this,"Clicked "+web[+pos],Toast.LENGTH_SHORT).show();
                launchActivity(view, pos);

                }


        });
    }


    public void launchActivity(View view, int pos){

        if(web[pos].trim().equalsIgnoreCase("Add User")){
            Intent intent= new Intent(getApplicationContext(),AddUser.class);
            startActivity(intent);

        }
        else
        if(web[pos].trim().equalsIgnoreCase("Add Emergency/Medical Contact")){
            Intent intent= new Intent(getApplicationContext(),AddEContact.class);
            startActivity(intent);

        }
        else
            if (web[pos].trim().equalsIgnoreCase("Add Destination")){
                Intent intent= new Intent(getApplicationContext(),AddDestination.class);
                startActivity(intent);
            }

    }


    public void dialogDestination(){

        LayoutInflater layoutInflater= LayoutInflater.from(this);
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(this);
    }


}
