package 爬虫.数据解析.有道;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class You {
    public static void main(String []args) throws Exception{
    CloseableHttpClient closeableHttpClient =  HttpClients.createDefault() ;

        String url = "http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule" ;
        //String url =  "http://fanyi.youdao.com/translate?smartresult=dict&smartresult=rule&smartresult=ugc&sessionFrom=null" ;
        HttpPost httpPost = new HttpPost(url) ;

      //  String ret = "i=hello&from=AUTO&to=AUTO&smartresult=dict&client=fanyideskweb&salt=1512591808369&sign=b515d03c69e3a763f1a282e1eec7191c&doctype=json&version=2.1&keyfrom=fanyi.web&action=FY_BY_CLICKBUTTION&typoResult=false" ;
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("i","hello"));
        nvps.add(new BasicNameValuePair( "from", "AUTO"));
        nvps.add(new BasicNameValuePair("smartresult","dict" ));
        nvps.add(new BasicNameValuePair("client", "fanyideskweb"));
        nvps.add(new BasicNameValuePair("version", "2.1"));
        nvps.add(new BasicNameValuePair("keyfrom", "fanyi.web"));
        nvps.add(new BasicNameValuePair("action", "FY_BY_CLICKBUTTION"));
        nvps.add(new BasicNameValuePair("typoResult", "false"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        HttpEntity he = response.getEntity();
        String index = EntityUtils.toString(he);
        System.out.println(index)  ;
//        Scanner cin = new Scanner(System.in);
//        String fanyi = cin.next();
        // 设置表单请求
        /*
            i:hello
            from:AUTO
            to:AUTO
            smartresult:dict
            client:fanyideskweb
            salt:1512591808369
            sign:b515d03c69e3a763f1a282e1eec7191c
            doctype:json
            version:2.1
            keyfrom:fanyi.web
            action:FY_BY_CLICKBUTTION
            typoResult:false
         */
//        httpPost.setHeader("Accept","*.*");
//        httpPost.setHeader("Accept-Language","zh-CN");
//        httpPost.setHeader("Connection","keep-alive");
//        httpPost.setHeader("Host","rlogs.youdao.com");
//        httpPost.setHeader("Referer","http://fanyi.youdao.com/");
//        httpPost.setHeader("Cookie","_ntes_nnid=f2a14c6ae5bf86a8b516907cfee268fd,1503390147248; OUTFOX_SEARCH_USER_ID_NCOO=2019093289.1938834; OUTFOX_SEARCH_USER_ID=1159097090@10.169.0.83; P_INFO=assembly_2011@163.com|1504619102|0|other|00&99|hen&1504602370&other#hen&410200#10#0#0|&0|urs&mailsettings&mail163|assembly_2011@163.com; _ga=GA1.2.812825060.1504624622; JSESSIONID=aaaUaowcEoIcE-8NTlVaw; ___rl__test__cookies=1512592816460");
//        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
//
    }
}
