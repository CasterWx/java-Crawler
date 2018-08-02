package 爬虫.数据解析.豆瓣图书深度爬取;

//  获取该页面所有URL

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HtmlUrlParserTool {
    // 获取一个 URL 中 所有 子URL

    public static Queue htmlUrlPerser(String url) throws Exception {
        Queue queue = new Queue();  //<a href="http://www.cnblogs.com/Moonlight-Shadow/p/3592266.html">1. 如何查看项目svn路径(2)</a>// ;
        //String data = new String( "<a href=\"http:(.*)html>") ; //http://tu.duowan.com/gallery http://tu.duowan.com/gallery/135626.html#p2
        //String n ;  www.cnblogs.com/AWCXV/
        String index = getUrlIndex(url) ;
        Document doc = Jsoup.parse(index);
        Elements elements = doc.select("a");
        for(Element element : elements){
            String aurl = element.attr("href") ;
            if(!queue.contains("http://music.163.com"+aurl)){
                String con = "/song\\?id=(.*)" ;
                Pattern ah = Pattern.compile(con);
                Matcher mr = ah.matcher(aurl);
                while(mr.find()) {
                    synchronized (HtmlUrlParserTool.class) {
                        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\网易\\歌曲ID.txt", "rw");
                        String musicId =  mr.group();
                        // 单曲 id 写入
                        // 用synchronized锁死
                        /*
                            面临问题 ：  在热评爬取处，如何利用多线程对保存歌曲ID的txt文件进行多线程取出ID并获得热评(获得热评的功能已经完成)
                                         如何将TXT进行多线程读取

                                         1. RandomAccessFile (一般方法)
                                            length() 获得文件长度, 将文件分块
                                            seek() 定位块区的起始地址。
                                            但是直接对文件进行均分，可能会导致某处不完整，既指向为id字符串中间的位置
                                            可以将此字符串省略，直接 readLine()

                                         2. nio (最好方法)
                                            .
                                            .
                                            .
                         */
                        randomAccessFile.seek(randomAccessFile.length());
                        randomAccessFile.write(musicId.getBytes());
                        randomAccessFile.write("\r\n".getBytes());
                        randomAccessFile.close();
                    }
                }
                queue.enQueue("http://music.163.com"+aurl);
            }
        }
        return queue ;
    }
    //  一个URL的解析
    public static String getUrlIndex(String url) throws Exception {
        CloseableHttpClient chc = HttpClients.createDefault() ;
        HttpGet httpGet = new HttpGet(url);
//        String ip = IPQueue.getIp();
//        String []ipArr = ip.split("-");
//        System.out.println(ipArr[0]+"   "+Integer.parseInt(ipArr[1]));
//        HttpHost httpHost = new HttpHost(ipArr[0],Integer.parseInt(ipArr[1]));
//        httpGet.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
//        httpGet.setHeader("accept","");
//        httpGet.setHeader("Accept-Charse","*/*");
//        httpGet.setHeader("accept-encoding","gzip, deflate, br");
        RequestConfig rc = RequestConfig.custom()
                //.setProxy(httpHost)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        httpGet.setConfig(rc);
        CloseableHttpResponse chp = chc.execute(httpGet);
        HttpEntity he = chp.getEntity();
        String index = EntityUtils.toString(he);
        Document doc = Jsoup.parse(index);
        Elements elements = doc.getElementsByTag("title");
        Element element = elements.get(0);
        Cnblogs.write(element.text()+"   "+url);
        System.out.println(element.text()+"   "+url);
        chc.close();
        return index ;
    }
}
