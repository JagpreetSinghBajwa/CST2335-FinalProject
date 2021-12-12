package com.example.cst2335_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailNews extends AppCompatActivity {

    TextView headline;
    TextView date;
    Button saveArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_version);
        Intent intent = getIntent();
        String head = intent.getStringExtra("headline");
        String description = intent.getStringExtra("description");
        String link = intent.getStringExtra("link");
        String Date= intent.getStringExtra("date");

        headline=findViewById(R.id.head);
        date= findViewById(R.id.date);

        headline.setText(head);
        date.setText(Date);

        saveArticle = findViewById(R.id.saveNews);

        DescriptionFragment dscFragment = new DescriptionFragment(); //add description Fragment
        Bundle fragmentDesc= new Bundle();
        fragmentDesc.putString("fragmentDesc",description);
        fragmentDesc.putString("fragmentLink",link);

        dscFragment.setArguments(fragmentDesc); //pass it a bundle for information
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, dscFragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment.

        Context context = getApplicationContext();

        saveArticle.setOnClickListener(e -> {
            Intent next = new Intent(DetailNews.this, SavedNews.class);
            next.putExtra("headline", head);
            next.putExtra("description", description);
            next.putExtra("link", link);
            next.putExtra("date",Date);
            next.setAction("1");
            Toast.makeText(context,getString(R.string.confirmation),
                    Toast.LENGTH_LONG).show();
            startActivity(next);
        });

    }


}