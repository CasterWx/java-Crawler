package 爬虫.数据解析.vip;

import java.util.HashSet;
import java.util.Set;

//  优先队列
public class LinkQueue {
    private static Set<String> visitedUrl = new HashSet<String>();
    private static Queue unVisitedUrl = new Queue();
    // 获得 URL 队列
    public static Queue getUnVisitedUrl(){
        return unVisitedUrl ;
    }
    // 添加到已访问
    public static void addVisitedUrl(String url){
        visitedUrl.add(url);
    }
    // 移除访问过的 URL
    public static void removeVisitedUrl(String url){
        visitedUrl.remove(url);
    }
    // 未访问过的 URL 出列
    public static String unVisitedUrlDeQueue(){
        return unVisitedUrl.deQueue();
    }
    // 在unVisitedUrl 加入之前判断其中是否有重复的 ， 当无重复时才做添加
    public static void addUnvisitedUrl(String url){
        if((!unVisitedUrl.contains(url))&&(url!=null)&&(!visitedUrl.contains(url))){
            unVisitedUrl.enQueue(url);
//            System.out.println(url);
        }
    }
    public static boolean hasUnvisitedUrl(String url){
        if((!unVisitedUrl.contains(url))&&(url!=null)&&(!visitedUrl.contains(url))){
            return true ;
//            System.out.println(url);
        }
        return false ;
    }
    // 已访问的数目
    public static int getVisitedUrlNum(){
        return visitedUrl.size();
    }
    // 判断 待访问队列 是否为空
    public static boolean unVisitedUrlEmpty(){
        return unVisitedUrl.isQueueEmpty();
    }
}
