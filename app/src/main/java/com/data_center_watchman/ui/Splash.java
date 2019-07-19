package com.data_center_watchman.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.data_center_watchman.R;

public class Splash extends AppCompatActivity {

    CardView cardRequest, cardCheckIn,cardCheckedOut, allLogs;
    Button sendRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        cardCheckIn = findViewById(R.id.cardCheckedIn);
        cardCheckedOut = findViewById(R.id.cardCheckout);
        cardRequest = findViewById(R.id.cardRequest);
        allLogs = findViewById(R.id.cardViewLogs);
        sendRequest = findViewById(R.id.sendRequest);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);

            }
        });

        cardRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, RequestsActivity.class);

                startActivity(intent);

            }
        });
        cardCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, CheckedInActivity.class);

                startActivity(intent);

            }
        });
        cardCheckedOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, CheckedOutActivity.class);

                startActivity(intent);

            }
        });
        allLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, VisitorList.class);

                startActivity(intent);

            }
        });
    }
}
