package cn.wmkfe.blog.model;

/**
 * @Date 2020/6/29 9:51
 * 这应该是一个原作者给head里标签赋值的类，不过我个人认为有点多此一举了吧
 */
public class MacroCommonHead {
    public String title;    //标题
    public String keywords="https://www.jsls9.top/";
    public String description;  //描述
    public String preconnect;   //关系，原做contorller里赋值时给了localhost:8080
    public String siteName;
    public String url;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreconnect() {
        return preconnect;
    }

    public void setPreconnect(String preconnect) {
        this.preconnect = preconnect;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
