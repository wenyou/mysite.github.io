package cn.stkit.wxl.blog.service.impl;

import cn.stkit.wxl.blog.entity.Link;
import cn.stkit.wxl.blog.repository.LinkRepository;
import cn.stkit.wxl.blog.service.LinkService;
import cn.stkit.wxl.blogadmin.entity.Pages;
import cn.stkit.wxl.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file LinkServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/30 11:33 上午
 */
@Service
public class LinkServiceImpl implements LinkService
{
    @Autowired
    private LinkRepository linkRepository;

    @Override
    @Cacheable(value = "link")
    public List<Link> findAllByEnabled()
    {
        return linkRepository.findAll();
    }

    @Override
    public int count() {
        return (int)linkRepository.count();
    }

    @Override
    public Page<Link> findAllBySearch(Pages pages, Integer linkId, String linkName)
    {
        Pageable pageable = PageRequest.of(pages.getPage(), pages.getPageSize(), Sort.Direction.DESC, "linkId");
        return linkRepository.findAll(this.getWhereClause(linkId, linkName), pageable);
    }

    @Override
    public Link findLinkByLinkId(Integer linkId)
    {
        Optional<Link> optionalLink = linkRepository.findById(linkId);
        if(optionalLink.isPresent()) {
            return optionalLink.get();
        }
        return null;
    }

    @Override
    public int saveOrUpdateLink(Link link)
    {
        int timestamp = TimeUtil.getNowTimestamp();
        if(link.getLinkId() == null) {
            link.setCreatedTime(timestamp);
            link.setCreatedUserId(link.getLoginUserId());
        } else {
            link.setUpdatedTime(timestamp);
            link.setUpdatedUserId(link.getLoginUserId());
        }
        linkRepository.save(link);
        return 1;
    }

    @Override
    public void deleteLink(Integer linkId)
    {
        linkRepository.deleteById(linkId);
    }

    private Specification<Link> getWhereClause(Integer linkId, String linkName)
    {
        return new Specification<Link>() {
            @Override
            public Predicate toPredicate(Root<Link> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(linkId != null) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.equal(root.get("linkId"), linkId))
                    );
                }
                if(linkName != null) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("linkName"), linkName+"%"))
                    );
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }
}