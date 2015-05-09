package com.example.shivam.clothes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Created by Shivam on 09/05/15 at 12:30 PM.
 */
public class SuggestionFragment extends Fragment {

    FloatingActionButton mGetSuggestion;
    RelativeLayout card;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_suggestion, null);
        mGetSuggestion = (FloatingActionButton)root.findViewById(R.id.getSuggestion);
        card = (RelativeLayout)root.findViewById(R.id.cardHolder);
        mGetSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SeeSuggestionActivity.class);
                startActivity(i);
            }
        });
        return root;
    }

    public SuggestionFragment newInstance(){
        SuggestionFragment mFragment = new SuggestionFragment();
        return mFragment;
    }
}
