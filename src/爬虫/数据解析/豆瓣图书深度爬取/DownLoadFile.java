package 爬虫.数据解析.豆瓣图书深度爬取;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// 下载此 URL 内容
public class DownLoadFile {
    //  url对应 文件类型名
    public static String getFileNameByUrl(String url) {
        // 移除   http://
        if (url.contains("http://")) {
            url = url.substring(7);
        }
        // 获取文件类型
        return url.replaceAll("[\\?/:*|<>\"]", "");
    }
    public static String downLoadFile(String url){
        URL u ;
        HttpURLConnection hc ;
        //String filePath = "d:\\网易\\"+getFileNameByUrl(url);
        String filePath = "d:\\网易\\ca.html";
        try{
            u = new URL(url);
            hc = (HttpURLConnection) u.openConnection();
            if(hc.getResponseCode()==200){
                byte[] bs = new byte[1024];
                int len ;
                InputStream is = hc.getInputStream();
                OutputStream os = new FileOutputStream(filePath);
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                os.close();
             }
        }catch (Exception e){
        }
        return filePath ;
    }
}
