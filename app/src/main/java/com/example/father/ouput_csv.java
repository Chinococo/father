package com.example.father;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ouput_csv extends Thread {
    DataSnapshot dataSnapshot;
    File Floder= new File(Environment.getExternalStorageDirectory()+File.separator+"test");
    String name;
    Context context;
    ouput_csv(DataSnapshot ds,Context context)
    {
        this.dataSnapshot=ds;
        this.context=context;
    }

    @Override
    public void run()
    {
        super.run();
        try
        {
            if(!Floder.exists())
                Floder.mkdirs();
            File output=new File(Floder,"test.csv");
            if(!output.exists())
                output.createNewFile();
            FileOutputStream os=new FileOutputStream(output);
            StringBuilder sb=new StringBuilder();
            for(DataSnapshot ds:this.dataSnapshot.getChildren())
           {
             sb.append(ds.child("date").getValue()+","+ds.child("vendor").getValue()+"\n");
             ArrayList<HashMap<String,Object>> test=(ArrayList<HashMap<String,Object>> )ds.child("item").getValue();
             for(int i=0;i<test.size();i++)
             sb.append(test.get(i).get("item_name")+","+test.get(i).get("count")+","+test.get(i).get("unit")+","+test.get(i).get("unit_price")+","+test.get(i).get("sum")+"\n");
             sb.append("\n\n");
        }
            os.write(sb.toString().getBytes());
            os.flush();
            os.close();
            Toast.makeText(context,"successful",Toast.LENGTH_LONG).show();
            Log.e("successful","123456789");
        }catch (Exception e)
        {
            Toast.makeText(context,"unsuccessful",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
