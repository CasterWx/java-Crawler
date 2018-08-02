package 爬虫.数据解析.宽度网络爬虫;

import java.util.LinkedList;

public class Queue {
    // 队列
    private LinkedList<String> queue = new LinkedList<String>() ;
    // 加入
    public void enQueue(String t){
        queue.addLast(t);
    }
    // 移除
    public String deQueue(){
        return queue.removeFirst();
    }
    // 是否为空     空->true
    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }
    // 是否包含t   包含->true
    public boolean contains(String t){
        return queue.contains(t);
    }
}
