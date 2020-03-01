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
import java.util.concurrent.CountDownLatch;

public class ouput_csv extends Thread {
    StringBuilder sb;
    String path;
    Context context;
    String file_name;
    CountDownLatch count;
    ouput_csv(StringBuilder sb,String path,Context context,String file_name,CountDownLatch count)
    {
       this.sb=sb;
       this.path=path;
       this.context=context;
       this.file_name=file_name;
       this.count=count;
    }

    @Override
    public void run()
    {
        File Floder = new File(path);
        try
        {
            if(!Floder.exists())
                Floder.mkdirs();
            File output=new File(Floder,file_name+".csv");
            if(!output.exists())
                output.createNewFile();
            FileOutputStream os =new FileOutputStream(output);
           os.write(sb.toString().getBytes());
           os.flush();
           if(count!=null)
           {
               count.countDown();
               Log.e("count",count.getCount()+"");
           }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
