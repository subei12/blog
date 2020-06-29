package cn.wmkfe.blog.config;


import cn.wmkfe.blog.handler.AdminLoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter=new WebMvcConfigurerAdapter(){
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                super.addInterceptors(registry);
                registry.addInterceptor(new AdminLoginHandlerInterceptor())
                        .addPathPatterns("/admin/**")
                        .excludePathPatterns("/perfectStudent/login.html","/perfectStudent/checkLogin");
            }
        };
        return adapter;
    }

    /**
     * 设置的好一手虚拟路径啊，以/article/images替换了配置文件里的
     * my-file-upload.profile=D:/
     * my-file-upload.path=images/
     * 直接在mvcConfig里配置，还好我聪明找得到，我要替换为七牛云储存，以后部署到linux方便
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //上传的图片在F盘下的images目录下，访问路径如：http://localhost:8081/images/e03c2ff4-ae67-43c9-a77d-4b78a9b3bd23(TT0$PTFC(}RWFNM$HMLUHY.png
        //其中images表示访问的前缀。"file:F:/images/"是文件真实的存储路径
//        registry.addResourceHandler("/images/**").addResourceLocations("file:F:/images/");
        registry.addResourceHandler("/article/images/**").addResourceLocations("file:"+FileUploadConfig.getProfile()+FileUploadConfig.getPath());
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
