# blog
博客系统
**不支持搜狗浏览器**

**后台登录地址：** http://localhost:8080/perfectStudent/login.html  

**账号：admin**

**密码：123456**

**前台地址：** http://localhost:8080/

1、修改首页显示，按日期降序排列(最新发布的在上)

2、修复json转换bug，以及随之而来的参数中bytes部分过长导致数据库(varchar=20000)无法存放的问题,参数为MultipartFile过长时提取关键信息转换为自定的json。

3、文件上传时(包含富文本编辑器)，从上传到本地修改为上传到七牛云。