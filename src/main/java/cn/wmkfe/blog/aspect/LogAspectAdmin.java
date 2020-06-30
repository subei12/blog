package cn.wmkfe.blog.aspect;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.SysLog;
import cn.wmkfe.blog.model.User;
import cn.wmkfe.blog.service.SysLogService;
import cn.wmkfe.blog.util.IpAdrressUtil;
import cn.wmkfe.blog.util.JacksonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qiniu.util.Json;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LogAspectAdmin {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogService sysLogService;

    /**
     * 定义切面
     */
    @Pointcut("execution(* cn.wmkfe.blog.controller.admin.*.*..*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        ReqeustLog reqeustLog = new ReqeustLog(
                request.getRequestURL().toString(),
                IpAdrressUtil.getIpAdrress(request),
                classMethod,
                joinPoint.getArgs()
        );
        logger.info("Rquest  ----- {}",reqeustLog);
    }

    @After("log()")
    public void doAfter() {
        logger.info("---------- doAfter 2 ----------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAtfertRturning(JoinPoint joinPoint,Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null){
            logger.info("Return ------ {}",result );
            return;
        }

        SysLog sysLog=new SysLog();
        //获取用户名

        sysLog.setUserName(user.getUserName());

        //获取用户ip地址
        sysLog.setIp(IpAdrressUtil.getIpAdrress(request));

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            String value = operation.value();
            sysLog.setOperation(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();

        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = null;
        try {
//            params = JacksonUtil.obj2json(args);
            /** 这里会报错https://blog.csdn.net/pange1991/article/details/79175448
             * params = JSON.toJSONString(args),加上SerializerFeature.IgnoreErrorGetter即可，有版本要求，1.2.44
             * 已解决
             * 还不如不解决，原本的代码只会在上传图片时报错，但是又try，参数为空继续向下执行，
             * 改了之后哦参数不为空了，太打了，30w，玩尼玛
             */
            params = JSON.toJSONString(args, SerializerFeature.IgnoreErrorGetter);
            if(params.length()>=10000){//数据库能放2w，我觉得没有必要，1w意思一下算了，反正是图片都会走这里

                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(args[0], SerializerFeature.IgnoreErrorGetter));

                String contentType = jsonObject.getString("contentType");
                String size = jsonObject.getString("size");
                String str="{\n" +
                        "\t\"contentType\": \""+contentType+"\",\n" +
                        "\t\"size\": "+size+",\n" +
                        "\t\"uplodeDate\": \""+new Date()+"\"\n" +
                        "}";

                params=str;

            }




        } catch (Exception e) {
            e.printStackTrace();
        }
        sysLog.setParams(params);
        //请求的时间
        sysLog.setCreateTime(new Date());

        sysLogService.saveSysLog(sysLog);

        logger.info("存入数据 ------ {}",sysLog );
        logger.info("返回数据 ------ {}",result );


    }


    private class ReqeustLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ReqeustLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ReqeustLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
