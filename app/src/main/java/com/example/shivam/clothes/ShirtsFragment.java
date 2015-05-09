package com.example.shivam.clothes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shivam on 09/05/15 at 12:30 PM.
 */
public class ShirtsFragment extends Fragment {

    FloatingActionButton mGallery,mCamera;
    Uri mMediaUri;
    int MEDIA_TYPE_IMAGE = 1;
    int PICTURE_INTENT_CODE = 2;
    int GALLERY_INTENT_CODE = 3;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_shirts, null);
        mCamera.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            mMediaUri = getOutputUri(MEDIA_TYPE_IMAGE);
            if (mMediaUri == null) {
                Toast.makeText(getActivity(), R.string.storage_access_error, Toast.LENGTH_SHORT).show();
            } else {
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                startActivityForResult(pictureIntent, PICTURE_INTENT_CODE);
                }
            }
        });

        /*mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_INTENT_CODE);
            }
        });*/
        return root;
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


    public ShirtsFragment newInstance(){
        ShirtsFragment mFragment = new ShirtsFragment();
        return mFragment;
    }
}
