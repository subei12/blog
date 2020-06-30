package cn.wmkfe.blog.util;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;

/**网上没找到合适的，所有就自己写一个上传的工具类
 * @author bSu
 * @date 2020/6/30 - 9:52
 */
@Component
@PropertySource(value = "classpath:application-dev.properties")//无效，只有能在构造器执行之前赋值才有用
public class  QiniuUtils {

    //设置好账号的ACCESS_KEY和SECRET_KEY
    @Value("${qiniu.a-c-c-e-s-s-k-e-y}")
    private static String ACCESS_KEY = "eyAAvCnEEPtiOUMi3ckW-cGqLzHMTknARrLeMyY6";
    //这两个登录七牛 账号里面可以找到
    @Value("${qiniu.s-e-c-r-e-t-k-e-y}")
    private static String SECRET_KEY = "mBIPu4iKgzGVcwgJwC0SAGusbbSUTNMdnSNbYNdr";
    //要上传的空间
    @Value("${qiniu.bucket-name")
    private static String bucketName = "su-1";
    //自有域名，方便直接返回url
    @Value("${qiniu.url}")
    private static String url="http://qn.jsls9.top/";



    // 密钥配置
    static Auth auth =null;
    //创建上传对象
    static Configuration configuration = null;
    static UploadManager uploadManager = null;


    private QiniuUtils() {
        auth = Auth.create(ACCESS_KEY,SECRET_KEY);
        configuration = new Configuration(Region.region2());//Region.region2()华南
        uploadManager = new UploadManager(configuration);
    }

    /**
     * 文件流上传
     * 流不需要手动close,执行七牛的上传put命令后会被自动关闭，
     * 也就是同一个流不能被两个七牛的api使用
     * @param inputStream
     * @return
     */
   public static String upload(FileInputStream inputStream, String newFileName){
        String result=null;
       try {
           //调用put方法上传
           Response res = uploadManager.put(inputStream,newFileName,auth.uploadToken(bucketName),null, null);
           //打印返回的信息
           result = res.bodyString();
           JSONObject jsonobject = JSONObject.parseObject(result);

           String key=jsonobject.getString("key");
           result=url+key;
           /*System.out.println("res.toString():"+res.toString());
           System.out.println("res.bodyString:"+res.bodyString());*/
       } catch (QiniuException e) {
           e.printStackTrace();
           // 请求失败时打印的异常的信息
           result = "no";
       }

        return result;
   }

}
