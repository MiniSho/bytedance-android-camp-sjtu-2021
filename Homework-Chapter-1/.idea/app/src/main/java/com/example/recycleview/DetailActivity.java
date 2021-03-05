package com.example.recycleview;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String extra = getIntent().getStringExtra("extra");
        TextView textView = findViewById(R.id.text);
        textView.setText(extra);

    findViewById(R.id.btn).setOnClickListener(v -> {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    });

}
}
