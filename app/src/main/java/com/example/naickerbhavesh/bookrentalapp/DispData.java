package com.example.naickerbhavesh.bookrentalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class DispData extends AppCompatActivity {

    EditText txtBookName,txtBookPrice;
    Button btnEditDetails,btnDeleteDetails;
    String bookname,bookprice,bookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_data);

        Intent intent=getIntent();
        bookname=intent.getStringExtra("name");
        bookprice=intent.getStringExtra("price");

        txtBookName=findViewById(R.id.txtBook);
        txtBookPrice=findViewById(R.id.txtPrice);
        btnEditDetails=findViewById(R.id.btnEdit);
        btnDeleteDetails=findViewById(R.id.btnDelete);

        txtBookName.setText(bookname);
        txtBookPrice.setText(bookprice);
        btnDeleteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String URL = "http://172.16.9.159/delete.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("success")) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", bookname);
                        params.put("price",bookprice);
                        return params;
                    }
                };

//MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
                rq.add(stringRequest);

                Toast.makeText(DispData.this, "Delete Sucessfully", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(DispData.this,MainActivity.class);
                startActivity(intent1);
            }
        });

        btnEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String URL = "http://172.20.10.2/update.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("success")) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", bookname);
                        params.put("price",bookprice);
                        params.put("name",txtBookName.getText().toString());
                        params.put("price",txtBookPrice.getText().toString());
                        return params;
                    }
                };

//MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
                rq.add(stringRequest);

                Toast.makeText(DispData.this, "Update Sucessfully", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(DispData.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
