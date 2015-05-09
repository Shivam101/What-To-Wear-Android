package com.example.shivam.clothes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.shivam.clothes.BookmarksFragment;
import com.example.shivam.clothes.PantsFragment;
import com.example.shivam.clothes.R;
import com.example.shivam.clothes.ShirtsFragment;
import com.facebook.FacebookSdk;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.Parse;
import com.parse.ParseUser;

import java.util.ArrayList;

import br.liveo.interfaces.NavigationLiveoListener;
import br.liveo.navigationliveo.NavigationLiveo;


public class MainActivity extends NavigationLiveo implements NavigationLiveoListener {

    public ArrayList<String> mListNameItem;
    ParseUser currentUser;
    static int cloth = 0;

    @Override
    public void onInt(Bundle bundle) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();
        if(currentUser==null)
        {
            Intent i = new Intent(MainActivity.this, SignInActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        this.setNavigationListener(this);
        this.setDefaultStartPositionNavigation(0);
        this.removeSelectorNavigation();
        this.setColorSelectedItemNavigation(R.color.nliveo_red_colorPrimary);
        mListNameItem = new ArrayList<>();
        mListNameItem.add(0, "What to Wear");
        mListNameItem.add(1, "My Shirts");
        mListNameItem.add(2, "My Pants");
        mListNameItem.add(3, "My Bookmarks");


        ArrayList<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, R.drawable.ic_accessibility_grey_500_24dp);
        mListIconItem.add(1, R.drawable.ic_favorite_grey_500_24dp); //Item no icon set 0
        mListIconItem.add(2, R.drawable.ic_favorite_grey_500_24dp); //Item no icon set 0
        mListIconItem.add(3,R.drawable.ic_favorite_grey_500_24dp);

        ArrayList<Integer> mListHeaderItem = new ArrayList<>();
        mListHeaderItem.add(4);


        this.setFooterNavigationVisible(true);
        this.setFooterInformationDrawer("Feedback", R.drawable.ic_accessibility_grey_500_24dp);
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        //this.setFooterNavigationVisible(false);
        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);

        //this.setNavigationAdapter(mListNameItem,mListIconItem);
    }

    @Override
    public void onUserInformation() {

        currentUser = ParseUser.getCurrentUser();
        if(currentUser!=null) {
            this.mUserName.setText(currentUser.getUsername());
            this.mUserEmail.setText(currentUser.getEmail());
            this.mUserBackground.setImageResource(R.drawable.background);
        }
        else
        {
            Intent i = new Intent(MainActivity.this, SignInActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         if (id == R.id.action_settings) {
            ParseUser.logOut();
            Intent i = new Intent(MainActivity.this, SignInActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickNavigation(int position, int containerLayout) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment;
        switch (position) {
            case 0:
                this.getToolbar().setTitle("What to Wear");
                mFragment = new SuggestionFragment().newInstance();
                if (mFragment != null) {
                    mFragmentManager.beginTransaction().replace(containerLayout, mFragment).commit();
                }
                break;
            case 1:
                this.getToolbar().setTitle("My Shirts");
                mFragment = new ShirtsFragment().newInstance();
                if (mFragment != null) {
                    mFragmentManager.beginTransaction().replace(containerLayout, mFragment).commit();
                }
                break;
            case 2:
                this.getToolbar().setTitle("My Pants");
                mFragment = new PantsFragment().newInstance();
                if (mFragment != null) {
                    mFragmentManager.beginTransaction().replace(containerLayout, mFragment).commit();
                }
                break;
            case 3:
                this.getToolbar().setTitle("My Bookmarks");
                mFragment = new BookmarksFragment().newInstance();
                if (mFragment != null) {
                    mFragmentManager.beginTransaction().replace(containerLayout, mFragment).commit();
                }
                break;


        }


    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int i, boolean b) {

    }

    @Override
    public void onClickFooterItemNavigation(View view) {

    }

    @Override
    public void onClickUserPhotoNavigation(View view) {

    }
}