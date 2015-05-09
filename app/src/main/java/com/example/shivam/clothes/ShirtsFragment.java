package com.example.shivam.clothes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shivam on 09/05/15 at 12:30 PM.
 */
public class ShirtsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_shirts, null);
        return root;
    }

    public ShirtsFragment newInstance(){
        ShirtsFragment mFragment = new ShirtsFragment();
        return mFragment;
    }
}
