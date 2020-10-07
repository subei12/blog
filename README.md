# blog
博客系统
**不支持搜狗浏览器**

**后台登录地址：** http://localhost:8080/blog/login.html  

**账号：admin**

**密码：123456**

**新版前台地址：** http://localhost:8085/
![index.png](https://s1.ax1x.com/2020/10/05/0tdRqP.png)
![post1.png](https://s1.ax1x.com/2020/10/05/0td2rt.png)
![post2.png](https://s1.ax1x.com/2020/10/05/0tdgKI.png)
**旧版前台地址：** http://localhost:8085/index.old
![index_old.png](https://s1.ax1x.com/2020/10/05/0twDe0.png)
![post1_old.png](https://s1.ax1x.com/2020/10/05/0twrwV.png)
![post2_old.png](https://s1.ax1x.com/2020/10/05/0tw0Lq.png)
1、修改首页显示，按日期降序排列(最新发布的在上)

2、修复json转换bug，以及随之而来的参数中bytes部分过长导致数据库(varchar=20000)无法存放的问题,参数为MultipartFile过长时提取关键信息转换为自定的json。

3、文件上传时(包含富文本编辑器)，从上传到本地修改为上传到七牛云。

4、 优化【归档】功能，优化部分部分代码结构

5、修复管理后台文章编辑时部分未展示

6、修复无效的置顶功能

7、重构前端UI，前端代码来着 https://blog.imalan.cn/ (扣的)