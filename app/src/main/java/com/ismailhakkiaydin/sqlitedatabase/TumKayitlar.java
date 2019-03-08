package com.ismailhakkiaydin.sqlitedatabase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TumKayitlar extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);

        TextView tv = findViewById(R.id.tumIsım);
        TextView tv1 = findViewById(R.id.tumYas);

        Veritabani db= new Veritabani(TumKayitlar.this);
        db.baglantiAc();
        String tumKayitlar = db.tumKayitlar();
        String tumKayitlarYas = db.tumKayitlaryas();
        db.baglantiKapat();

        tv.setText(tumKayitlar);
        tv1.setText(tumKayitlarYas);

    }
}
