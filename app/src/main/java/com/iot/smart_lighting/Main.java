package com.iot.smart_lighting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Main extends AppCompatActivity {

    // Variable Declaration
    CardView feature1, feature2, feature3, feature4, feature5, feature6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        feature1 = findViewById(R.id.cardView1);
        feature2 = findViewById(R.id.cardView2);
        feature3 = findViewById(R.id.cardView3);
        feature4 = findViewById(R.id.cardView4);

        feature1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, LampController.class);
                startActivity(intent);
            }
        });

        feature2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Timer.class);
                startActivity(intent);
            }
        });
    }
}
