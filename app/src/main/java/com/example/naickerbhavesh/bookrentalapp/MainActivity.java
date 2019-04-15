package com.example.naickerbhavesh.bookrentalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pd;
    ListView lstView;
    String userName;
    TextView txtUsername;
    JSONArray ja;
    ArrayList<BeanClass> bclass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bclass=new ArrayList<>();
        txtUsername=findViewById(R.id.txtUserName);
        Intent i=getIntent();
        userName=i.getStringExtra("uname");
        txtUsername.setText(userName);
        lstView=findViewById(R.id.lstView);
        final ArrayList<HashMap<String,String>> aList=new ArrayList<>();
        pd=new ProgressDialog(MainActivity.this);
        pd.setMessage("Please Wait... ");
        pd.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                "http://192.168.0.29/book.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            pd.dismiss();
                           // Toast.makeText(MainActivity.this, "Try", Toast.LENGTH_SHORT).show();
                            JSONObject jo=new JSONObject(response);
                            ja=jo.getJSONArray("Book");

                            for(int i=0;i<ja.length();i++){
                                JSONObject jsonObject=ja.getJSONObject(i);
                                HashMap<String,String> hs=new HashMap<>();
                                hs.put("name",jsonObject.getString("name"));
                                hs.put("price",jsonObject.getString("price"));
                                aList.add(hs);


                                BeanClass b=new BeanClass();
                                b.bkName=jsonObject.getString("name");
                                b.bkPrice=jsonObject.getString("price");
                                bclass.add(b);
                            }

                            String from[]={"name","price"};
                            int to[]={R.id.bkName,R.id.bkPrice};
                            SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),aList,R.layout.bookfile,from,to);
                            lstView.setAdapter(simpleAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Catch Block", Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error : " + error, Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
        rq.add(stringRequest);


        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stData=parent.getItemAtPosition(position)+"";
                Toast.makeText(MainActivity.this, stData, Toast.LENGTH_SHORT).show();

                BeanClass bc=new BeanClass();
                bc=bclass.get(position);

                Intent intent=new Intent(MainActivity.this,BooksFinal.class);
                intent.putExtra("name",bc.bkName);
                intent.putExtra("price",bc.bkPrice);
                startActivity(intent);

            }
        });

    }
}
