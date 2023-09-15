package com.prakruthi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.prakruthi.R;

import pl.droidsonroids.gif.GifImageView;

public class Pasword_Updated_Animation extends AppCompatActivity {

    TextView txt_24_hour, txt_your_pasword_updated;
    ImageView gif_pasword_updated_image;
    GifImageView gif_pasword_updated_gifimage;
    AppCompatButton btn_update_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasword_updated_animation);

        getSupportActionBar().hide();

        txt_24_hour = findViewById(R.id.txt_24_hour);
        txt_your_pasword_updated = findViewById(R.id.txt_your_pasword_updated);

        gif_pasword_updated_image = findViewById(R.id.gif_pasword_updated_image);
        gif_pasword_updated_gifimage = findViewById(R.id.gif_pasword_updated_gifimage);

        btn_update_text = findViewById(R.id.btn_update_text);

        btn_update_text.setOnClickListener(view -> {
            startActivity(new Intent(Pasword_Updated_Animation.this, Login.class));
        });




    }
}