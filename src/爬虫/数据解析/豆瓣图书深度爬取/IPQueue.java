package 爬虫.数据解析.豆瓣图书深度爬取;

import java.util.LinkedList;

public class IPQueue {
    private static LinkedList<String> ipQueue = new LinkedList<String>() ;
    public static void setIPQueue() {
        if (ipQueue.isEmpty()) {
            ipQueue.add("212.200.126.14-8080");
            ipQueue.add("60.195.17.240-3128");
            ipQueue.add("118.187.58.34-53281");
            ipQueue.add("5.2.64.132-8080");
            ipQueue.add("221.4.133.67-53281");
            ipQueue.add("176.58.105.132-3128");
            ipQueue.add("59.63.178.203-55555");
            ipQueue.add("60.207.239.247-3128");
        }
    }
    //  获得一个代理IP和其端口
    public static String getIp(){
        setIPQueue();
        String use = ipQueue.removeFirst() ;
        ipQueue.add(use);
        return use ;
    }
}
