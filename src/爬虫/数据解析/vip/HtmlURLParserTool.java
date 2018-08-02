package 爬虫.数据解析.vip;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlURLParserTool {
    /**
     *  解析目标 url
     *  @param url  vip网页链接
     *  @return url下对应网页源代码
     * */
    public String getThisURLParser(String url){
        //"https://www.vip.com/"
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        String en = "" ;
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            en = EntityUtils.toString(httpEntity);
//            System.out.println(en);
        }catch (Exception e){
            System.out.println("get "+url+" has exception "+e);
        }
        return en ;
    }
    /**
     *  解析html下所有子url
     * @param html  目标html
     * @return 返回一个队列，内容为html中所有子url (url需保证唯一性)
     * */
    public Queue getThisHTMLParser(String html){
        Queue queue= new Queue() ;
        ////beauty.vip.com/
        try{
            // 匹配原型 href="//beauty.vip.com/"
            String href = "href=\"(.*?)\"" ;
            String data = new String(href) ; //http://tu.duowan.com/gallery http://tu.duowan.com/gallery/135626.html#p2
            String n ;
            Pattern ah = Pattern.compile(data);
            Matcher mr = ah.matcher(html);
            while(mr.find()){
                String get = mr.group() ;
                get = get.replace("href=","") ;
                get = get.replace("\"","") ;
                if(!get.contains("http")) {
                    get = "https:" + get ;
                }
                if(!get.contains("javascript")&&!get.contains("{")){
                    if(!queue.contains(get)){
//                        get = get.replace("?","%3F") ;
//                        get = get.replace("&","%26") ;
//                        get = get.replace("|","%124") ;
//                        get = get.replace("=","%3D") ;
//                        get = get.replace("#","%23") ;
//                        get = get.replace("+","%2B") ;
//                        get = get.replace("%","%25") ;
//                        get = get.replace(" ","%20") ;
                        if(get.contains("vip")){
                            if(LinkQueue.hasUnvisitedUrl(get)){
                                queue.enQueue(get);
                                System.out.println(" url :" +get);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){

        }
        return queue ;
    }

}
