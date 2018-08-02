package 爬虫.数据解析.电影资源爬取;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.RandomAccessFile;
import java.util.HashSet;

/*
*    不能离开 www.dy2018.com
*    队列不重复。
*    检查顺序
*
* */
public class Demo implements Runnable{
    public static HashSet<String> set = new HashSet<String>() ;
    public  void run() {
        while (!LinkQueue.unVisitedUrlEmpty()) {
            try{
                String url = LinkQueue.unVisitedUrlDeQueue();
                LinkQueue.addVisitedUrl(url);
                Queue newQ = getUrlQueue(url);
                while(!newQ.isQueueEmpty()){
                    String oneUrl = newQ.deQueue();
                    if((!set.contains(oneUrl))&&(oneUrl.indexOf("/i/")!=-1)) {
                        synchronized(new Object()) {
                            System.out.println(oneUrl);
                            set.add(oneUrl);
                            RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\dy.txt", "rw");
                            randomAccessFile.seek(randomAccessFile.length());
                            randomAccessFile.write(oneUrl.getBytes());
                            randomAccessFile.write("\r\n".getBytes());
                            randomAccessFile.close();
                        }
                    }
                    LinkQueue.addUnvisitedUrl(oneUrl);
                }
                System.out.println("线程 : "+Thread.currentThread().getName()+"  已访问数目 ："+LinkQueue.getVisitedUrlNum()+" 待访问队列数目 : "+LinkQueue.getUnVisitedUrlNum());
                System.out.println();
            }catch (Exception e){
            }
        }
    }
    public static void main(String[] args) throws Exception {
        Queue queue = getUrlQueue("http://www.dy2018.com/");
        LinkQueue.addVisitedUrl("http://www.dy2018.com/");
        while(!queue.isQueueEmpty()){
            String oneUrl = queue.deQueue() ;
            LinkQueue.addUnvisitedUrl(oneUrl);
        }
        int i ;
        Demo demo = new Demo();
        for(i=0;i<100;i++){
            new Thread(demo,"线程"+i).start();
        }

    }

    public static Queue getUrlQueue(String url) throws Exception{
        Queue queue = new Queue() ;
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url) ;
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet) ;
        HttpEntity httpEntity = closeableHttpResponse.getEntity() ;
        String index = EntityUtils.toString(httpEntity,"gb2312");

        Document doc = Jsoup.parse(index);
        Elements elements = doc.select("a");
        for(Element element : elements) {
            String aurl = element.attr("href");

            if(aurl.indexOf("webPlay")!=-1){

            }else {
                queue.enQueue("http://www.dy2018.com" + aurl);
            }
        }
        return queue ;
    }
}
