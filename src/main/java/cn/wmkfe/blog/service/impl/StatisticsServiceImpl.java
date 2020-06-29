package cn.wmkfe.blog.service.impl;

import cn.wmkfe.blog.dao.StatisticsMapper;
import cn.wmkfe.blog.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 这是更新浏览次数的，原作每次访问会增加2个浏览
 * 试试能不能改掉bug,奥里给
 * 时刻记住我不是来给原作挑毛病的，我是在二次开发。
 *
 * 调用一次updateCount为什么会+2了呢，sql也没毛病啊
 * 日志里执行了两条sql，分别是
 * update t_statistics set viewCount=viewCount+1 这个是什么鬼 本类的 话说这个也加不上吧
 * UPDATE t_article AS a SET view_count= view_count+1 WHERE id=? 这个才对吧 ArticleMapper.updateViewCount
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public int updateCount() {
        return statisticsMapper.updateViewCount();
    }

    @Override
    public int getCount() {
        return statisticsMapper.getViewCount();
    }
}
