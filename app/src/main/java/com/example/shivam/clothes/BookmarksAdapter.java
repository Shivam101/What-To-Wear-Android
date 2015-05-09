package com.example.shivam.clothes;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shivam on 10/05/15 at 3:11 AM.
 */
public class BookmarksAdapter extends BaseAdapter {

    static Context context;
    static int layoutResourceId;
    ArrayList<String> shirtUri = new ArrayList<String>();
    ArrayList<String> pantUri = new ArrayList<String>();

    public BookmarksAdapter(Context c,int layoutResourceId,ArrayList<String> shirtUri,ArrayList<String> pantUri)
    {
        this.context = c;
        this.layoutResourceId = layoutResourceId;
        this.shirtUri = shirtUri;
        this.pantUri = pantUri;
    }


    @Override
    public int getCount() {
        return shirtUri.size();
    }

    @Override
    public Object getItem(int position) {
        return pantUri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BookmarkHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new BookmarkHolder();
            holder.shirtImage = (ImageView)row.findViewById(R.id.shirtImage);
            holder.pantImage = (ImageView)row.findViewById(R.id.pantImage);
            row.setTag(holder);
        }
        else
        {
            holder = (BookmarkHolder)row.getTag();
        }

        Picasso.with(this.context).load(Uri.parse(shirtUri.get(position)).toString()).resize(300,300).into(holder.shirtImage);
        Picasso.with(this.context).load(Uri.parse(pantUri.get(position)).toString()).resize(300,300).into(holder.pantImage);
        return row;
    }

    static class BookmarkHolder
    {
        ImageView shirtImage,pantImage;
    }

}
