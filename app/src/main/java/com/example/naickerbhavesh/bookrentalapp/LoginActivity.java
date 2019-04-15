package com.example.naickerbhavesh.bookrentalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText txtUname,txtPass;
    Button btnLogin;
    TextView txtReg;
    int flag=0;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUname=findViewById(R.id.txtUname);
        txtPass=findViewById(R.id.txtPass);
        btnLogin=findViewById(R.id.btnLogin);
        txtReg=findViewById(R.id.txtReg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.GET,
                        "http://192.168.0.29/login.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jo=new JSONObject(response);
                                    JSONArray ja=jo.getJSONArray("Book");

                                    for(int i=0;i<ja.length();i++){
                                        JSONObject jsonObject=ja.getJSONObject(i);

                                        if(txtUname.getText().toString().equals(jsonObject.getString("uname"))
                                                && txtPass.getText().toString().equals(jsonObject.getString("pass"))){
                                            flag++;
                                            uname=txtUname.getText().toString();
                                            break;
                                        }

                                    }
                                    if(flag==1){
                                        flag=0;
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("uname",uname);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });

                RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
                rq.add(stringRequest);
            }
        });

        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegistrationPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
