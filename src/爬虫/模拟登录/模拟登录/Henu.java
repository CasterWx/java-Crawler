package 爬虫.模拟登录.模拟登录;


import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;


// http://xk.henu.edu.cn/cas/login.action  教务系统网页访问返回信息.
// http://xk.henu.edu.cn/cas/logon.action  点击登录后发送请求的连接，登录成功与否，根据此连接请求的返回信息确定.

public class Henu extends JFrame {
    // main
    private static JLabel imageLabel ;
    private static Button button = new Button() ;
    private static TextField jusername = new TextField();
    private static TextField jpassword = new TextField();
    private static TextField randnumber = new TextField();
    private static Frame frame = new Frame();
    private static CloseableHttpClient closeableHttpClient =  HttpClients.createDefault() ;
    public Henu() throws Exception {
        setSessionid();
        this.setLayout(null);
        this.setBounds(120,80,500,130);
        jusername.setBounds(0,10,130,30);
        jpassword.setBounds(140,10,130,30);
        randnumber.setBounds(280,10,130,30);
        button.setLabel("Login");
        button.setBounds(220,50,100,40);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = jusername.getText();
                password = jpassword.getText() ;
                randNumber = randnumber.getText();
                try {
                    Login();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.add(button);
        this.add(jusername);
        this.add(jpassword);
        this.add(randnumber);
        loadValidateCode(closeableHttpClient) ;
        imageLabel.setBounds(400,0,100,40);
        this.add(imageLabel) ;

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
        this.setResizable(false);
        this.setVisible(true);
    }
    private static void loadValidateCode(CloseableHttpClient client) {

        String url = null;
        try {
            url = SiteConfig.BASIC_VALIDATE_CODE_URL + "?dateTime=" + URLEncoder.encode(new Date().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpGet get = new HttpGet(url);
        get.addHeader("Cookie","JSESSIONID="+sessionid);

        try (CloseableHttpResponse response = client.execute(get)) {
            ByteOutputStream byteOutputStream = new ByteOutputStream();
            byteOutputStream.write(response.getEntity().getContent());
            Image  img = ImageIO.read(new ByteArrayInputStream(byteOutputStream.getBytes()));
            ImageIcon icon = new ImageIcon(img);
            imageLabel = new JLabel(icon);
            byteOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String []args) throws Exception{
        System.out.println("http://xk.henu.edu.cn/cas/genValidateCode?dateTime=Mon%20Jul%2016%202018%2013:23:56%20GMT+0800%20(%D6%D0%B9%FA%B1%EA%D7%BC%CA%B1%BC%E4)");
        Henu henu = new Henu();
    }

    // 用户名 ： username = base64encode(username+";;"+_sessionid);
    // 用户名是根据： 学号 加 两个冒号 加  sessionid值 后的字符串 ，再经过base64加密.
    //              学号+ " ::" +  sessionid
    //   sessionid值 是  在打开教务系统网站时(还没有点击登录),给定的cookie.
    private static String username = "" ;
    private static String sessionid = "" ;

    // 密码   密码是对密码进行md5加密，再对验证码进行md5加密 ，再对他们加密过后的字符串进行一次md5加密
    //      md5 ( md5(password) + md5(randnumber.toLowerCase()) );
    private static String password = "" ;

    // 验证码 ：" http://xk.henu.edu.cn/cas/genValidateCode?dateTime= " 后接一段字符串 就是验证码图片的链接 ，后接的字符串是根据当前时间来确定的
    private static String randNumber = "" ;

    // 访问：
    public static void setSessionid() throws Exception {
        // 连接登录界面（还没有点击登录的那个界面）
        URL url = new URL("http://xk.henu.edu.cn/cas/login.action") ;
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

        // 获取响应头，从中获取 Set-Cookie,再从Cookie中获取Sessionid（这个值与用户名有关）;
        String CookieData = httpURLConnection.getHeaderField("Set-Cookie") ;
        // Set-Cookie 内容一般是 JSESSIONID=2DE25086A20F71F40694C938A6DF27D3.kingo154; Path=/ 这种
        // 真正要取的是 2DE25086A20F71F40694C938A6DF27D3.kingo154
        // 不过后面可能跟了  ; Path=/  这种，要把这些去掉
        // 所需的 sessionid 就是 JSESSIONID 对应的字符串
        String arr[] = CookieData.split(";") ;
        arr[0] = arr[0].replaceAll("JSESSIONID=","");
        // 此时 arr[0] 就是 JSESSIONID 的内容了
        sessionid = arr[0] ;
        System.out.println("sessionid is :"+sessionid);
    }

    // 登录 ： 在所以信息获取完成后，进行登录
    public static void Login() throws Exception {

        // 获取验证码部分没有写

        randNumber = jusername.getText();

        // 设置点击登录按钮之后，http://xk.henu.edu.cn/cas/logon.action 发送往服务器的表单信息
        /*
        表单内容 ：
        表单不是有 value 和 key 值 吗
        value 的值 是 根据 验证码
        参考 表单数据.png(根目录下面)
        比如 输入的验证码是8968
        那么 用户名和密码 相关的表单数据 value 就是  _u + 验证码
        _u8968: MTEyMTI7O0NFQjc5M0Y2NERDQzNBMjZCOTU1RERBNTYyMzBGREJFLmtpbmdvMTU0 （加密后的）
        _p8968: 0e90199d89318059c8b6adbbf6e58370  （加密后的）
        randnumber:  验证码(无需处理)
        isPasswordPolicy:1  （这个值虽然不知道是干什么的，但是设置为 1 ）
        */
        //密码处理：
        password = MD5.string2MD5(MD5.string2MD5(password)+MD5.string2MD5(randNumber)) ;
        String p_username = "_u" + randNumber;
        String p_password = "_p" + randNumber;
        // 用户名 base64加密
        username = BASE64.toBase64(username + ";;" + sessionid);
        // 发送请求 ：
        System.out.println("p_username :"+username);
        System.out.println("p_password :"+password);
//        _u1234: MTYxMDEyMDAyMDs7RTgzNkQ1NkUwQUJCNDUwNTNFNjI1NTU1OEJFQTI1NTUua2luZ28xNTQ=
//                _p1234: 8b62ff217e50dc469a4d0f75fca19e5d
        HttpPost httpPost = new HttpPost("http://xk.henu.edu.cn/cas/logon.action");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair(p_username, username));
        nvps.add(new BasicNameValuePair(p_password, password));
        nvps.add(new BasicNameValuePair("randnumber", randNumber)); // 此处验证码暂为空
        nvps.add(new BasicNameValuePair("isPasswordPolicy", "1"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        HttpEntity he = response.getEntity();

        String index = EntityUtils.toString(he);
        // 打印返回信息
        // 401 验证码输入错误
        // 402 帐号或密码有误
        // 登录成功 好像是5000  ...好像
        System.out.println(index)  ;

    }
}
