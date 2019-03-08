package com.ismailhakkiaydin.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Veritabani {

    private static final String DATABASE_ISIM = "ornekdb";
    private static final String DATABASE_TABLO = "vatandaslar";
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_ROW_ID = "_id";
    private static final String KEY_ISIM = "isim";
    private static final String KEY_YAS = "yas";

    private final Context context;
    private VeritabaniHelper veritabaniHelper;
    private SQLiteDatabase veritabani;


    public Veritabani(Context c){
        this.context=c;
    }

    public Veritabani baglantiAc() throws SQLException {

        veritabaniHelper = new VeritabaniHelper(context);
        veritabani=veritabaniHelper.getWritableDatabase();

        return this;
    }

    public void baglantiKapat(){

        veritabaniHelper.close();

    }

    public void isimYasBilgileri(String ad, String yas) {

        ContentValues cv = new ContentValues();

        cv.put(KEY_ISIM,ad);
        cv.put(KEY_YAS,yas);

        veritabani.insert(DATABASE_TABLO, null, cv);

    }

    public String tumKayitlar() {
        String[] sutunlar = new String[]{KEY_ROW_ID, KEY_ISIM, KEY_YAS};
        Cursor c = veritabani.query(DATABASE_TABLO, sutunlar, null,null,null,null,null);

        String tumIsım = " ";


        int idSiraNo = c.getColumnIndex(KEY_ROW_ID);
        int isimSiraNo = c.getColumnIndex(KEY_ISIM);
       // int yasSiraNo = c.getColumnIndex(KEY_YAS);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            tumIsım = tumIsım + c.getString(idSiraNo) + " "+ c.getString(isimSiraNo)+ " " +"\n";
        }



        return tumIsım ;



    }

    public String tumKayitlaryas() {

        String[] sutunlar = new String[]{KEY_ROW_ID, KEY_ISIM, KEY_YAS};
        Cursor c = veritabani.query(DATABASE_TABLO, sutunlar, null,null,null,null,null);
        String tumYas = " ";
        int yasSiraNo = c.getColumnIndex(KEY_YAS);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            tumYas = tumYas +  c.getString(yasSiraNo)+ "\n";
        }
        return tumYas ;
    }

    public String getName(long arananId) {
        String[] sutunlar = new String[]{KEY_ROW_ID, KEY_ISIM, KEY_YAS};
        Cursor c = veritabani.query(DATABASE_TABLO, sutunlar, KEY_ROW_ID + "=" + arananId,null,null,null,null);

        if (c!=null){
            c.moveToFirst();
            String isim =c.getString(1);
            return isim;
        }

        return null;
    }

    public String getYas(long arananId) {
        String[] sutunlar = new String[]{KEY_ROW_ID, KEY_ISIM, KEY_YAS};
        Cursor c = veritabani.query(DATABASE_TABLO, sutunlar, KEY_ROW_ID + "=" + arananId,null,null,null,null);

        if (c!=null){
            c.moveToFirst();
            String yas =c.getString(2);
            return yas;
        }

        return null;
    }

    public void kaydiGuncelle(long guncellenecekId, String adGuncelle, String yasGuncelle) {
        ContentValues cvGuncelle = new ContentValues();

        cvGuncelle.put(KEY_ISIM,adGuncelle);
        cvGuncelle.put(KEY_YAS,yasGuncelle);

        veritabani.update(DATABASE_TABLO, cvGuncelle, KEY_ROW_ID + "="+ guncellenecekId, null);
    }

    public void kaydiSil(long silinecekId) {
        veritabani.delete(DATABASE_TABLO, KEY_ROW_ID + "="+ silinecekId,null);
    }


    private static class VeritabaniHelper extends SQLiteOpenHelper{

        public VeritabaniHelper(Context contextim){
            super(contextim,DATABASE_ISIM,null,DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(" CREATE TABLE " + DATABASE_TABLO + "(" +
                    KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_ISIM + " TEXT NOT NULL, " +
                    KEY_YAS + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + DATABASE_TABLO);
            onCreate(sqLiteDatabase);
        }
    }

}
