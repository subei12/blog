package cn.wmkfe.blog.controller.NewController;

import cn.wmkfe.blog.dao.ArticleMapper;
import cn.wmkfe.blog.model.Article;
import cn.wmkfe.blog.model.Categories;
import cn.wmkfe.blog.model.ExSearch;
import cn.wmkfe.blog.model.Posts;
import cn.wmkfe.blog.service.ArticleService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author bSu
 * @date 2020/9/20 - 13:06
 */
@RestController
public class NewIndexController {

    @Autowired
    private ArticleService articleService;
    @Resource
    private ArticleMapper articleMapper;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(value = {"init"})
    public PageInfo<Article> index(@RequestParam(defaultValue = "1") int pageIndex) throws ParseException {
        /*List<Article> articles = articleMapper.listArchives();
        return articles;*/
        PageInfo<Article> pageInfo = articleService.listArchives(pageIndex, 10);
        return pageInfo;
    }

    /**
     * 根据id查询文章详情
     * 我也服了，只能返回String，返回Map或者Article都TM直接报错被全局异常处理捕获
     * @param id
     * @return
     */
    @RequestMapping(value = {"/post/{id}"})
    public Article getPost(@PathVariable String id){
        Article article = articleService.getArticle(id);
        return article;
    }

    @RequestMapping(value = {"/exSearch"})
    public ExSearch exSearch(){
        List<Article> list = articleMapper.listArchives();
        List<Posts> posts=new ArrayList<>();
        for(Article article : list){
            Posts p=new Posts();
            p.setTitle(article.getTitle());
            p.setDate(String.valueOf(article.getCreateTime()));
            p.setPath("/post.html?id="+article.getId());
            p.setText(article.getIntroduction());
            p.setCategories(new ArrayList<Categories>());
            p.setTags(new ArrayList<String>());
            posts.add(p);
        }
        ExSearch exSearch=new ExSearch();
        exSearch.setPosts(posts);
        exSearch.setPages(new ArrayList<String>());
        return exSearch;
    }

}
