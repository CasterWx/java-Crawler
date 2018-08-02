package 爬虫.数据解析.vip;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.RandomAccessFile;

public class MyClawer extends Thread {

    @Override
    public void run() {
    }
    @Test
    public void cxa() {
        String fristUrl = "http://www.vip.com" ;
        HtmlURLParserTool htmlURLParserTool = new HtmlURLParserTool() ;
        Queue queue = htmlURLParserTool.getThisHTMLParser(htmlURLParserTool.getThisURLParser(fristUrl)) ;
//        htmlURLParserTool.getThisHTMLParser(htmlURLParserTool.getThisURLParser("url下对应网页源代码"));
        LinkQueue.addVisitedUrl(fristUrl);
        while (!queue.isQueueEmpty()) {
            String oneUrl = queue.deQueue();
            LinkQueue.addUnvisitedUrl(oneUrl);
        }
        System.out.println("已访问："+LinkQueue.getVisitedUrlNum());
        LinkQueue.getVisitedUrlNum();
        while (!LinkQueue.unVisitedUrlEmpty()) {
            String url = LinkQueue.unVisitedUrlDeQueue();
            LinkQueue.addVisitedUrl(url);

            Queue newQ = htmlURLParserTool.getThisHTMLParser(htmlURLParserTool.getThisURLParser(url)) ;
            while (!newQ.isQueueEmpty()) {
                String oneUrl = newQ.deQueue();
                LinkQueue.addUnvisitedUrl(oneUrl);
            }
        }
    }

    @Test
    public void cat(){
            String fristUrl = "http://www.vip.com" ;
            HtmlURLParserTool htmlURLParserTool = new HtmlURLParserTool() ;
            Queue queue = htmlURLParserTool.getThisHTMLParser(htmlURLParserTool.getThisURLParser(fristUrl)) ;
            while(!queue.isQueueEmpty()){
                LinkQueue.addUnvisitedUrl(queue.deQueue());
            }
            LinkQueue.addVisitedUrl(fristUrl);

            while(LinkQueue.unVisitedUrlEmpty()){
                String url = LinkQueue.unVisitedUrlDeQueue() ;
                try{
                    RandomAccessFile randomAccessFile = new RandomAccessFile("E:\\get.txt","rw") ;
                    randomAccessFile.seek(randomAccessFile.getFilePointer());
                    randomAccessFile.write((url+"\r\n").getBytes());
                    randomAccessFile.close();
                }catch (Exception e){
                    System.out.println("IO exception "+ e);
                }
                Queue qu = htmlURLParserTool.getThisHTMLParser(htmlURLParserTool.getThisURLParser(url));
                while(!qu.isQueueEmpty()){
                    LinkQueue.addUnvisitedUrl(qu.deQueue());
                }
                LinkQueue.addVisitedUrl(fristUrl);
            }
        }
}
