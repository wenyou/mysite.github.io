package cn.stkit.wxl.blog.service.impl;

import cn.stkit.wxl.blog.entity.BlogAccess;
import cn.stkit.wxl.blog.repository.BlogAccessRepository;
import cn.stkit.wxl.blog.service.BlogAccessService;
import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogAccessServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 5:26 下午
 */

@Service
public class BlogAccessServiceImpl implements BlogAccessService
{
    @Autowired
    private BlogAccessRepository blogAccessRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public BlogAccess getByAccessDateIs(Date accessDate)
    {

        return blogAccessRepository.getByAccessDateIs(accessDate);
    }

    @Override
    public void save(BlogAccess blogAccess)
    {
        blogAccessRepository.save(blogAccess);
    }

    @Override
    public int sumBlogAccess(Date date, Integer days)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<BlogAccess> root = query.from(BlogAccess.class);
        query.select(criteriaBuilder.sum(root.get("accessCount")));
        if(days == null && date != null) {
            Predicate predicate = criteriaBuilder.equal(root.get("accessDate"), date);
            query.where(predicate);
        } else if(date != null) {
            Predicate predicate = criteriaBuilder.between(root.get("accessDate"), TimeUtil.getDateBefore(date, days), date);
            query.where(predicate);
        }
        Integer result = entityManager.createQuery(query).getSingleResult();
        if(result == null)
            return 0;

        return result;
    }

    @Override
    public List<BlogAccess> findChartsBlogAccess()
    {
        Pageable pageable = PageRequest.of(Constants.startPage, Constants.defaultPageSize, Sort.Direction.ASC, "accessDate");
        Page<BlogAccess> blogAccessPage = blogAccessRepository.findAll(pageable);
        return blogAccessPage != null ? blogAccessPage.getContent() : null;
    }
}