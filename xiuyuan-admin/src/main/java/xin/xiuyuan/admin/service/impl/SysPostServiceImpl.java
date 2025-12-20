package xin.xiuyuan.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xin.xiuyuan.admin.dto.post.SysPostForm;
import xin.xiuyuan.admin.dto.post.SysPostPageQuery;
import xin.xiuyuan.admin.entity.SysPost;
import xin.xiuyuan.admin.mapper.post.SysPostMapper;
import xin.xiuyuan.admin.repository.SysPostRepository;
import xin.xiuyuan.admin.service.ISysPostService;
import xin.xiuyuan.admin.vo.post.SysPostPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.Option;
import xin.xiuyuan.common.common.PageData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位 ServiceImpl
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysPostServiceImpl implements ISysPostService {

    private final SysPostRepository postRepository;

    private final MongoTemplate mongoTemplate;

    private final SysPostMapper postMapper;


    @Override
    public ApiResult<String> save(SysPostForm form) {
        // 校验岗位编码是否已存在
        SysPost postCodeCheck = postRepository.findByPostCode(form.getPostCode());
        Assert.isNull(postCodeCheck, "岗位编码已存在");

        // 校验岗位名称是否已存在
        SysPost postNameCheck = postRepository.findByPostName(form.getPostName());
        Assert.isNull(postNameCheck, "岗位名称已存在");

        SysPost post = postMapper.toEntity(form);
        postRepository.save(post);
        return ApiResult.success("新增岗位成功");
    }

    @Override
    public ApiResult<String> update(String id, SysPostForm form) {
        // 校验岗位 ID 是否存在
        SysPost post = postRepository.findById(id).orElse(null);
        Assert.notNull(post, "岗位不存在");

        // 校验岗位编码是否已存在
        SysPost postCodeCheck = postRepository.findByPostCodeAndIdNot(form.getPostCode(), id);
        Assert.isNull(postCodeCheck, "岗位编码已存在");

        // 校验岗位名称是否已存在
        SysPost postNameCheck = postRepository.findByPostNameAndIdNot(form.getPostName(), id);
        Assert.isNull(postNameCheck, "岗位名称已存在");

        postMapper.updateEntity(form, post);
        post.setUpdateTime(LocalDateTime.now());
        postRepository.save(post);
        return ApiResult.success("编辑岗位成功!");
    }

    @Override
    public ApiResult<String> delete(String id) {
        // 校验岗位是否存在
        SysPost post = postRepository.findById(id).orElse(null);
        Assert.notNull(post, "岗位不存在");
        postRepository.delete(post);
        return ApiResult.success("删除岗位成功!");
    }

    @Override
    public ApiResult<PageData<SysPostPageVO>> list(SysPostPageQuery pageQuery) {
        Pageable pageable = PageRequest.of(pageQuery.getPage(), pageQuery.getPageSize());

        // 构建动态查询条件
        Criteria criteria = new Criteria();

        // 根据查询参数动态添加条件
        if (StrUtil.isNotBlank(pageQuery.getPostCode())) {
            // i 表示忽略大小写
            criteria.and("postCode").regex(pageQuery.getPostCode(), "i");
        }
        if (StrUtil.isNotBlank(pageQuery.getPostName())) {
            // i 表示忽略大小写
            criteria.and("postName").regex(pageQuery.getPostName(), "i");
        }
        if (pageQuery.getStatus() != null) {
            criteria.and("status").is(pageQuery.getStatus());
        }

        Query query = new Query(criteria);
        // 设置分页
        query.with(pageable).with(Sort.by(Sort.Direction.ASC, "orderNum"));
        // 执行查询
        List<SysPost> postList = mongoTemplate.find(query, SysPost.class);

        // 查询总数
        long total = mongoTemplate.count(new Query(criteria), SysPost.class);

        // 转换为 VO 对象
        List<SysPostPageVO> voList = postList.stream()
                .map(postMapper::toVO)
                .collect(Collectors.toList());

        // 构造 PageData 对象
        PageData<SysPostPageVO> pageData = new PageData<>();
        pageData.setList(voList);
        pageData.setTotal(total);
        return ApiResult.success(pageData);
    }

    @Override
    public ApiResult<List<Option>> options() {
        List<Option> options = postRepository.findAll().stream()
                .map(post -> new Option().setLabel(post.getPostName()).setValue(post.getId()))
                .toList();
        return ApiResult.success(options);
    }
}