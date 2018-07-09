package com.test.bookmedriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ValidateActivity extends AppCompatActivity {
    private Button NFC,QR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        NFC = (Button)findViewById(R.id.btnNfc);
        QR = (Button)findViewById(R.id.btnQr);

        NFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ValidateActivity.this, NFCActivity.class);
                startActivity(intent);
            }
        });
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ValidateActivity.this, QRActivity.class);
                startActivity(intent);
            }
        });
    }
}
