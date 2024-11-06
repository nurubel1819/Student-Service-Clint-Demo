package com.example.studentserviceclintdemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.helper.FileUploader;

public class FileUploadActivity extends AppCompatActivity {

    ImageView imageView;
    Button choose_image,upload_image;

    private static final int choose_image_request_code = 101;
    Uri image_uri;

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
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,choose_image_request_code);
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

        if(resultCode==RESULT_OK)
        {
            if(requestCode == choose_image_request_code)
            {
                imageView.setImageURI(data.getData());
                // upload image in server
                image_uri = data.getData();
                Toast.makeText(FileUploadActivity.this,image_uri.toString(),Toast.LENGTH_SHORT).show();
                Log.d("image_path",image_uri.toString());
            }
        }
    }
}