package com.example.studentserviceclintdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.helper.FileUploader;
import com.example.studentserviceclintdemo.helper.RealPathUtil;

public class FileUploadActivity extends AppCompatActivity {

    ImageView imageView;
    Button choose_image,upload_image;

    private static final int choose_image_request_code = 101;
    Uri image_uri;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_file_upload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // find component id
        imageView = findViewById(R.id.image_view_id);
        choose_image = findViewById(R.id.button_choose_image_id);
        upload_image = findViewById(R.id.button_upload_image_id);

        // code
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,10);
                } else {
                    ActivityCompat.requestPermissions(FileUploadActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code
                FileUploader.UploadImage(FileUploadActivity.this,image_uri);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == Activity.RESULT_OK)
        {
            image_uri = data.getData();
            imageView.setImageURI(image_uri);
            /*Context context = FileUploadActivity.this;
            path = RealPathUtil.getRealPath(context,image_uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bitmap);*/

        }
    }
}