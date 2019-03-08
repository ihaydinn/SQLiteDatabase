package com.ismailhakkiaydin.sqlitedatabase;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button kaydet, getir, idBtn, guncelle, sil;
    EditText isimBilgisi, yasBilgisi, idBilgisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kaydet = findViewById(R.id.kaydet);
        getir = findViewById(R.id.verigetir);
        idBtn = findViewById(R.id.idGetir);
        guncelle = findViewById(R.id.btnGuncelle);
        sil = findViewById(R.id.btnSil);

        kaydet.setOnClickListener(this);
        getir.setOnClickListener(this);
        idBtn.setOnClickListener(this);
        guncelle.setOnClickListener(this);
        sil.setOnClickListener(this);

        isimBilgisi = findViewById(R.id.etIsim);
        yasBilgisi = findViewById(R.id.etYas);
        idBilgisi=findViewById(R.id.tvId);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.kaydet:

                String ad = isimBilgisi.getText().toString();
                String yas = yasBilgisi.getText().toString();

                try {

                    Veritabani db = new Veritabani(MainActivity.this);
                    db.baglantiAc();
                    db.isimYasBilgileri(ad, yas);
                    db.baglantiKapat();

                }catch(Exception e){
                    Dialog dia = new Dialog(this);
                    dia.setTitle("Ekleme Durumu");
                    TextView hata = new TextView(this);
                    hata.setText(e.toString());
                    dia.setContentView(hata);
                    dia.show();
                }finally {
                    Dialog dialog = new Dialog(this);
                    dialog.setTitle("Ekleme Durumu");
                    TextView tv = new TextView(this);
                    tv.setText("BAŞARILI");
                    dialog.setContentView(tv);
                    dialog.show();
                }

                break;

            case R.id.verigetir:

                Intent intent = new Intent(this, TumKayitlar.class);
                startActivity(intent);

                break;


            case R.id.idGetir:

                String id = idBilgisi.getText().toString();
                long arananId = Long.parseLong(id);
                Veritabani db = new Veritabani(MainActivity.this);
                db.baglantiAc();

                String girilenIsim = db.getName(arananId);
                String girilenYas = db.getYas(arananId);

                db.baglantiKapat();

                isimBilgisi.setText(girilenIsim);
                yasBilgisi.setText(girilenYas);

                break;

            case R.id.btnGuncelle:

                String adGuncelle = isimBilgisi.getText().toString();
                String yasGuncelle = yasBilgisi.getText().toString();
                String idGuncelle = idBilgisi.getText().toString();

                long guncellenecekId = Long.parseLong(idGuncelle);

                Veritabani dbGuncelle = new Veritabani(MainActivity.this);
                dbGuncelle.baglantiAc();
                dbGuncelle.kaydiGuncelle(guncellenecekId, adGuncelle,yasGuncelle);
                dbGuncelle.baglantiKapat();


                break;

            case R.id.btnSil:

                String silinecekKayit = idBilgisi.getText().toString();
                long silinecekId = Long.parseLong(silinecekKayit);

                Veritabani dbSil = new Veritabani(MainActivity.this);
                dbSil.baglantiAc();
                dbSil.kaydiSil(silinecekId);
                dbSil.baglantiKapat();


                break;
        }
    }
}
