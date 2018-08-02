package 爬虫.数据解析.HttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GetUrl {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault() ;
        HttpPost httpPost = new HttpPost("https://www.zhihu.com/question/263398393/answer/269329988") ;
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost) ;
        HttpEntity httpEntity = closeableHttpResponse.getEntity() ;
        String c = EntityUtils.toString(httpEntity) ;
        System.out.println(c);
    }
}
