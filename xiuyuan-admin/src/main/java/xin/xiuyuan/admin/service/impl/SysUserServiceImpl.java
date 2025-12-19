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
import xin.xiuyuan.admin.dto.SysUserCreateForm;
import xin.xiuyuan.admin.dto.SysUserForm;
import xin.xiuyuan.admin.dto.SysUserPageQuery;
import xin.xiuyuan.admin.entity.SysUser;
import xin.xiuyuan.admin.mapper.SysUserMapper;
import xin.xiuyuan.admin.repository.SysUserRepository;
import xin.xiuyuan.admin.service.ISysUserService;
import xin.xiuyuan.admin.vo.SysUserPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户 ServiceImpl
 *
 * @author xinbaojian
 * @create 2025-12-15 17:34
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements ISysUserService {

    private final SysUserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    private final SysUserMapper userMapper;


    @Override
    public ApiResult<String> save(SysUserCreateForm form) {
        // 校验登录账号是否已存在
        SysUser loginNameCheck = userRepository.findByLoginName(form.getLoginName());
        Assert.isNull(loginNameCheck, "登录账号已存在");

        // 校验邮箱是否已存在
        if (StrUtil.isNotBlank(form.getEmail())) {
            SysUser emailCheck = userRepository.findByEmail(form.getEmail());
            Assert.isNull(emailCheck, "邮箱已存在");
        }

        // 校验手机号码是否已存在
        if (StrUtil.isNotBlank(form.getMobile())) {
            SysUser mobileCheck = userRepository.findByMobile(form.getMobile());
            Assert.isNull(mobileCheck, "手机号码已存在");
        }

        SysUser user = userMapper.toEntity(new SysUserForm()
                .setDeptId(form.getDeptId())
                .setLoginName(form.getLoginName())
                .setUsername(form.getUsername())
                .setUserType(form.getUserType())
                .setEmail(form.getEmail())
                .setMobile(form.getMobile())
                .setUserSex(form.getUserSex())
                .setPassword(form.getPassword())
                .setStatus(form.getStatus())
                .setRemark(form.getRemark()));
        // 设置密码加密盐值 TODO: 实现密码加密逻辑
        user.setSalt("");
        userRepository.save(user);
        return ApiResult.success("新增用户成功");
    }

    @Override
    public ApiResult<String> update(String id, SysUserForm form) {
        // 校验用户 ID 是否存在
        SysUser user = userRepository.findById(id).orElse(null);
        Assert.notNull(user, "用户不存在");

        // 校验登录账号是否已存在
        SysUser loginNameCheck = userRepository.findByLoginNameAndIdNot(form.getLoginName(), id);
        Assert.isNull(loginNameCheck, "登录账号已存在");

        // 校验邮箱是否已存在
        if (StrUtil.isNotBlank(form.getEmail())) {
            SysUser emailCheck = userRepository.findByEmailAndIdNot(form.getEmail(), id);
            Assert.isNull(emailCheck, "邮箱已存在");
        }

        // 校验手机号码是否已存在
        if (StrUtil.isNotBlank(form.getMobile())) {
            SysUser mobileCheck = userRepository.findByMobileAndIdNot(form.getMobile(), id);
            Assert.isNull(mobileCheck, "手机号码已存在");
        }

        userMapper.updateEntity(form, user);
        user.setUpdateTime(LocalDateTime.now());
        userRepository.save(user);
        return ApiResult.success("编辑用户成功!");
    }

    @Override
    public ApiResult<String> delete(String id) {
        // 校验用户是否存在
        SysUser user = userRepository.findById(id).orElse(null);
        Assert.notNull(user, "用户不存在");
        userRepository.delete(user);
        return ApiResult.success("删除用户成功!");
    }

    @Override
    public ApiResult<PageData<SysUserPageVO>> list(SysUserPageQuery pageQuery) {
        Pageable pageable = PageRequest.of(pageQuery.getPage(), pageQuery.getPageSize());

        // 构建动态查询条件
        Criteria criteria = new Criteria();

        // 根据查询参数动态添加条件
        if (StrUtil.isNotBlank(pageQuery.getLoginName())) {
            // i 表示忽略大小写
            criteria.and("loginName").regex(pageQuery.getLoginName(), "i");
        }
        if (StrUtil.isNotBlank(pageQuery.getUsername())) {
            // i 表示忽略大小写
            criteria.and("username").regex(pageQuery.getUsername(), "i");
        }
        if (pageQuery.getUserType() != null) {
            criteria.and("userType").is(pageQuery.getUserType());
        }
        if (pageQuery.getUserSex() != null) {
            criteria.and("userSex").is(pageQuery.getUserSex());
        }
        if (pageQuery.getStatus() != null) {
            criteria.and("status").is(pageQuery.getStatus());
        }

        Query query = new Query(criteria);
        // 设置分页
        query.with(pageable).with(Sort.by(Sort.Direction.DESC, "createTime"));
        // 执行查询
        List<SysUser> userList = mongoTemplate.find(query, SysUser.class);

        // 查询总数
        long total = mongoTemplate.count(new Query(criteria), SysUser.class);

        // 转换为 VO 对象
        List<SysUserPageVO> voList = userList.stream()
                .map(userMapper::toVO)
                .collect(Collectors.toList());

        // 构造 PageData 对象
        PageData<SysUserPageVO> pageData = new PageData<>();
        pageData.setList(voList);
        pageData.setTotal(total);
        return ApiResult.success(pageData);
    }
}