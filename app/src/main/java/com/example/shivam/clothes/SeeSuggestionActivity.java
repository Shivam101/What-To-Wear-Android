package com.example.shivam.clothes;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SeeSuggestionActivity extends ActionBarActivity {

    String shirtUri,pantUri;
    ShirtORM s = new ShirtORM();
    PantORM p = new PantORM();
    ArrayList<String> holder;
    ImageView randomShirt,randomPant;
    FloatingActionButton mFavorite;
    int flag = 0;
    FavoriteORM f = new FavoriteORM();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_suggestion);
        holder = new ArrayList<>();
        randomPant = (ImageView)findViewById(R.id.randomPant);
        randomShirt = (ImageView)findViewById(R.id.randomShirt);
        mFavorite = (FloatingActionButton)findViewById(R.id.fab1);
        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0)
                {
                    flag = 1;
                    mFavorite.setImageResource(R.drawable.ic_favorite_white_24dp);
                    f.addImage(SeeSuggestionActivity.this, shirtUri, pantUri);
                    Toast.makeText(SeeSuggestionActivity.this,"Added to Bookmarks",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SeeSuggestionActivity.this,"Already added to Bookmarks",Toast.LENGTH_SHORT).show();
                }
            }
        });
        new SuggestionTask().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_see_suggestion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SuggestionTask extends AsyncTask<String,String,ArrayList<String>>
    {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SeeSuggestionActivity.this);
            pDialog.setMessage("Deciding what to wear ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected ArrayList<String> doInBackground(String... params) {
            //shirtUri = s.getRandomUri(SeeSuggestionActivity.this);
            pantUri = p.getRandomUri(SeeSuggestionActivity.this);
            holder.add(shirtUri);
            holder.add(pantUri);
            return holder;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            Picasso.with(SeeSuggestionActivity.this).load(Uri.parse(shirtUri).toString()).resize(300,300).into(randomShirt);
            Picasso.with(SeeSuggestionActivity.this).load(Uri.parse(pantUri).toString()).resize(300,300).into(randomPant);
        }
    }
}
