package com.example.naickerbhavesh.bookrentalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class    RegistrationPage extends AppCompatActivity {

    String stArea,uname,pass;

    EditText txtUsername,txtPassword;
    Spinner spArea;
    Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        txtUsername=findViewById(R.id.txtEmailId);
        txtPassword=findViewById(R.id.txtPassword);
        spArea=findViewById(R.id.spArea);
        btnReg=findViewById(R.id.btnSave);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname=txtUsername.getText().toString();
                pass=txtPassword.getText().toString();
                stArea=spArea.getSelectedItem().toString();
                Log.i("vishal","start");
                Toast.makeText(RegistrationPage.this, "Email id : "+uname+
                        "\nPassword : "+pass+
                        "\nArea : "+stArea, Toast.LENGTH_SHORT).show();

                final String URL = "http://172.16.9.159/registration.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if(response.contains("success"))
                        {
                            Log.i("vishal","start 1");

                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.i("vishal","start 2");

                        Log.i("vishal","err:"+error.getMessage());
                    }

                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Log.i("vishal","start 3");

                        Map<String, String> params = new HashMap<>();
                        params.put("uname", txtUsername.getText().toString());
                        params.put("pass", txtPassword.getText().toString());
                        params.put("area", spArea.getSelectedItem().toString());

                        return params;
                    }
                };


                RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
                rq.add(stringRequest);

                Toast.makeText(RegistrationPage.this, "Sucessfully Stored", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegistrationPage.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
