package com.example.shivam.clothes;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Downloader;

import org.json.JSONException;
import org.json.JSONObject;


public class FacebookSignUpActivity extends ActionBarActivity {

    EditText mUsername, mPassword;
    Button mConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_sign_up);
        mUsername = (EditText) findViewById(R.id.usernameET);
        mPassword = (EditText) findViewById(R.id.passwordET);
        mConfirm = (Button) findViewById(R.id.register);
        mConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String user_username = mUsername.getText().toString();
                String user_password = mPassword.getText().toString();
                user_username = user_username.trim();
                user_password = user_password.trim();
                if (user_password.isEmpty() || user_username.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            FacebookSignUpActivity.this);
                    builder.setMessage("Couldn't sign up !");
                    builder.setTitle("Oops !");
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    currentUser.setUsername(user_username);
                    currentUser.setPassword(user_password);
                    currentUser.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            // TODO Auto-generated method stub
                            if (e == null) {
                                Intent i = new Intent(
                                        FacebookSignUpActivity.this,
                                        MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        FacebookSignUpActivity.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle("Couldn't sign up !");
                                builder.setPositiveButton(android.R.string.ok,
                                        null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_facebook_sign_up, menu);
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
