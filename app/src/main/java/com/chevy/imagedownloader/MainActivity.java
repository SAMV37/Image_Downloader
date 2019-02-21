package com.chevy.imagedownloader;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    String url;
    EditText edit_url;
    Button download_button;

    String dirPath, fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());

        image = (ImageView) findViewById(R.id.image);
        edit_url = (EditText) findViewById(R.id.edit_url);
        download_button = (Button) findViewById(R.id.download_button);

        //Path for image
        dirPath = Environment.getExternalStorageDirectory() + "/Downloaded";

        //Image name
        fileName = "image.jpg";

        //Working on Edit_text text change
        edit_url.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                url = s.toString();
                if(!url.isEmpty()) {
                    Picasso.get().load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(image);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        //Working on button click
        download_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.download(url, dirPath, fileName)
                        .build()
                        .startDownload(new DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                Toast.makeText(MainActivity.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
