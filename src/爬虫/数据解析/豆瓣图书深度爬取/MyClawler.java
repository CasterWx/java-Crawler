package 爬虫.数据解析.豆瓣图书深度爬取;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static 爬虫.数据解析.豆瓣图书深度爬取.DownLoadFile.downLoadFile;

// 主程序
class Cnblogs implements Runnable{
    public static void write(String read) throws Exception{
        File f = new File("src\\fangwen.txt");
        OutputStream os = new FileOutputStream(f,true);

        os.write((read+"\r\n").getBytes());
    }
    public  void  run() {
        while (!LinkQueue.unVisitedUrlEmpty()) {
            try{
                String url = LinkQueue.unVisitedUrlDeQueue();
                LinkQueue.addVisitedUrl(url);
                Queue newQ = HtmlUrlParserTool.htmlUrlPerser(url);
                while(!newQ.isQueueEmpty()){
                    String oneUrl = newQ.deQueue();
                    LinkQueue.addUnvisitedUrl(oneUrl);
                }
                System.out.println("线程 : "+Thread.currentThread().getName()+"  已访问数目 ："+LinkQueue.getVisitedUrlNum()+" 待访问队列数目 : "+LinkQueue.getUnVisitedUrlNum());
                System.out.println();
            }catch (Exception e){
            }finally {

            }
        }
    }
}
public class MyClawler {

    public static void main(String []args) throws Exception {
        try {
            File f = new File("D:\\网易\\歌曲ID.txt");
            f.delete();
        }catch (Exception e){
        }finally {
//            Queue q = HtmlUrlParserTool.htmlUrlPerser("http://www.cnblogs.com/AWCXV/p/7626366.html") ;
//            LinkQueue.addVisitedUrl("http://www.cnblogs.com/AWCXV/p/7626366.html");

            Queue q = HtmlUrlParserTool.htmlUrlPerser("http://music.163.com/#/song?id=26548584") ;
            LinkQueue.addVisitedUrl("http://music.163.com/#/song?id=26548584");
//            downLoadFile("http://music.163.com/#/song?id=26548584");
                while(!q.isQueueEmpty()){
                    String oneUrl = q.deQueue() ;
                    LinkQueue.addUnvisitedUrl(oneUrl);
            }
            //System.out.println("已访问："+LinkQueue.getVisitedUrlNum());
            int i ;
            Cnblogs cnblogs = new Cnblogs();
            for(i=0;i<100;i++){
                new Thread(cnblogs,"线程"+i).start();
            }
        }
    }
}