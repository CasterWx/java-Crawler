package 爬虫.数据解析.HttpURLConnect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUrl {
    private static GetUrl getUrl ;
    private static String index ;
    private GetUrl(String url) {
        URL u ;
        HttpURLConnection httpURLConnection ;
        BufferedReader bf ;
        String readLine  ;
        try{
            u = new URL(url) ;
            httpURLConnection = (HttpURLConnection)u.openConnection() ;
            int responsecode = httpURLConnection.getResponseCode() ;  // 返回码
            if(responsecode==200) {
                bf = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                while ((readLine = bf.readLine()) != null) {
                    index += readLine += "\r\n";
                }
            }else{
                System.out.println("NOT  "+responsecode);
            }
        }catch(Exception e){
            System.out.println("Exception->"+e);
        }
    }
    public static String getGetUrl(String url){
        getUrl = new GetUrl(url);
        return getUrl.index ;
    }

    public static void main(String[] args) {
        System.out.println(GetUrl.getGetUrl("https://www.zhihu.com/question/263398393/answer/269329988"));
    }
}
