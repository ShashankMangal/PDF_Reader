package com.sharkBytesLab.pdfreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sharkBytesLab.pdfreader.Adapters.PdfAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private PdfAdapter adapter;
    private List<File> pdfList;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

       runTimePermission();

    }


    private void runTimePermission()
    {

        Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                try {
                    displayPdf();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                Toast.makeText(MainActivity.this, "Permission is Required", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                permissionToken.continuePermissionRequest();

            }
        }).check();

    }



    public ArrayList<File> findPdf(File file)
    {

        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for(File singleFile : files)
        {
            if(singleFile.isDirectory() && !singleFile.isHidden())
            {
                arrayList.addAll(findPdf(singleFile));
            }
            else
            {

                if(singleFile.getName().endsWith(".pdf"))
                {
                    arrayList.add(singleFile);
                }

            }
        }

        return arrayList;

    }

    private void displayPdf()
    {


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        pdfList = new ArrayList<>();
        pdfList.addAll(findPdf(Environment.getExternalStorageDirectory()));
        adapter = new PdfAdapter(this, pdfList);
        recyclerView.setAdapter(adapter);

    }

}