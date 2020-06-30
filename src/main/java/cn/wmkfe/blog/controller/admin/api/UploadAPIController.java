package cn.wmkfe.blog.controller.admin.api;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.config.FileUploadConfig;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.MyUUIDUtils;
import cn.wmkfe.blog.util.QiniuUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
public class UploadAPIController {

    @Autowired
    private QiniuUtils qiniuUtils;

    //上传的时候目录也加日期，方便查找
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");

    @Operation("文件上传")
    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        Map<String,Object> map=new HashMap<>();

        map.put("code",200);
        map.put( "data",null);
        if (!uploadFile.isEmpty()) {
//            String[] split = uploadFile.getOriginalFilename().split("\\.");

            //获取文件名后缀
            String originalFilename = uploadFile.getOriginalFilename();

            String suffix = uploadFile.getOriginalFilename().substring(originalFilename.lastIndexOf("."),originalFilename.length());

            if(suffix.equals(".bmp")||suffix.equals(".jpg")||suffix.equals(".png")
                                    ||suffix.equals(".tif")||suffix.equals(".gif")
                                    ||suffix.equals(".tga")||suffix.equals(".exif")
                                    ||suffix.equals(".fpx")||suffix.equals(".svg")
                                    ||suffix.equals(".psd")||suffix.equals(".pcd")
                                    ||suffix.equals(".dxf")||suffix.equals(".ufo")
                                    ||suffix.equals(".eps")||suffix.equals(".ai")
                                    ||suffix.equals(".WMF")||suffix.equals(".webp")){
                //新文件名
//                String newFileName = UUID.randomUUID()+ uploadFile.getOriginalFilename();
                String newFileName = "images/"+sdf.format(new Date())+MyUUIDUtils.getUUID()+suffix;

                //七牛云使用开始

                //util上传
                FileInputStream fileInputStream=(FileInputStream)uploadFile.getInputStream();
                System.out.println("---------util---------");
                String url = qiniuUtils.upload(fileInputStream, newFileName);
                System.out.println("result:"+url);
                if(url!="no"){
                    map.put( "msg",ConstantValue.SUCCESS);
                    map.put( "data",url);
                    return map;
                }
                map.put("code",500);
                map.put( "msg","文件上传失败！");


                //七牛云使用结束



                /*//文件保存目录，原作者的上传到本地指定路径
                String filePath = FileUploadConfig.getProfile()+FileUploadConfig.getPath();
                File dest = new File(filePath);
                if (!dest.exists()){
                    dest.mkdirs();
                }
                try {
                    uploadFile.transferTo(new File(filePath+newFileName));


                    map.put( "msg",ConstantValue.SUCCESS);
                    map.put( "data","/article/images/"+newFileName);

                    return map;
                } catch (IOException e) {
                    map.put("code",500);
                    map.put( "msg","文件上传失败！");
                    e.printStackTrace();
                }*/

            }

        }

        return map;
    }
    @Operation("富文本编辑器图片上传")
    @PostMapping("/uploadEditor")
    public Map<String,Object> uploadEditor(@RequestParam("file[]")MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        //总数据
        Map<String,Object> map=new HashMap<>();
        //data
        Map<String,Object> dataMap=new HashMap<>();
        //data内的errMap  sccMap
        Map<String, Object> filename = new HashMap<>();
        if (!uploadFile.isEmpty()) {
            //获取文件名后缀
            String originalFilename = uploadFile.getOriginalFilename();

            String suffix = uploadFile.getOriginalFilename().substring(originalFilename.lastIndexOf("."),originalFilename.length());
            if(suffix.equals(".bmp")||suffix.equals(".jpg")||suffix.equals(".png")
                                    ||suffix.equals(".jpeg")||suffix.equals(".gif")
                                    ||suffix.equals(".png")||suffix.equals(".bmp")
                                    ||suffix.equals(".webp")){
                //新文件名
//                String newFileName = UUID.randomUUID()+ uploadFile.getOriginalFilename();
                String newFileName = "images/"+sdf.format(new Date())+MyUUIDUtils.getUUID()+suffix;

                //七牛云使用开始

                //util上传
                FileInputStream fileInputStream=(FileInputStream)uploadFile.getInputStream();
                System.out.println("---------util---------");
                String url = qiniuUtils.upload(fileInputStream, newFileName);
                System.out.println("result:"+url);
                if(url!="no"){
                    filename.put(originalFilename,url);
                    dataMap.put("succMap",filename);
                    map.put( "code",200);
                    map.put( "msg","图片上传成功");
                    map.put( "data",dataMap);
                    return map;
                }
                dataMap.put("errFiles",new String []{originalFilename});
                map.put( "code",202);
                map.put( "msg","图片上传失败");
                return map;

                //七牛云使用结束

                /*//文件保存目录
                String filePath = FileUploadConfig.getProfile()+FileUploadConfig.getPath();
                File dest = new File(filePath);
                if (!dest.exists()){
                    dest.mkdirs();
                }
                try {
                    uploadFile.transferTo(new File(filePath+newFileName));
                    filename.put(originalFilename,"/article/images/"+newFileName);
                    dataMap.put("succMap",filename);
                    map.put( "code",200);
                    map.put( "msg","图片上传成功");
                    map.put( "data",dataMap);
                    return map;
                } catch (IOException e) {
                    dataMap.put("errFiles",new String []{originalFilename});
                    map.put( "code",202);
                    map.put( "msg","图片上传失败");
                    e.printStackTrace();

                }*/
            }
        }
        return map;
    }
}
