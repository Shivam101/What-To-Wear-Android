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
 * Created by Shivam on 09/05/15 at 10:58 PM.
 */
public class ShirtAdapter extends BaseAdapter {

    static Context context;
    static int layoutResourceId;
    ArrayList<String> uri = new ArrayList<String>();

    public ShirtAdapter(Context c,int layoutResourceId,ArrayList<String> uri)
    {
        this.context = c;
        this.layoutResourceId = layoutResourceId;
        this.uri = uri;
    }

    @Override
    public int getCount() {
        return uri.size();
    }

    @Override
    public Object getItem(int position) {
        return uri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ShirtHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ShirtHolder();
            holder.shirtImage = (SpecialImageView) row.findViewById(R.id.shirtImage);
            row.setTag(holder);
        } else {
            holder = (ShirtHolder) row.getTag();
        }
        Picasso.with(this.context).load(Uri.parse(uri.get(position)).toString()).resize(300, 300).into(holder.shirtImage);
        return row;

    }

    static class ShirtHolder
    {
        SpecialImageView shirtImage;
    }
}
