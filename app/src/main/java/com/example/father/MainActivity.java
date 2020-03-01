package com.example.father;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Size;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.UserManagerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reference;
    HashMap<String, HashMap<String, HashMap<String, ArrayList<item>>>> online_data;
    CountDownLatch count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output();
    }

    void output() {
        reference = FirebaseDatabase.getInstance().getReference();
        online_data = new HashMap<>();
        reference.child("account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = new CountDownLatch(Integer.parseInt(dataSnapshot.getChildrenCount() + ""));
                final DataSnapshot o = dataSnapshot;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            count.await();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        count = new CountDownLatch(16+Integer.parseInt(o.getChildrenCount() + ""));

                        // Log.e("fin", "fin");
                        out();
                    }
                }.start();
                for (final DataSnapshot temp : dataSnapshot.getChildren()) {
                    final DataSnapshot remeber = temp;
                    new Thread() {
                        @Override
                        public void run() {
                            String data = remeber.getValue().toString();
                            String[] spite = data.split(",");
                            String veder = spite[1].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                            String date = spite[0].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");

                            //System.out.println(veder);
                            //System.out.println(date);

                            for (int i = 2; i < spite.length; i += 5) {
                                String item_name = spite[i].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                                String count = spite[i + 1].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                                String unit_price = spite[i + 2].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                                String unit = spite[i + 3].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                                String sum = spite[i + 4].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                                if (online_data.get(veder) == null)
                                    online_data.put(veder, new HashMap<String, HashMap<String, ArrayList<item>>>());
                                if (online_data.get(veder).get(date) == null)
                                    online_data.get(veder).put(date, new HashMap<String, ArrayList<item>>());
                                if (online_data.get(veder).get(date).get(temp.getKey()) == null)
                                    online_data.get(veder).get(date).put(temp.getKey(), new ArrayList<item>());
                                item item = new item(item_name, count, unit_price, unit, sum);
                                online_data.get(veder).get(date).get(temp.getKey()).add(item);
                            }
                            //Log.e("count",count.getCount()+"");
                            count.countDown();
                        }

                    }.start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void out() {

        for (String v : online_data.keySet())//廠商
        {
            StringBuilder vedener = new StringBuilder();
            //Log.e(v+"",v);
            Object[] index = online_data.get(v).keySet().toArray();
            Arrays.sort(index);
            for (int i = 0; i < index.length; i++)//整理好的日期
            {
                //System.out.println(index[i]);
                for (String t : online_data.get(v).get(index[i]).keySet())//整理好的編號
                {
                    try {
                        double s = 0;
                        StringBuilder sb = new StringBuilder();
                        sb.append("廠商:" + v + "," + "日期:" + index[i] + "," + "編號:" + t + "\n\n");
                        sb.append("商品,數量,單位,單價,總價\n");
                        vedener.append("廠商:" + v + "," + "日期:" + index[i] + "," + "編號:" + t + "\n\n");
                        vedener.append("商品,數量,單位,單價,總價\n");
                        for (int p = 0; p < online_data.get(v).get(index[i]).get(t).size(); p++, sb.append("\n"), vedener.append("\n")) {
                            vedener.append(online_data.get(v).get(index[i]).get(t).get(p).toString());
                            sb.append(online_data.get(v).get(index[i]).get(t).get(p).toString());
                            s += Double.parseDouble(online_data.get(v).get(index[i]).get(t).get(p).getSum());
                        }
                        vedener.append(",,,,,總價=" + s);
                        sb.append(",,,,,總價=" + s);
                        vedener.append("\n\n");
                        new ouput_csv(sb, Environment.getExternalStorageDirectory() + File.separator + "out_csv" + File.separator + v + File.separator + index[i], MainActivity.this, t, count).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*
                    for(int p=0;p<online_data.get(v).get(index[i]).get(t).size();p++)
                    System.out.println(online_data.get(v).get(index[i]).get(t).get(p));
                     */
                }
            }
            new ouput_csv(vedener, Environment.getExternalStorageDirectory() + File.separator + "out_csv" + File.separator + v, MainActivity.this, "all_data", count).start();


        }
        new Thread() {
            @Override
            public void run() {
                try {
                    count.await();
                    Log.e("fin", "fin");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(MainActivity.this,"fin",Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}



