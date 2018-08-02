package 爬虫.数据解析.爬取论坛文章;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {

    public static void main(String []args) throws Exception {
        Frame fr = new Frame("天涯");
        fr.setLayout(null);
        final TextArea mulu = new TextArea() ;
        mulu.setBounds(10,120,350,450);
        final Choice wz =  new Choice();
        wz.setBounds(10,580,350,400);
        fr.add(wz);
        Button getWz = new Button("Get");
        getWz.setBounds(10,610,100,50);
        fr.add(getWz);
        final TextArea ta = new TextArea();
        ta.setBounds(380,70,1100,900);
        fr.setBounds(0,0,1500,1000);
        Button b = new Button("GET");
        final Choice xiala = new Choice() ;
        fr.add(ta);
        fr.add(mulu);
        xiala.setBounds(20,60,300,30);
        b.setBounds(30,90,50,30);
        fr.add(xiala);
        fr.add(b);
        fr.setResizable(false);

        String index = getUrlIndex("http://bbs.tianya.cn/m/block.jsp");
        // 	 			<li><a href="/m/list-100-1.shtml">*/
        String ur = "<a href=\\\"/m/(.*)</a>" ;
        Pattern bn = Pattern.compile(ur);
        Matcher bmr = bn.matcher(index);
        while(bmr.find()){
            xiala.add(bmr.group());
        }

        fr.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
        getWz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText("");
                String wzlj = wz.getSelectedItem();
                try{
                    if(!wzlj.equals("")){
                        String wzIndex =getUrlIndex("http://bbs.tianya.cn" + wzlj);
                        ta.setText(wzIndex);
                    }
                }catch(Exception x){
                }
            }
        });
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                ta.setText("");
                mulu.setText("");
                wz.removeAll();
                String chose = xiala.getSelectedItem();
                String []arr = chose.split("\\\"");
                try {
                    String muluIndex = getUrlIndex("http://bbs.tianya.cn" + arr[1]);
                    ta.setText(muluIndex);
                    String shum = "<a href=\\\"/m/post-(.*).shtml\\\">" ;
                    /*<a href="/m/post-feeling-4295853-1.shtml">
						<div class="p-title">
                   <a href="/m/post-funinfo-7555365-1.shtml">
						<div class="p-title">无论是什么时候,只要站在风口上,猪都能飞上天!世界这么神奇是该去看看~~

						</div>*/
                    Pattern ym = Pattern.compile(shum);
                    Matcher gl = ym.matcher(muluIndex);
                    while(gl.find()){
                        String arra[] = gl.group().split("\\\"");
                        wz.add(arra[1]);
                        mulu.append(arra[1]);
                        mulu.append("\r\n");
                    }
                }catch (Exception t){

                }
            }
        });
        fr.setVisible(true);
    }
    public static String getUrlIndex(String url) throws Exception{
        String index = "" ;
        String read = "" ;
        URL u = new URL(url);
        HttpURLConnection hc ;
        try{
            hc = (HttpURLConnection)u.openConnection() ;
            if(hc.getResponseCode()==200) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(hc.getInputStream(),"utf-8"));
                while((read=bf.readLine())!=null){
                    index += read +"\r\n" ;
                }
            }else {
                System.out.println("404 Not Found.");
            }
        }catch (Exception e){

        }
        return index ;
    }
}
