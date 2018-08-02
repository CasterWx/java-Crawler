package 爬虫.数据解析.BiliBili弹幕爬取 ;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getBiliBiliBofqi {
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    public static void getBofqi(String aid) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.bilibili.com/video/av" + aid + "/");
        CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        String en = EntityUtils.toString(httpEntity);
        //"cid=16496518&aid=9979006&pre_ad="
        String con = "cid=(.*)?&aid=";
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(en);
        while (mr.find()) {
            String id = mr.group();
            // 解析弹幕xml文件
            String newUrl = id.replace("cid=", "");
            String x = newUrl.replace("&aid=", "");
            if(!isInteger(x)){
                return ;
            }
            URL url  = new URL( "http://comment.bilibili.com/"+x+".xml" );
            HttpGet httpGet1 = new HttpGet("http://comment.bilibili.com/"+x+".xml");
            CloseableHttpResponse httpResponse1 = closeableHttpClient.execute(httpGet1) ;
            HttpEntity httpEntity1 = httpResponse1.getEntity() ;
            String en1 = EntityUtils.toString(httpEntity1,"utf-8") ;

            String c = "\">(.*?)<";
            Pattern a = Pattern.compile(c);
            Matcher m = a.matcher(en1);
            RandomAccessFile randomAccessFile = new RandomAccessFile("danmu.txt", "rw");
            while (m.find()) {
                String speak = m.group().replace("\">", "");
                speak = speak.replace("<", "");

                // 存储弹幕
                long len = randomAccessFile.length();
                randomAccessFile.seek(len);
                randomAccessFile.write(speak.getBytes());
                randomAccessFile.write("\r\n".getBytes());
                System.out.println(speak);
            }
            randomAccessFile.write("\r\n".getBytes());
            randomAccessFile.close();
        }
    }
    public static void main(String[] args) throws Exception {
        ArrayList<String> arrayList = new ArrayList<String>() ;
        arrayList.add("16772795");
        arrayList.add("8542373");
        arrayList.add("5112921");
        arrayList.add("1747345");
        arrayList.add("2648921");
        arrayList.add("2333333");
        arrayList.add("3771373");
        arrayList.add("17224371");
        //22060440
        arrayList.add("22060440");
        for (String avId:arrayList) {
            getBofqi(avId);
        }
    }
}


