package 爬虫.数据解析.Book ;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetBook {

    public static String getBookUrlCode(String url) throws IOException {
        URL u ;
        HttpURLConnection httpURLConnection ;
        String ret = "" ;
        try{
            u = new URL(url);
            httpURLConnection = (HttpURLConnection)u.openConnection() ;
            if(httpURLConnection.getResponseCode()==200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8")) ;

                String read ;
                while((read=bufferedReader.readLine())!=null){
                    ret += read ;
                    ret+="\r\n" ;
                }
            }
        }catch (Exception e){

        }
        return ret ;
    }


    public static ArrayList getBookClass(String read){
        ArrayList<String> arrayList = new ArrayList<String>() ;
        Document doc = Jsoup.parse(read);
        Elements elements = doc.select("a");
        for(Element element : elements){
            String aurl = element.attr("href") ;
            String con = "/categories(.*)" ;
            Pattern ah = Pattern.compile(con);
            Matcher mr = ah.matcher(aurl);
            while(mr.find()){
                if(!arrayList.contains(mr.group())){
                    arrayList.add(mr.group());
                }
            }
        }
        return  arrayList ;
    }

    public static ArrayList getBook(String read){
        ArrayList<String> arrayList = new ArrayList<String>() ;

        String con = "<a href=(.*)<img src=\"/images/download" ;
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(read);
        while(mr.find()) {
            if (!arrayList.contains(mr.group())) {
                arrayList.add(mr.group());
            }
        }
        return  arrayList ;
    }
    public static void find(String read){
        String con = "<a href=\"(.*)pan.baidu.com(.*)ref" ;
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(read);
        while(mr.find()) {
            String []bookPan = mr.group().split("\"") ;
            String bookM = bookPan[1] ;
            System.out.print(bookM+"        ");

        }
    }
    public static void getM(String read){
        String con = "密码(.*)" ;
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(read);
        while(mr.find()) {
            System.out.print(mr.group());

        }
        System.out.println();
    }
    public static void main(String[] args) throws Exception {
        ArrayList<String> arrayList= getBookClass(getBookUrlCode("http://bestcbooks.com/"));
        for(int i=0;i<arrayList.size();i++){
            String read = getBookUrlCode("http://bestcbooks.com"+arrayList.get(i));
            ArrayList<String> book = getBook(read);
            for(int j=0;j<book.size();j++){
                String[] bookIn = book.get(j).split("\"");
                String myBook = bookIn[1] ;
                String myBookCode = getBookUrlCode("http://bestcbooks.com"+myBook);
                //System.out.println(myBookCode);
                find(myBookCode);
                getM(myBookCode);
            }
        }
    }
}
