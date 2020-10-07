package cn.wmkfe.blog.dao;

import cn.wmkfe.blog.model.Article;
import cn.wmkfe.blog.vo.ArticleGet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    int save(Article article);

    int remove(String[] ids);

    int update(Article article);

    Article getOne(String id);

    List<Article> getList(@Param("from")Integer from, @Param("pageSize") Integer pageSize,@Param("articleGet") ArticleGet articleGet);

    int count(@Param("articleGet") ArticleGet articleGet);

    /**
     * 这里才是更新当前文章的浏览量
     * 但是sql有问题，每次增加了2
     * 原sql：UPDATE t_article AS a SET view_count= view_count+1 WHERE id=?
     * 卧槽了，神tm又正常了，卧槽了，还反复起来了
     * 应该是页面加载的原因，从首页点击标题进入文章详情会多刷新一次，导致整个controller执行两次
     * 随之调用的更新浏览量也执行了两次，直接在详情页面刷新只调用一次。
     * @param id
     * @return
     */
    int updateViewCount(String id);

    int updatePutTop(@Param("id")String id, @Param("putTop")boolean putTop);

    Article about();

    //归档
    List<Article> archives();

    //以下是新接口内容


    List<Article> listArchives();

    //逻辑删除文章
    int deleteLogicArticles(String[] ids);
}