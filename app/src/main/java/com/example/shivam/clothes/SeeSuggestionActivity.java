package com.example.shivam.clothes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class SeeSuggestionActivity extends ActionBarActivity {

    String shirtUri,pantUri;
    ShirtORM s = new ShirtORM();
    PantORM p = new PantORM();
    ArrayList<String> holder;
    ImageView randomShirt,randomPant;
    FloatingActionButton mFavorite;
    int flag = 0;
    Button mShare,mDislike;
    RelativeLayout rv;
    FavoriteORM f = new FavoriteORM();
    String mPath;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_suggestion);
        holder = new ArrayList<>();
        randomPant = (ImageView)findViewById(R.id.randomPant);
        randomShirt = (ImageView)findViewById(R.id.randomShirt);
        rv =(RelativeLayout)findViewById(R.id.card);
        mFavorite = (FloatingActionButton)findViewById(R.id.fab1);
        mShare = (Button)findViewById(R.id.shareButton);
        mDislike = (Button)findViewById(R.id.dislikeButton);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPath = takeScreenshot();
//                Uri uri = Uri.fromFile(new File(mPath));
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                Uri screenshotUri = uri;
//                sharingIntent.setType("image/*");
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, "body text");
//                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
//                startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                //View v1 = getWindow().getDecorView().getRootView();
                 View v1 = rv.getRootView(); //even this works
                // View v1 = findViewById(android.R.id.content); //this works too
                // but gives only content
                v1.setDrawingCacheEnabled(true);
                bitmap = v1.getDrawingCache();
                saveBitmap(bitmap);

            }
        });
        mDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SuggestionTask().execute();
            }
        });
        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0)
                {
                    flag = 1;
                    mFavorite.setIcon(R.drawable.ic_favorite_white_24dp);
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

    public void saveBitmap(Bitmap bitmap) {
        String filePath = Environment.getExternalStorageDirectory()
                + File.separator + "Pictures/screenshot.png";
        File imagePath = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            sendMessage(filePath);
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public void sendMessage(String path)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri myUri = Uri.parse("file://" + path);
        Uri screenshotUri = myUri;
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Let's wear this today !");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(sharingIntent, "Share image using"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_see_suggestion, menu);
        return true;
    }

    public String takeScreenshot()
    {
        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + "SCREEN";
        View v1 = findViewById(R.id.card);
        v1 = v1.getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        return mPath;
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
            shirtUri = s.getRandomUri(SeeSuggestionActivity.this);
            pantUri = p.getRandomUri(SeeSuggestionActivity.this);
            holder.add(shirtUri);
            holder.add(pantUri);
            return holder;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            Picasso.with(SeeSuggestionActivity.this).load(Uri.parse(shirtUri).toString()).resize(300,300).placeholder(R.drawable.placeholder).into(randomShirt);
            Picasso.with(SeeSuggestionActivity.this).load(Uri.parse(pantUri).toString()).resize(300,300).placeholder(R.drawable.placeholder).into(randomPant);
            pDialog.dismiss();
        }
    }
}
