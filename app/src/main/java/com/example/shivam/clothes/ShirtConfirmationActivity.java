package com.example.shivam.clothes;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class ShirtConfirmationActivity extends ActionBarActivity {

    ImageView shirtImage;
    TextView title;
    Button mSave,mDiscard;
    Uri imageUri;
    ShirtORM s = new ShirtORM();
    PantORM p = new PantORM();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shirt_confirmation);
        shirtImage = (ImageView)findViewById(R.id.image);
        mSave = (Button)findViewById(R.id.saveImage);
        title = (TextView)findViewById(R.id.titleText);
        mDiscard = (Button)findViewById(R.id.cancelImage);
        imageUri = getIntent().getData();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.cloth == 1) {
                    s.addImage(ShirtConfirmationActivity.this, imageUri.toString());
                    Toast.makeText(ShirtConfirmationActivity.this,"Added new Shirt !", Toast.LENGTH_SHORT).show();
                }
                else if(MainActivity.cloth == 2)
                {
                    title.setText("Add this Pant ?");
                    p.addImage(ShirtConfirmationActivity.this,imageUri.toString());
                    Toast.makeText(ShirtConfirmationActivity.this,"Added new Pant !", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(ShirtConfirmationActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        mDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShirtConfirmationActivity.this,"Discarded !", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ShirtConfirmationActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        Picasso.with(this).load(imageUri.toString()).resize(500,500).into(shirtImage);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shirt_confirmation, menu);
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
}
