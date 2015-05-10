package com.example.shivam.clothes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shivam on 09/05/15 at 12:30 PM.
 */
public class BookmarksFragment extends Fragment {

    FavoriteORM f = new FavoriteORM();
    ListView mBookmarkList;
    ArrayList<String> shirts,pants;
    ArrayList<ArrayList<String>> holder;
    BookmarksAdapter adapter;
    ImageView emptyImage;
    TextView emptyText;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_bookmarks, null);
        mBookmarkList = (ListView)root.findViewById(R.id.bookmarkList);
        emptyImage = (ImageView)root.findViewById(R.id.emptyImage);
        emptyText = (TextView)root.findViewById(R.id.emptyText);
        holder = new ArrayList<ArrayList<String>>();
        shirts = new ArrayList<>();
        pants = new ArrayList<>();
        new BookmarkTask().execute();
        return root;
    }

    public BookmarksFragment newInstance(){
        BookmarksFragment mFragment = new BookmarksFragment();
        return mFragment;
    }

    public class BookmarkTask extends AsyncTask<String,String,ArrayList<ArrayList<String>>>
    {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Retreiving Bookmarks...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(String... params) {
            shirts = f.getShirtUriFromDB(getActivity());
            pants = f.getPantUriFromDB(getActivity());
            holder.add(shirts);
            holder.add(pants);
            return holder;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> strings) {
            if(f.isEmpty(getActivity()))
            {
                emptyText.setVisibility(View.VISIBLE);
                emptyImage.setVisibility(View.VISIBLE);
                mBookmarkList.setVisibility(View.GONE);
                pDialog.dismiss();
            }
            else {
                ArrayList<String> finalShirt = shirts;
                ArrayList<String> finalPant = pants;
                adapter = new BookmarksAdapter(getActivity(), R.layout.bookmark_list_item, finalShirt, finalPant);
                mBookmarkList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                pDialog.dismiss();
            }
        }
    }



}
