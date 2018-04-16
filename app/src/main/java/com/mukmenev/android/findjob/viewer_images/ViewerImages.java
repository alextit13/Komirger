package com.mukmenev.android.findjob.viewer_images;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mukmenev.android.findjob.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class ViewerImages extends AppCompatActivity {

    private PhotoView viewer_images;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer_images);

        init();
        stack();
    }

    private void init() {
        viewer_images = (PhotoView) findViewById(R.id.viewer_images);
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
    }

    private void stack() {
        if (link!=null&&!link.equals("")){
            Picasso.with(this)
                    .load(link)
                    .into(viewer_images);
        }
    }
}
