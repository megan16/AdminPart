package com.example.megan.admincomm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminSignIn extends AppCompatActivity {

    private Button button;
    private EditText username;
    private EditText password;
    private final String URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);

        final TextInputLayout usernameWrapper= (TextInputLayout) findViewById(R.id.unameWrapper);
        final TextInputLayout passwordWrapper= (TextInputLayout) findViewById(R.id.passwordWrapper);

        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");

        button= (Button) findViewById(R.id.signInBtn);


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String uname=usernameWrapper.getEditText().getText().toString();
                String pass= passwordWrapper.getEditText().getText().toString();

                // go to a new activity
                Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
             //   intent.putExtra("username",uname);
                startActivity(intent);
                //closeActivity(getApplicationContext());

//                JSONObject jsonObject=new JSONObject();
//                try {
//
//                    jsonObject.put("uname",uname);
//                    jsonObject.put("pass",pass);
//                    webService(jsonObject,uname);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }



            }
        });


    }

    public void closeActivity(Context c){
        //below does not work inside a json request
        this.finish();
    }

    public void webService(JSONObject obj, final String uname){


        JsonObjectRequest jsonObjectReq= new JsonObjectRequest(Request.Method.POST, URL, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ME", " response from server: " + response.toString());

                        if(response.toString().equals("priviledged page")){
                            Toast.makeText(getApplicationContext(), "Redirecting", Toast.LENGTH_LONG).show();
                            // go to a new activity
                            Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                            intent.putExtra("username",uname);
                            startActivity(intent);
                            //closeActivity();

                        }

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



}
