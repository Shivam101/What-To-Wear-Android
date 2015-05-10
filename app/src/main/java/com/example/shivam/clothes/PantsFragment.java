package com.example.shivam.clothes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shivam on 09/05/15 at 12:30 PM.
 */
public class PantsFragment extends Fragment {

    FloatingActionButton mGallery,mCamera;
    Uri imageUri,galleryUri;
    int MEDIA_TYPE_IMAGE = 1;
    int PICTURE_INTENT_CODE = 2;
    int GALLERY_INTENT_CODE = 3;
    int MEDIA_TYPE_GALLERY = 4;
    ShirtAdapter adapter;
    GridView shirtList;
    ArrayList<String> sqluri;
    ImageView emptyImage;
    TextView emptyText;
    PantORM p = new PantORM();
    FloatingActionsMenu fabMenu;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_shirts, null);
        MainActivity.cloth = 2;
        mCamera = (FloatingActionButton)root.findViewById(R.id.fromCamera);
        shirtList = (GridView)root.findViewById(R.id.shirtsList);
        mGallery = (FloatingActionButton)root.findViewById(R.id.pickGallery);
        emptyImage = (ImageView)root.findViewById(R.id.emptyImage);
        fabMenu = (FloatingActionsMenu)root.findViewById(R.id.fab1);
        emptyText = (TextView)root.findViewById(R.id.emptyText);
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                Intent pictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = getOutputUri(MEDIA_TYPE_IMAGE);
                if (imageUri == null) {
                    Toast.makeText(getActivity(), R.string.storage_access_error, Toast.LENGTH_SHORT).show();
                } else {
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(pictureIntent, PICTURE_INTENT_CODE);
                }
            }
        });
        new PantTask().execute();

        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), GALLERY_INTENT_CODE);
                //Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(galleryIntent, GALLERY_INTENT_CODE);
            }
        });
        return root;
    }

    public class PantTask extends AsyncTask<String,String,ArrayList<String>>
    {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Looking for your pants ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            sqluri = p.getUriFromDB(getActivity());
            return sqluri;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            if(p.isEmpty(getActivity()))
            {
                shirtList.setVisibility(View.GONE);
                emptyText.setVisibility(View.VISIBLE);
                emptyImage.setVisibility(View.VISIBLE);
                pDialog.dismiss();
            }
            else {
                ArrayList<String> uris = sqluri;
                adapter = new ShirtAdapter(getActivity(), R.layout.shirt_list_item, uris);
                shirtList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                pDialog.dismiss();
            }
        }
    }

    private Uri getOutputUri(int mediaType) {
        if (hasExternalStorage()) {
            // get external storage directory
            String appName = getActivity().getString(R.string.app_name);
            File extStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),appName);
            if(!extStorageDir.exists())
            {
                if(!extStorageDir.mkdirs())
                {
                    Toast.makeText(getActivity(), "Failed to create directory", Toast.LENGTH_SHORT).show();
                }
            }
            File mFile;
            Date mCurrentDate = new Date();
            String mTimestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(mCurrentDate);
            String path = extStorageDir.getPath() + File.separator;
            if(mediaType == MEDIA_TYPE_IMAGE) {
                mFile = new File(path + "CLOTHIMG_" + mTimestamp + ".jpg");
            }
            else
            {
                return null;
            }
            return Uri.fromFile(mFile);
        } else {
            return null;
        }
    }
    private boolean hasExternalStorage() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    public PantsFragment newInstance(){
        PantsFragment mFragment = new PantsFragment();
        return mFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICTURE_INTENT_CODE)
        {
            Intent galleryAddIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            galleryAddIntent.setData(imageUri);
            getActivity().sendBroadcast(galleryAddIntent);
            Intent sendIntent = new Intent(getActivity(),ShirtConfirmationActivity.class);
            sendIntent.setData(imageUri);
            startActivity(sendIntent);
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == GALLERY_INTENT_CODE && data!=null)
        {
            Intent galleryAddIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            galleryUri = data.getData();
            galleryAddIntent.setData(galleryUri);
            getActivity().sendBroadcast(galleryAddIntent);
            Intent sendIntent = new Intent(getActivity(),ShirtConfirmationActivity.class);
            sendIntent.setData(galleryUri);
            startActivity(sendIntent);
        }
        else if(resultCode != Activity.RESULT_CANCELED)
        {
            Toast.makeText(getActivity(), "There was an error", Toast.LENGTH_SHORT).show();
        }
    }
}
