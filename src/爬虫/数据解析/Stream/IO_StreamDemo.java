package 爬虫.数据解析.Stream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class IO_StreamDemo {
    public static void main(String []args) throws Exception{
        String urlFile = "http://music.163.com/#/song?id=26548584" ;
        //String urlFile = "http://xueshu.baidu.com/s?wd=paperuri%3A%2837a8c2ad22f8566bacf16ea6eb78af82%29&filter=sc_long_sign&sc_ks_para=q%3D%E6%B2%B3%E5%8D%97%E5%A4%A7%E5%AD%A6%E6%A0%A1%E5%9B%AD%E6%A4%8D%E7%89%A9%E5%8C%BA%E7%B3%BB%E5%88%9D%E6%AD%A5%E5%88%86%E6%9E%90&sc_us=8073812392051282037&tn=SE_baiduxueshu_c1gjeupa&ie=utf-8" ;
        //CloseableHttpClient h = HttpClients.createDefault();
        //HttpGet httpGet = new HttpGet("http://music.163.com/#/song?id=26548584");
        //CloseableHttpResponse ch = h.execute(httpGet);
        //HttpEntity httpEntity = ch.getEntity();
        //System.out.println(EntityUtils.toString(httpEntity,"utf-8"));

        //String urlFile = "http://www.cnki.net/" ;
        URL url = new URL(urlFile) ;
        HttpURLConnection hc = (HttpURLConnection)url.openConnection();
        if(hc.getResponseCode()==200) {
            byte[] bs = new byte[1024];
            int len;
            InputStream is = hc.getInputStream();
            OutputStream os = new FileOutputStream("D:\\网易\\123.html"); // http://music.163.com/#/song?id=26548584
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
        }
//            InputStream is = hc.getInputStream();
//            File file = new File("D://url.html") ;
//            if(!file.exists()){
//                file.delete();
//            }
//            file.createNewFile();
//            OutputStream os = new FileOutputStream(file);
//            byte []bs = new byte[1024] ;
//            int data ;
//            while((data=is.read(bs))!=-1){
//                os.write(bs, 0, data);
//            }
//            os.flush();
//            is.close();
////            os.close();
//            RandomAccessFile rf = new RandomAccessFile("D:\\网易爸爸\\happy.html","rw") ;
//            String index = "" ;
//            String read = "" ;
//            while((read=rf.readLine())!=null){
//                index += read += "\r\n" ;
//            }
//            System.out.println(index);
//        }
    }
}
