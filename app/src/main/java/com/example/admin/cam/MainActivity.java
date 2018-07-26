package com.example.admin.cam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button butTake;
    ImageView img;
    boolean isGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=this.findViewById(R.id.img);
        butTake=this.findViewById(R.id.butTake);
        permis();
        butTake.setOnClickListener(this);

    }

    private void permis(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                    ||checkSelfPermission(Manifest.permission_group.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission_group.CAMERA},0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]!=PackageManager.PERMISSION_GRANTED || grantResults[1]!=PackageManager.PERMISSION_GRANTED){
            isGranted=false;
        }else {
            isGranted=true;
        }
    }

    @Override
    public void onClick(View view) {
        capture();
    }

    private void capture(){
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,5);
        }else{
            Toast.makeText(this,"wrong cam",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap img2=(Bitmap) data.getExtras().get("data");
        img.setImageBitmap(img2);
    }
}
