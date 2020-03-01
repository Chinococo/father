package com.example.father;

import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.util.Xml;
//import e.user.ibus.View.MainActivity;

//import static e.user.ibus.model.AskBusRouteTask.getServerTime;
import org.apache.commons.codec.Encoder;
import org.apache.http.Header;
import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class http_url_get_and_post extends AsyncTask<String,Integer,String> {
    HttpURLConnection connection;
    String Authentication = "chinococo";
    String resule;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        //String my_url = strings[0];
        //String methon = strings[1];
        try
        {
            URL url = new URL("https://zh.wikipedia.org/zh-tw/Hello_World");
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setRequestProperty("id=","firstHeading");
            connection.connect();
            URLConnection urlConnection = (URLConnection)url.openConnection();
            InputStream is =  connection.getInputStream();
            System.out.println(connection.getHeaderField(1));
            resule="";
            if(is!=null)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                String line;
                while((line=in.readLine())!=null)
                {
                    resule+=line+"\n";
                }

            }else
            {
                resule="null";
            }
            System.out.println(resule);
            System.out.println(connection.getRequestMethod());
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(resule);
            System.out.println(connection.getRequestMethod());
        }
      return  null;
    }
}
