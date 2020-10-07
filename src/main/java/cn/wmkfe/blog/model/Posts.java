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
 */
public class Posts {

    private String title;
    private String date;
    private String path;
    private String text;
    private List<Categories> categories;
    private List<String> tags;
    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setDate(String date) {
         this.date = date;
     }
     public String getDate() {
         return date;
     }

    public void setPath(String path) {
         this.path = path;
     }
     public String getPath() {
         return path;
     }

    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }

    public void setCategories(List<Categories> categories) {
         this.categories = categories;
     }
     public List<Categories> getCategories() {
         return categories;
     }

    public void setTags(List<String> tags) {
         this.tags = tags;
     }
     public List<String> getTags() {
         return tags;
     }

}