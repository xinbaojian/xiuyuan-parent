package xin.xiuyuan.admin.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.ReUtil;
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
import xin.xiuyuan.admin.dto.dept.SysDeptForm;
import xin.xiuyuan.admin.dto.dept.SysDeptPageQuery;
import xin.xiuyuan.admin.entity.SysDept;
import xin.xiuyuan.admin.mapper.dept.SysDeptMapper;
import xin.xiuyuan.admin.repository.SysDeptRepository;
import xin.xiuyuan.admin.service.ISysDeptService;
import xin.xiuyuan.admin.vo.dept.SysDeptPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.types.CommonStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门 ServiceImpl
 *
 * @author xinbaojian
 * @create 2025-12-15 18:02
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysDeptServiceImpl implements ISysDeptService {

    private final SysDeptRepository deptRepository;

    private final MongoTemplate mongoTemplate;

    private final SysDeptMapper deptMapper;


    @Override
    public ApiResult<String> save(SysDeptForm form) {
        // 校验部门名称是否已存在
        SysDept deptNameCheck = deptRepository.findByDeptName(form.getDeptName());
        Assert.isNull(deptNameCheck, "部门名称已存在");
        SysDept dept = deptMapper.toEntity(form);
        checkParams(dept);
        if (!dept.getParentId().equals("00")) {
            deptRepository.findById(dept.getParentId()).ifPresent(parentDept -> dept.setAncestors(
                    StrUtil.format("{}/{}", parentDept.getAncestors(), parentDept.getId())
            ));
        }
        deptRepository.save(dept);
        return ApiResult.success("新增部门成功");
    }

    @Override
    public ApiResult<String> update(String id, SysDeptForm form) {
        // 校验部门 ID 是否存在
        SysDept dept = deptRepository.findById(id).orElse(null);
        Assert.notNull(dept, "部门不存在");
        // 校验部门名称是否已存在
        SysDept deptNameCheck = deptRepository.findByDeptName(form.getDeptName());
        Assert.isTrue(deptNameCheck == null || deptNameCheck.getId().equals(id), "部门名称已存在");
        checkParams(dept);
        deptMapper.updateEntity(form, dept);
        if (!dept.getParentId().equals("00")) {
            deptRepository.findById(dept.getParentId()).ifPresent(parentDept -> dept.setAncestors(
                    StrUtil.format("{}/{}", parentDept.getAncestors(), parentDept.getId())
            ));
        }
        dept.setUpdateTime(LocalDateTime.now());
        deptRepository.save(dept);
        return ApiResult.success("编辑部门成功!");
    }

    @Override
    public ApiResult<String> delete(String id) {
        // 校验部门是否存在
        SysDept dept = deptRepository.findById(id).orElse(null);
        Assert.notNull(dept, "部门不存在");
        deptRepository.delete(dept);
        return ApiResult.success("删除部门成功!");
    }

    @Override
    public ApiResult<PageData<SysDeptPageVO>> list(SysDeptPageQuery pageQuery) {
        Pageable pageable = PageRequest.of(pageQuery.getPage(), pageQuery.getPageSize());

        // 构建动态查询条件
        Criteria criteria = new Criteria();
        // 默认只查询未删除的数据
        criteria.and("delFlag").is(false);

        // 根据查询参数动态添加条件
        if (StrUtil.isNotBlank(pageQuery.getDeptName())) {
            // i 表示忽略大小写
            criteria.and("deptName").regex(pageQuery.getDeptName(), "i");
        }
        if (pageQuery.getStatus() != null) {
            criteria.and("status").is(pageQuery.getStatus());
        }

        Query query = new Query(criteria);
        // 设置分页
        query.with(pageable).with(Sort.by(Sort.Direction.ASC, "orderNum"));
        // 执行查询
        List<SysDept> deptList = mongoTemplate.find(query, SysDept.class);

        // 查询总数
        long total = mongoTemplate.count(new Query(criteria), SysDept.class);

        // 转换为 VO 对象
        List<SysDeptPageVO> voList = deptList.stream()
                .map(deptMapper::toVO)
                .collect(Collectors.toList());

        // 构造 PageData 对象
        PageData<SysDeptPageVO> pageData = new PageData<>(voList, total);
        return ApiResult.success(pageData);
    }

    @Override
    public ApiResult<List<Tree<String>>> getDeptTree(String deptName, CommonStatus status) {
        Criteria criteria = Criteria.where("delFlag").is(false);
        if (StrUtil.isNotBlank(deptName)) {
            criteria.and("deptName").regex(deptName, "i");
        }
        if (status != null) {
            criteria.and("status").is(status);
        }
        Query query = new Query(criteria).with(Sort.by(Sort.Direction.ASC, "orderNum"));
        List<SysDept> allList = mongoTemplate.find(query, SysDept.class);
        List<TreeNode<String>> nodeList = allList.stream().map(dept -> {
            TreeNode<String> node = new TreeNode<>();
            node.setId(dept.getId());
            node.setName(dept.getDeptName());
            node.setParentId(dept.getParentId());
            // 设置权重为 orderNum
            node.setWeight(dept.getOrderNum());
            Map<String, Object> extra = new HashMap<>(3);
            extra.put("status", dept.getStatus().name());
            extra.put("leader", dept.getLeader());
            extra.put("phone", dept.getEmail());
            extra.put("email", dept.getEmail());
            extra.put("statusDesc", dept.getStatus().getDesc());
            extra.put("createTime", LocalDateTimeUtil.format(dept.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            node.setExtra(extra);
            return node;
        }).toList();
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("orderNum");
        treeNodeConfig.setIdKey("id");
        // 最大递归深度
        treeNodeConfig.setDeep(5);
        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(nodeList, "00", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getWeight());
                    tree.setName(treeNode.getName());
                    // 扩展属性 ...
                    Map<String, Object> extra = treeNode.getExtra();
                    tree.putExtra("status", extra.get("status"));
                    tree.putExtra("leader", extra.get("leader"));
                    tree.putExtra("phone", extra.get("phone"));
                    tree.putExtra("email", extra.get("email"));
                    tree.putExtra("statusDesc", extra.get("statusDesc"));
                    tree.putExtra("createTime", extra.get("createTime"));
                });
        return ApiResult.success(treeNodes);
    }

    private void checkParams(SysDept dept) {
        if (dept != null) {
            // 校验手机号
            if (StrUtil.isNotBlank(dept.getPhone())) {
                Assert.isTrue(PhoneUtil.isMobile(dept.getPhone()), "手机号格式不正确");
            }
            // 校验邮箱
            if (StrUtil.isNotBlank(dept.getEmail())) {
                Assert.isTrue(ReUtil.isMatch("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", dept.getEmail()), "邮箱格式不正确");
            }
        }
    }
}