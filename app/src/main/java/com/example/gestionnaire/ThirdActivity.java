package com.example.gestionnaire;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DatabaseHelper obj;
    private TextView site;
    private TextView email;
    private TextView mdp;
    private ContentValues contentValues = new ContentValues();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        site = (TextView) findViewById(R.id.addDataSite);
        email = (TextView) findViewById(R.id.addDataEmail);
        mdp = (TextView) findViewById(R.id.addDataMdp);

        obj = new DatabaseHelper(this, obj.dbName, null, obj.dbVersion);

        openDB();
    }

    public void openDB() throws SQLiteException {
        try {
            db = obj.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = obj.getReadableDatabase();
        }
    }

    public void add_data(View view) {
        String Site = site.getText().toString();
        String Email = email.getText().toString();
        String Password = mdp.getText().toString();

        // Insert a new record
        // -------------------
        insertRecord(contentValues,Site ,Email, Password);
    }

    private void insertRecord(ContentValues contentValues, String new_site, String new_email, String new_mdp) {
        // Assign the values for each column.

        String countQuery = "SELECT  * FROM " + obj.Comptes;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        contentValues.put(obj.colID, count);
        contentValues.put(obj.colSite, new_site);
        contentValues.put(obj.colEmail, new_email);
        contentValues.put(obj.colMdp, new_mdp);

        // Insert the line in the database
        long rowId = db.insert(obj.Comptes, null, contentValues);

        // Test to see if the insertion was ok
        if (rowId == -1) {
            Toast.makeText(this, "Erreur lors de l'enregistrement de la nouvelle association email/mot de passe.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nouvelle association email/mot de passe enregistrée.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void addDataButton(View view) {
        String Site = site.getText().toString();
        String Email = email.getText().toString();
        String Password = mdp.getText().toString();
        insertRecord(contentValues, Site, Email, Password);

        finish();
    }

    /*public void test_incognito(View view) {
        Intent intent = new Intent("org.chromium.chrome.browser.incognito.OPEN_PRIVATE_TAB");
        startActivity(intent);
    }*/
}

