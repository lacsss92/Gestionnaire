package com.example.gestionnaire;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.provider.SyncStateContract;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    protected static final String dbName = "Gestionnaire.db";
    protected static final int dbVersion = 1;
    protected static final String Table = "gestionnaire";
    protected static final String Comptes = "compte";
    protected static String colID = "id";
    protected static String colEmail = "email";
    protected static String colMdp = "mot_de_passe";
    protected static String colSite = "site";
    protected static String create_bdd = "CREATE TABLE " + Table + " (" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + colEmail + " TEXT, " + colMdp + " TEXT);";
    protected static String create_bdd2 = "CREATE TABLE compte (" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+ colSite +" TEXT, " + colEmail + " TEXT, " + colMdp + " TEXT);";
    protected static String delete_bdd = "DROP TABLE IF EXISTS " + Table + ";";
    protected static String add_data = "INSER INTO "+ Table + " ("+ colEmail +", "+ colMdp +") VALUES('";
    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        // constructeur
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(create_bdd);
        db.execSQL(create_bdd2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.w("DatabaseHelper", "Mise à jour de la version " + oldVersion
                + " vers la version " + newVersion
                + ", les anciennes données seront détruites.");
        // Drop the old database
        db.execSQL(delete_bdd);
        // Create the new one
        onCreate(db);
    }
}
