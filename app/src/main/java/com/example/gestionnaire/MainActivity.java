package com.example.gestionnaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DatabaseHelper obj;
    private EditText email;
    private EditText password;
    private ContentValues contentValues = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText)findViewById(R.id.Text_Email_Address);
        password = (EditText)findViewById(R.id.Text_Password);
        //startService(new Intent(this, BackgroundService.class));
        // Create or retrieve the database
        // -------------------------------
        obj = new DatabaseHelper(this, obj.dbName, null, obj.dbVersion);

        // open the database
        openDB();

        try {
            obj.onCreate(db);
        } catch(Exception e) {
            e.printStackTrace();
        }

        // update that line
        // ----------------
        //rowId = updateRecord(contentValues, rowId);

        // Query that line
        // ---------------
        //queryTheDatabase();

    }

    @Override
    protected void onResume() {
        super.onResume();
        openDB();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDB();
    }

    /**
     * * Open the database* *
     *
     * @throws SQLiteException
     */
    public void openDB() throws SQLiteException {
        try {
            db = obj.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = obj.getReadableDatabase();
        }
    }

    /** *Close Database */
    public void closeDB() {
        db.close();
    }

    /**
     * Insert a record
     *
     * @param contentValues
     *            (an empty contentValues)
     * @return the inserted row id
     */
    private long insertRecord(ContentValues contentValues, String new_email, String new_mdp) {
        // Assign the values for each column.

        String countQuery = "SELECT  * FROM " + obj.Table;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        contentValues.put(obj.colID, count);
        contentValues.put(obj.colEmail, new_email);
        contentValues.put(obj.colMdp, new_mdp);

        // Insert the line in the database
        long rowId = db.insert(obj.Table, null, contentValues);

        // Test to see if the insertion was ok
        if (rowId == -1) {
            Toast.makeText(this, "Erreur lors de l'enregistrement de la nouvelle association email/mot de passe.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Compte initialisé.",
                    Toast.LENGTH_LONG).show();
        }
        return rowId;
    }


    /**
     * Query a the database
     */
    private void queryLogin() {
        // Using man made query
        // The projection define what are the column you want to retrieve
        String[] projections = new String[] {
                obj.colEmail, obj.colMdp };
        // And then store the column index answered by the request (we present
        // an other way to
        // retireve data)
        final int cursorIdColNumber = 0, cursorNameColNumber = 1, cursorFirstNameColNumber = 2;
        // To add a Where clause you can either do that:
        // qb.appendWhere(Constants.KEY_COL_HAIR_COLOR+ "=blond");
        // Or that:
        String selection = null;
        String[] selectionArg = null;
        //String selection = obj.colEmail + "=?";
        //String[] selectionArg = new String[] { "pjbn1999@gmail.com" };
        // The groupBy clause:
        //String groupBy = Constants.KEY_COL_EYES_COLOR;
        String groupBy = null;
        // The having clause
        String having = null;
        // The order by clause (column name followed by Asc or DESC)
        //String orderBy = Constants.KEY_COL_AGE + "  ASC";
        String orderBy = null;
        // Maximal size of the results list
        String maxResultsListSize = "60";
        Cursor cursor = db.query(obj.Table, projections, selection,
                selectionArg, groupBy, having, orderBy, maxResultsListSize);
        displayResults(cursor);

        /*// Using the QueryBuilder
        // Create a Query SQLite object
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Constants.MY_TABLE);
        // qb.appendWhere(Constants.KEY_COL_HAIR_COLOR+ "=blond");
        qb.setDistinct(true);
        cursor = qb.query(db, projections, selection, selectionArg, groupBy,
                having, orderBy);
        displayResults(cursor); */
    }

    /**
     * Display the results stored in the cursor
     *
     * @param cursor
     */
    private void displayResults(Cursor cursor) {
        // then browse the result:
        if (cursor.moveToFirst()) {
            // The elements to retrieve
            String emailbdd;
            String mdpbdd;
            String Email = email.getText().toString();
            String Password = password.getText().toString();
            // The associated index within the cursor
            int indexEmail = cursor.getColumnIndex(obj.colEmail);
            int indexMdp = cursor.getColumnIndex(obj.colMdp);
            // Browse the results list:
            int count = 0;
            do {
                emailbdd = cursor.getString(indexEmail);
                mdpbdd = cursor.getString(indexMdp);
                if (Email.isEmpty() || Password.isEmpty() ){
                    Toast.makeText(this, "Email ou mot de passe vide.", Toast.LENGTH_SHORT).show();
                }
                if (Email.matches(emailbdd)){
                    if (Password.matches(mdpbdd)){
                        Toast.makeText(this, "Connexion réussie.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    }
                }
                else {
                    Toast.makeText(this, "Email ou mot de passe incorrect.", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(this,"Récupérer l'élément :" + email + "," + mdp + " ("+ ")", Toast.LENGTH_LONG).show();
                count++;
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Aucun compte défini, créez-en un.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /* Boutons de l'application */

    public void login (View view) {
        queryLogin();
    }

    public void add_data(View view) {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        if (Email.isEmpty() || Password.isEmpty() ){
            Toast.makeText(this, "Email ou mot de passe vide.", Toast.LENGTH_SHORT).show();
        }
        if (!Email.contains("@") ){
            Toast.makeText(this, "Format de l'email incorrect.", Toast.LENGTH_SHORT).show();
        }
        else {
            // Insert a new record
            // -------------------
            insertRecord(contentValues, Email, Password);
        }
    }
}
