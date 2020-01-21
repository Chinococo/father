package com.example.father;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button test;
   DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test=findViewById(R.id.test);
        requestpermission();
        db = FirebaseDatabase.getInstance().getReference();

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(requestpermission()==PackageManager.PERMISSION_GRANTED)
            {
             db.child("backup").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     if(dataSnapshot.getValue()!=null)
                     {
                         ouput_csv ouput_csv=new ouput_csv(dataSnapshot,MainActivity.this);
                         ouput_csv.run();
                     }

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });

            }
            //backup();
            }

        });
    }
    int requestpermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
     void backup()
     {
         db.child("account").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if(dataSnapshot.getValue()!=null)
                 {
                     for(DataSnapshot ds : dataSnapshot.getChildren())
                     {
                         Log.e("test",ds.getKey());
                         String test=(String)ds.getValue();
                         test=test.replaceAll("\\[","");
                         test=test.replaceAll("]","");
                         String[] spite=test.split(",");
                         data data=new data();
                         data.setDate(spite[0].replaceAll("\"",""));
                         data.setVendor(spite[1].replaceAll("\"",""));
                         for(int i=2;i< spite.length;i+=5)
                         {
                             item item=new item();
                             item.setItem_name(spite[i].replaceAll("\"",""));
                             item.setCount(Double.parseDouble(spite[i+1].replaceAll("\"","")));
                             item.setUnit(spite[i+2].replaceAll("\"",""));
                             String temp=spite[i+3].replaceAll("\"","");
                             temp=temp.replace("\n","");
                             item.setUnit_price(Double.parseDouble(temp));
                             item.setSum(Double.parseDouble(spite[i+4].replaceAll("\"","")));
                             data.item.add(item);
                         }
                         db.child("backup").child(ds.getKey()).setValue(data);
                     }

                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
             }
         });
     }
}
