/**
  * Copyright 2020 bejson.com 
  */
package cn.wmkfe.blog.model;
import java.util.List;

/**
 * Auto-generated: 2020-10-07 11:40:50
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 * github
 * https://github.com/AlanDecode/Typecho-Plugin-ExSearch/blob/master/README.md
 * 抄的格式：https://blog.imalan.cn/265e103beba74013c5ed51203a70d210.json
 */
public class ExSearch {

    private List<Posts> posts;
    private List<String> pages;
    public void setPosts(List<Posts> posts) {
         this.posts = posts;
     }
     public List<Posts> getPosts() {
         return posts;
     }

    public void setPages(List<String> pages) {
         this.pages = pages;
     }
     public List<String> getPages() {
         return pages;
     }

}