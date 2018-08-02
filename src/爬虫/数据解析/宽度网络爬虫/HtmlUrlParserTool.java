package 爬虫.数据解析.宽度网络爬虫;

//  获取该页面所有URL

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUrlParserTool {
    // 获取一个 URL 中 所有 子URL

    public static Queue htmlUrlPerser(String url) throws Exception {
        Queue queue = new Queue();
        String data = new String( "\"http(.*)[\"?]") ; //http://tu.duowan.com/gallery http://tu.duowan.com/gallery/135626.html#p2
        String n ;
        Pattern ah = Pattern.compile(data);
        Matcher mr = ah.matcher(getUrlIndex(url));
        while(mr.find()) {
            n = mr.group().replaceAll("\""," ");
            n = n.replaceAll(">"," ");
            n = n.replaceAll("%"," ");
            n = n.replaceAll("#"," ");
            n = n.replaceAll("<"," ");
            n = n.replaceAll("\\("," ");
            n = n.replaceAll("\\)"," ");
            String []x = n.split(" ");
            if(!queue.contains(x[0])){
                queue.enQueue(x[0]);
            }
        }
        return queue ;
    }

    public static void main(String[] args) throws Exception {
        String url = "http://music.163.com/#/song?id=26548584";
   //     String url = "https://www.baidu.com/link?url=ppiV7jOJfzw4GB7BHKwPDodrxWBE7cq_dWiUyEAxFaKVBYOLjN6OL7geMIgsltsA&wd=&eqid=8cf6d8a700006544000000035a12a4ed";
        System.out.println(getUrlIndex(url));
    }
    //  一个URL的解析
    public static String getUrlIndex(String r) throws Exception {
        String index = new String("") ;
        URL url ;
        int responsecode;
        HttpURLConnection urlConnection;
        BufferedReader re ;
        String line;
        try{
            url = new URL(r) ;
            urlConnection = (HttpURLConnection)url.openConnection();
            responsecode=urlConnection.getResponseCode();
            if(responsecode==200){
                InputStream inputStream  = urlConnection.getInputStream();
                byte []bs = new byte[1024] ;
                int len ;
                OutputStream  outputStream = new FileOutputStream("e:\\网易.html");
                while((len=inputStream.read(bs))!=-1) {
                    outputStream.write(bs);
                    outputStream.write("\r\n".getBytes());
                }
                outputStream.flush();
                outputStream.close();
                return index ;
            }else {
                System.out.println("NOT "+responsecode);
            }
        }catch(Exception e){
            System.out.println("Exception->"+e);
        }
        return index ;
    }
}
