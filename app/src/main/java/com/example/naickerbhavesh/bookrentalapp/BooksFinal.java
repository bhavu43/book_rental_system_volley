package com.example.naickerbhavesh.bookrentalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BooksFinal extends AppCompatActivity {

    TextView txtVwBook;
    Button btnAdd;
    String bookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_final);

        final Intent intent=getIntent();
        bookName=intent.getStringExtra("name");

        txtVwBook=findViewById(R.id.name);
        btnAdd=findViewById(R.id.btnAddBook);

        txtVwBook.setText(bookName);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BooksFinal.this, "Book Added!", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(BooksFinal.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
