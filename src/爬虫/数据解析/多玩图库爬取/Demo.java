package 爬虫.数据解析.多玩图库爬取;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MyFrame extends Frame{
    public Frame fr ;
    public Button bu ;
    public TextField tf ;  // 网站
    public Choice xi ;    // 需要获取的图片格式
    public TextField ta ;  // 存放路径
    public TextArea zhuang ;  // 状态
    public TextArea index ;  // 状态
    public MyFrame(){
        fr = new Frame("快图");
        bu = new Button("Get");
        tf = new TextField();
        xi = new Choice();
        ta = new TextField();
        index = new TextArea();
        zhuang = new TextArea();
        xi.add("jpg");
        xi.add("png");
        xi.add("gif");

        fr.setLayout(null);
        fr.add(index);
        fr.add(bu);
        fr.add(tf);
        fr.add(xi);
        fr.add(ta);
        fr.add(zhuang);
        fr.setBounds(120,80,1600,900);
        index.setBounds(400,70,1150,800);
        bu.setBounds(160,260,60,30);
        tf.setBounds(60,70,300,50);
        ta.setBounds(60,180,300,50);
        xi.setBounds(250,140,100,30);
        zhuang.setBounds(60,300,270,480);
        fr.setResizable(false);
        fr.setVisible(true);
    }
}
public class Demo {
    public static int num = 1 ;
    static final MyFrame my = new MyFrame();
    public static void main(String []args) throws Exception {
        my.fr.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
        my.bu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String in = getUrlIndex(my.tf.getText());
                    my.index.setText(in);
                    getThisIndexPngUrl(in,my.xi.getSelectedItem(),my.ta.getText());
                    my.zhuang.append("over！");
                }catch (Exception x){
                }
            }
        });
    }
    public static void getThisIndexPngUrl(String index,String geshi,String Lujing) throws MalformedURLException {
        //<img src="http://s1.dwstatic.com/group1/M00/CA/29/2c4fd8e53ec4fd81f20420b19df7a3dd.jpg"
        String data = new String( "img src=\\\"http(.*)."+geshi+"\\\"") ; //http://tu.duowan.com/gallery http://tu.duowan.com/gallery/135626.html#p2

        Pattern ah = Pattern.compile(data);
        Matcher mr = ah.matcher(index);
        while(mr.find()) {
             String arr[] = mr.group().split("\\\"");
             my.zhuang.append(arr[1]+"\r\n");
            saveJpg(arr[1],Lujing);
        }
    }
    public static void saveJpg(String u,String Lujing) throws MalformedURLException {
        my.zhuang.append("DownLoading..."+"the "+num+" "+my.xi.getSelectedItem()+"\r\n");
        String index = new String("") ;
        URL url ;
        int responsecode;
        my.zhuang.append(u+"\r\n");
        HttpURLConnection urlConnection;
        String line;
        try{
                url = new URL(u );
                urlConnection = (HttpURLConnection) url.openConnection();
                responsecode = urlConnection.getResponseCode();

                if (responsecode == 200) {
                    byte[] bs = new byte[1024];
                    int len;
                    InputStream br = urlConnection.getInputStream();
                    OutputStream os = new FileOutputStream(Lujing+"\\" + num + ".jpg");
                    num++;
                    while ((len = br.read(bs)) != -1) {
                        os.write(bs, 0, len);
                    }
                    os.close();
                } else {
                    System.out.println("NOT " + responsecode);
                    return;
                }
        }catch(Exception e){
            System.out.println("Exception->"+e);
        }
    }
    public static String getUrlIndex(String r) throws Exception {
        String index = new String("") ;
        URL url ;
        int responsecode;
        HttpURLConnection urlConnection;
        BufferedReader re ;
        String line;
        try{
            url = new URL(r) ;
            urlConnection = (HttpURLConnection)url.openConnection();
            responsecode=urlConnection.getResponseCode();

            if(responsecode==200){
                re = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
                while((line=re.readLine())!=null)
                {
                    index+=line ;
                    index+="\n" ;
                }
                return index ;
            }else {
                System.out.println("NOT "+responsecode);
            }
        }catch(Exception e){
            System.out.println("Exception->"+e);
        }
        return index ;
    }
}