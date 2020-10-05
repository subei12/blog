package cn.wmkfe.blog.controller.NewController;

import cn.wmkfe.blog.dao.ArticleMapper;
import cn.wmkfe.blog.model.Article;
import cn.wmkfe.blog.service.ArticleService;
import com.alibaba.fastjson.JSON;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bSu
 * @date 2020/9/20 - 13:06
 */
@RestController
public class NewIndexController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(value = {"init"})
    public Object index() throws ParseException {
        List<Article> articles = articleMapper.listArchives();
        return articles;
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

}
