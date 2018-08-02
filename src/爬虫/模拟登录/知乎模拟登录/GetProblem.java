package 知乎模拟登录;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class GetProblem {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault() ;
        HttpPost httpPost = new HttpPost("https://www.zhihu.com/login/phone_num") ;
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("_xsrf", "66653239623962342d396237632d346233332d396331362d333434386438326438616139"));
        nvps.add(new BasicNameValuePair("password", "xxxxxxxxx"));
        nvps.add(new BasicNameValuePair("captcha_type", "cn"));
        nvps.add(new BasicNameValuePair("phone_num", "xxxxxxxxx"));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost) ;
        HttpEntity entity = closeableHttpResponse.getEntity() ;
        String s = EntityUtils.toString(entity);
        System.out.println(s);
    }
}
