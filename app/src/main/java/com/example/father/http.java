package com.example.father;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class http  {
    int end;
    http(int first)
    {
        this.end=first;
    }
    String dosearch(String target1,String data)
    {
        String target=target1;
        //System.out.println(data);
        int firstindex=0,endindex=0;
        end=data.indexOf(target1,end);
        target = ">";
        end=data.indexOf(target,end);
        firstindex=end+1;
        //Log.e(target,data.substring(firstindex,firstindex+10));

        target = "<";
        end=data.indexOf(target,end);
        endindex=end;
        //Log.e(target,data.substring(endindex-10,endindex));
        end = endindex+1;
        //Log.e(target1,data.substring(firstindex,endindex));
        return data.substring(firstindex,endindex);
    }

    public int get_end() {
        return end;
    }
}
