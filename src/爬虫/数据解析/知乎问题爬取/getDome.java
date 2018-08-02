package 爬虫.数据解析.知乎问题爬取;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class getDome {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault() ;
        HttpGet httpget = new HttpGet("https://www.zhihu.com/question/35474744");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpget) ;
        HttpEntity httpEntity = closeableHttpResponse.getEntity() ;
        String text = (EntityUtils.toString(httpEntity)) ;
        System.out.println(text);
    }
}
