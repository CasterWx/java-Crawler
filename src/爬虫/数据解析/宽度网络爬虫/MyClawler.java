package 爬虫.数据解析.宽度网络爬虫;
// 主程序
public class MyClawler {

    public static void main(String []args) throws Exception {
        Queue q = HtmlUrlParserTool.htmlUrlPerser("https://www.vip.com/") ;
        LinkQueue.addVisitedUrl("https://www.vip.com/");
        while(!q.isQueueEmpty()){
            String oneUrl = q.deQueue() ;
            LinkQueue.addUnvisitedUrl(oneUrl);
        }
        //System.out.println("已访问："+LinkQueue.getVisitedUrlNum());

        while(!LinkQueue.unVisitedUrlEmpty()){
            String url = LinkQueue.unVisitedUrlDeQueue();
            LinkQueue.addVisitedUrl(url);
            if(url.contains("jpg")||url.contains("png")||url.contains("gif")){
                DownLoadFile.downLoadFile(url);
            }
            Queue newQ = HtmlUrlParserTool.htmlUrlPerser(url);
            while(!newQ.isQueueEmpty()){
                String oneUrl = newQ.deQueue();
                 LinkQueue.addUnvisitedUrl(oneUrl);
            }
            //System.out.println("已访问："+LinkQueue.getVisitedUrlNum());
        }

    }
}
