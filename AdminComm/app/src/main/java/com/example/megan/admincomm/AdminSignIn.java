package com.example.megan.admincomm;

import android.app.Application;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;



public class AdminSignIn extends AppCompatActivity {

    private Button button;
    private EditText username;
    private EditText password;
    private final static String URL = "https://projectcomp3990.herokuapp.com/login";
    //private RequestQueue queue;//
    HurlStack hurlStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.unameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);

        username= (EditText) findViewById(R.id.uname);
        password= (EditText) findViewById(R.id.pass);

        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");

        button = (Button) findViewById(R.id.signInBtn);

//TODO: Check for empty fields

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = usernameWrapper.getEditText().getText().toString();

                RequestParams params= new RequestParams();
                params.put("username",uname.trim());
                params.put("password", passwordWrapper.getEditText().getText().toString().trim());
                webService(params);
            }
        });


    }

    public void closeActivity() {
        //below does not work inside web service function
        this.finish();
    }

    public void webService(RequestParams params) {

        AsyncHttpClient client= new AsyncHttpClient();

        //client.addHeader("Content-Type","application/x-www-form-urlencoded");//?? it magically works now
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
                Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                closeActivity();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("MEG","Failure Status Code "+statusCode);
                String response= null;
                try {
                    response = new String(responseBody,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),""+statusCode+".."+response,Toast.LENGTH_SHORT).show();
            }
        });



    }
}
