package 爬虫.模拟登录.模拟登录;

import org.apache.commons.codec.binary.Base64;
import java.io.UnsupportedEncodingException;

public class BASE64 {
    public static String toBase64(String str){
        try{
            byte[] encodeBase64 = Base64.encodeBase64(str.getBytes("UTF-8"));
            return new String(encodeBase64);
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}
