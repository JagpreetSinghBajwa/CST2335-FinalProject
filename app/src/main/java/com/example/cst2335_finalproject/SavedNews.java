package com.example.cst2335_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedNews extends AppCompatActivity {
    ListView view;
    ArrayList<NewsInformation> titles = new ArrayList<>();
    TextView headLines;
    TextView description;
    TextView link;
    TextView Date;
    NewsAdapter ns;
    ContentValues databaseValues;
    SQLiteDatabase db;
    Database dbOpener;
    NewsInformation n;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        view = findViewById(R.id.savedItems);
        view.setOnItemLongClickListener((parent, view, position, id) -> {

            androidx.appcompat.app.AlertDialog.Builder alert= new androidx.appcompat.app.AlertDialog.Builder(SavedNews.this);
            alert.setTitle("Delete");
            alert.setMessage("Do you want to delete this saved article on row"+position);
            alert.setPositiveButton("Yes", (dialog,which)->{
                db.delete(dbOpener.TABLE_NAME, dbOpener._ID + "= ?", new String[]{Long.toString(titles.get(position).getId())});
                titles.remove(position);
                ns.notifyDataSetChanged();
            });
            alert.setNegativeButton("No",(dialog,which)->dialog.dismiss());
            alert.show();
            return true;
        });

        view.setOnItemClickListener((parent, view, position, id) ->{
            String url= titles.get(position).getLink();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        n = new NewsInformation();

        dbOpener = new Database(this);
        db = dbOpener.getWritableDatabase();
        Cursor cursor = db.query(false, Database.TABLE_NAME, new String[]{Database._ID, Database.COL_HEADLINES, Database.COL_DESCRIPTION, Database.COL_URL, Database.COL_DATE}, null, null, null, null, null, null, null);
        databaseValues = new ContentValues();

        if (getIntent().getAction() == "1") {

            databaseValues.put(Database.COL_HEADLINES, getIntent().getStringExtra("headline"));
            databaseValues.put(Database.COL_DESCRIPTION, getIntent().getStringExtra("description"));
            databaseValues.put(Database.COL_URL, getIntent().getStringExtra("link"));
            databaseValues.put(Database.COL_DATE,getIntent().getStringExtra("date"));

            id = db.insert(Database.TABLE_NAME, null, databaseValues);
        }

        while (cursor.moveToNext()) {
            int headlinesColumnIndex = cursor.getColumnIndex(Database.COL_HEADLINES);
            int descriptionColumnIndex = cursor.getColumnIndex(Database.COL_DESCRIPTION);
            int linkColIndex = cursor.getColumnIndex(Database.COL_URL);
            int dateColIndex = cursor.getColumnIndex(Database.COL_DATE);
            int idColIndex = cursor.getColumnIndex(Database._ID);

            String head = cursor.getString(headlinesColumnIndex);
            String desc = cursor.getString(descriptionColumnIndex);
            String url = cursor.getString(linkColIndex);
            String date= cursor.getString(dateColIndex);
            Long id = cursor.getLong(idColIndex);


            titles.add(new NewsInformation(id, head, desc, url,date));

        }


        ns = new NewsAdapter(titles, this);
        view.setAdapter(ns);

        Toast.makeText(this,getString(R.string.saved)+" "+titles.size(),Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.drawer_menu,menu);// inflates and displays menu
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)  {

        switch (item.getItemId()) {

            case R.id.help:
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SavedNews.this);
                builder.setTitle("Instructions");
                builder.setMessage(getString(R.string.fullStory)+"\n"+  //user guide and user experience
                        getString(R.string.addFvrt)+"\n"+
                        getString(R.string.viewFvrt)+"\n"+
                        getString(R.string.mored)+"\n"+getString(R.string.removeFvrt)+"\n"+getString(R.string.view));

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("OK", (dialog, which) -> {
                });
                builder.show();
                return true;

            case R.id.more:
                final Dialog fbDialogue = new Dialog(SavedNews.this, android.R.style.Theme_Black);
                fbDialogue.setContentView(R.layout.fragment_base);
                BaseFragment dFragment = new BaseFragment(); //add a Detail Fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.More, dFragment) //Add the fragment in FrameLayout
                        .commit();//actually load the fragment.
                fbDialogue.setCancelable(true);
                fbDialogue.show();

            case R.id.contact:
                final Dialog dialogue = new Dialog(SavedNews.this, android.R.style.Theme_Black);
                dialogue.setContentView(R.layout.fragment_area);
                AreaFragment write = new AreaFragment(); //add a Detail Fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.write, write) //Add the fragment in Frame layout
                        .commit();//actually load the fragment.
                dialogue.setCancelable(true);
                dialogue.show();


            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private class NewsAdapter extends ArrayAdapter<NewsInformation>  {

        public NewsAdapter(ArrayList<NewsInformation> data, Context ctx) {
            super(ctx, 0,data);
        }

        public int getCount(){
            return titles.size();
        }


        public NewsInformation getItem(int position){
            return titles.get(position);
        }

        @SuppressLint("ViewHolder")
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = SavedNews.this.getLayoutInflater();
            View result;
            result = inflater.inflate(R.layout.activity_saved,null); //tp inflate the layout

            headLines = result.findViewById(R.id.savedHead);
            description=result.findViewById(R.id.savedDesc);
            link=result.findViewById(R.id.savedLink);
            Date=result.findViewById(R.id.savedDate);

            headLines.setText(getItem(position).getHeadline()  ); // get the string at position
            description.setText(getItem(position).getDescription());
            link.setText(getItem(position).getLink());
            Date.setText(getItem(position).getDate());
            return result;
        }

    }

    public class Database extends SQLiteOpenHelper  {

        // All Static variables
        // Database Version
        protected final static String DATABASE_NAME = "NewsDB";
        protected final static int VERSION_NUM = 2;
        public final static String TABLE_NAME = "News";
        public final static String COL_HEADLINES = "HEADLINES";
        public final static String COL_DESCRIPTION = "DESCRIPTION";
        public final static String COL_URL = "URL";
        public final static String COL_DATE = "DATE";
        public final static String _ID = "_id";


        public Database(Context ctx)
        {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "("+_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_HEADLINES + " text,"+ COL_DESCRIPTION  + " text,"+ COL_URL  + " text,"+ COL_DATE +" text); ");
        }
        // Upgrading database

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}