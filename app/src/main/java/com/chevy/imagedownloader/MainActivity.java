package com.chevy.imagedownloader;

import android.os.Build;
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

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    String url = "https://sites.psu.edu/siowfa16/files/2016/10/YeDYzSR-10apkm4.jpg";
    EditText edit_url;
    Button download_button;

    String dirPath, fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random r = new Random();

        AndroidNetworking.initialize(getApplicationContext());

        image = (ImageView) findViewById(R.id.image);
        edit_url = (EditText) findViewById(R.id.edit_url);
        download_button = (Button) findViewById(R.id.download_button);

        //Path for image
        dirPath = Environment.getExternalStorageDirectory() + "/Downloaded";

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
                //Image name
                if(!url.isEmpty()) {
                    fileName = "image" + (new Random().nextInt(10000)) + url.substring(url.lastIndexOf("."));
                }
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
                                Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                                System.out.println(anError.getErrorDetail());
                            }
                        });
            }
        });
    }
}
