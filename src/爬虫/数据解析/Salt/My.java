package 爬虫.数据解析.Salt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class My {
    public static String getUrlCode(String u) throws Exception  {
        URL url = new URL(u) ;
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection() ;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8")) ;
        String read = "" ;
        String get  ;
        while((get=bufferedReader.readLine())!=null){
            read += get += "\r\n" ;
        }
        return read ;
    }
    public static void main(String []args) throws Exception {
        System.out.println(getUrlCode("https://salttiger.com/archives/")) ;
        //
    }
}
