package cn.stkit.wxl.blog.service.impl;

import cn.stkit.wxl.blog.entity.Tag;
import cn.stkit.wxl.blog.repository.TagRepository;
import cn.stkit.wxl.blog.service.TagService;
import cn.stkit.wxl.blogadmin.entity.Pages;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file TagServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/30 11:39 上午
 */
@Service
public class TagServiceImpl implements TagService
{
    @Autowired
    private TagRepository tagRepository;

    @Override
    @Cacheable(value = "tag")
    public List<Tag> findAll()
    {
        return tagRepository.findAll();
    }

    @Override
    public Tag findByTagName(String tagName)
    {
        return tagRepository.findByTagName(tagName);
    }

    @Override
    public Integer countByTagInputDate(Date tagInputDate)
    {
        return tagRepository.countByTagInputDate(tagInputDate);
    }

    @Override
    public int count()
    {
        return (int)tagRepository.count();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Tag saveTag(Tag tag)
    {
        return tagRepository.save(tag);
    }

    @Override
    public Page<Tag> findAllBySearch(Pages pages, Integer tagId, String tagName)
    {
        Pageable pageable = PageRequest.of(pages.getPage(), pages.getPageSize(), Sort.Direction.DESC, "tagId");
        return tagRepository.findAll(this.getWhereClause(tagId, tagName), pageable);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteTagByTagId(Integer tagId)
    {
        tagRepository.deleteById(tagId);
    }

    /**
     * 动态生成 where 语句
     * @param tagId
     * @param tagName
     * @return
     */
    private Specification<Tag> getWhereClause(Integer tagId, String tagName)
    {
        return new Specification<Tag>() {
            @Override
            public Predicate toPredicate(Root<Tag> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(tagId != null) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.equal(root.get("tagId"), tagId))
                    );
                }
                if(!StringUtils.isEmpty(tagName)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("tagName"), tagName + "%"))
                    );
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }
}