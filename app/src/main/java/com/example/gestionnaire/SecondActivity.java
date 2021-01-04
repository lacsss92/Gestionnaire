package com.example.gestionnaire;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;

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

public class SecondActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DatabaseHelper obj;
    private TextView Site;
    private TextView Email;
    private TextView Mdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Site = (TextView) findViewById(R.id.textViewSite);
        Email = (TextView) findViewById(R.id.textViewEmail);
        Mdp = (TextView) findViewById(R.id.textViewMdp);

        obj = new DatabaseHelper(this, obj.dbName, null, obj.dbVersion);

        openDB();

        queryComptesData();

    }

    public void openDB() throws SQLiteException {
        try {
            db = obj.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = obj.getReadableDatabase();
        }
    }

    private void queryComptesData() {
        // Using man made query
        // The projection define what are the column you want to retrieve
        String[] projections = new String[] { obj.colSite,
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
        Cursor cursor = db.query(obj.Comptes, projections, selection,
                selectionArg, groupBy, having, orderBy, maxResultsListSize);
        displayComptesData(cursor);

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
    private void displayComptesData(Cursor cursor) {
        // then browse the result:
        if (cursor.moveToFirst()) {
            // The elements to retrieve
            String site;
            String email;
            String mdp;
            // The associated index within the cursor
            int indexSite = cursor.getColumnIndex(obj.colSite);
            int indexEmail = cursor.getColumnIndex(obj.colEmail);
            int indexMdp = cursor.getColumnIndex(obj.colMdp);
            // Browse the results list:
            Site.setText("Site\n");
            Email.setText("Email\n");
            Mdp.setText("Mdp\n");
            int count = 0;
            do {
                site = cursor.getString(indexSite);
                email = cursor.getString(indexEmail);
                mdp = cursor.getString(indexMdp);

                String siteText = Site.getText().toString();
                Site.setText(siteText+ "\n" + site);
                String emailText = Email.getText().toString();
                Email.setText(emailText+ "\n" + email);
                String mdpText = Mdp.getText().toString();
                Mdp.setText(mdpText+ "\n" + mdp);
                count++;
            } while (cursor.moveToNext());
            Toast.makeText(this,
                    "Nombre de comptes sauvegardés dans le gestionnaire : " + count,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Aucun compte sauvegardé dans le gestionnaire.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void ClickAddCompte(View view) {
        startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
    }

    public void refresh(View view) {
        queryComptesData();
    }
}


