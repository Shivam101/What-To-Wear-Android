package com.example.shivam.clothes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFlat;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;


public class SignInActivity extends ActionBarActivity {

    EditText mUsername,mPassword;
    ButtonFlat mRegister;
    Button mSignIn,mFacebookSignIn;
    Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mUsername = (EditText)findViewById(R.id.usernameET);
        mPassword = (EditText)findViewById(R.id.passwordET);
        mRegister = (ButtonFlat)findViewById(R.id.register);
        mSignIn = (Button)findViewById(R.id.done);
        mFacebookSignIn = (Button)findViewById(R.id.facebookSignIn);
        mFacebookSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInActivity.this.progressDialog=ProgressDialog.show(SignInActivity.this, "", "Logging In...", true);
                List<String> permissions = Arrays.asList("user_about_me", "email", "user_hometown", "user_location", "user_friends");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(SignInActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if(e==null)
                        {
                            if(parseUser==null)
                            {
                                Log.e("STATE","here");
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle("Couldn't sign up !");
                                builder.setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else if(parseUser.isNew())
                            {
                                Log.e("STATE","here2");
                                progressDialog.dismiss();
                                Intent i = new Intent(SignInActivity.this,FacebookSignUpActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);

                            }
                            else
                            {
                                Log.e("STATE","here3");
                                progressDialog.dismiss();
                                Intent i = new Intent(SignInActivity.this,MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        }
                        else
                        {

                        }
                    }
                });
            }
        });
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                username=username.trim();
                password=password.trim();
                if(password.isEmpty()||username.isEmpty())
                {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(SignInActivity.this);
                    builder.content("Details cannot be blank.");
                    builder.title("Oops !");
                    builder.positiveText(android.R.string.ok);
                    builder.show();
                }
                else
                {
                    SignInActivity.this.progressDialog= ProgressDialog.show(SignInActivity.this, "", "Signing In...", true);
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e == null) {
                                SignInActivity.this.progressDialog.dismiss();
                                Intent i = new Intent(SignInActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                SignInActivity.this.progressDialog.dismiss();
                                MaterialDialog.Builder builder = new MaterialDialog.Builder(SignInActivity.this);
                                builder.content("Couldn't Sign In! Try again");
                                builder.title("Ouch !");
                                builder.positiveText(android.R.string.ok);
                                builder.show();
                            }
                        }
                    });
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
