package com.iot.smart_lighting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Main extends AppCompatActivity {

    // Variable Declaration
    CardView feature1, feature2, feature3, feature4;
    ImageView settingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        feature1 = findViewById(R.id.cardView1);
        feature2 = findViewById(R.id.cardView2);
        feature3 = findViewById(R.id.cardView3);
        feature4 = findViewById(R.id.cardView4);
        settingMenu = findViewById(R.id.setting_btn1);

        settingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the menu layout
                PopupMenu popupMenu = new PopupMenu(Main.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.setting, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.darkMode) {
                            // Handle dark mode
                        }
                        else if (menuItem.getItemId() == R.id.lightMode){
                            // Handle light mode
                        }
                        else {
                            NetworkDialogBox dialogBox = new NetworkDialogBox();
                            dialogBox.show(getSupportFragmentManager(), "Network Dialog Box");
                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });

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

        feature3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, ColourEditor.class);
                startActivity(intent);
            }
        });

        feature4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, DataAnalysis.class);
                startActivity(intent);
            }
        });
    }
}
