package xin.xiuyuan.admin.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xin.xiuyuan.admin.dto.login.LoginForm;
import xin.xiuyuan.admin.dto.user.*;
import xin.xiuyuan.admin.entity.SysRole;
import xin.xiuyuan.admin.entity.SysUser;
import xin.xiuyuan.admin.mapper.SysUserMapper;
import xin.xiuyuan.admin.repository.SysDeptRepository;
import xin.xiuyuan.admin.repository.SysPostRepository;
import xin.xiuyuan.admin.repository.SysRoleRepository;
import xin.xiuyuan.admin.repository.SysUserRepository;
import xin.xiuyuan.admin.service.ISysConfigService;
import xin.xiuyuan.admin.service.ISysUserService;
import xin.xiuyuan.admin.vo.SysUserPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.constant.ConfigConstant;
import xin.xiuyuan.common.types.CommonStatus;

import java.time.LocalDateTime;
import java.util.Collections;
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

    private final SysPostRepository postRepository;

    private final SysRoleRepository roleRepository;

    private final SysDeptRepository deptRepository;

    private final ISysConfigService configService;


    @Override
    public ApiResult<String> save(SysUserCreateForm form) {
        // 校验登录账号是否已存在
        SysUser loginNameCheck = userRepository.findByLoginNameAndDeletedIsFalse(form.getLoginName());
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
        // 设置密码加密盐值
        user.setSalt(IdUtil.fastSimpleUUID());
        user.setPassword(SaSecureUtil.md5(SaSecureUtil.md5(user.getPassword()) + SaSecureUtil.md5(user.getSalt())));
//        user.setCreateBy(StpUtil.getLoginIdAsString());
        setPostRole(user, form.getDeptId(), form.getPostId(), form.getRoleIds());
        userRepository.save(user);
        return ApiResult.success("新增用户成功");
    }

    @Override
    @CacheEvict(value = {"user", "user:permissions"}, key = "#id")
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
        if (StrUtil.isNotBlank(form.getPassword())) {
            user.setSalt(IdUtil.fastSimpleUUID());
            user.setPassword(SaSecureUtil.md5(SaSecureUtil.md5(form.getPassword()) + SaSecureUtil.md5(user.getSalt())));
        }
        setPostRole(user, form.getDeptId(), form.getPostId(), form.getRoleIds());
        userMapper.updateEntity(form, user);
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateBy(StpUtil.getLoginIdAsString());
        userRepository.save(user);
        return ApiResult.success("编辑用户成功!");
    }

    private void setPostRole(SysUser user, String deptId, String postId, List<String> roleIds) {
        if (StrUtil.isNotBlank(deptId)) {
            deptRepository.findById(deptId).ifPresent(user::setDept);
        }
        if (StrUtil.isNotBlank(postId)) {
            postRepository.findById(postId).ifPresent(user::setPost);
        }
        if (CollUtil.isNotEmpty(roleIds)) {
            List<SysRole> roles = roleRepository.findAllById(roleIds);
            if (CollUtil.isNotEmpty(roles)) {
                user.setRoles(roles);
            } else {
                throw new RuntimeException("角色不存在");
            }
        }
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

    @Override
    public ApiResult<SaTokenInfo> login(LoginForm form) {
        SysUser loginNameCheck = userRepository.findByLoginNameAndDeletedIsFalse(form.getUsername());
        if (loginNameCheck == null) {
            return ApiResult.error("用户不存在");
        }
        if (loginNameCheck.getStatus() != CommonStatus.NORMAL) {
            return ApiResult.error("用户被禁用登录");
        }
        if (!loginNameCheck.getPassword().equals(SaSecureUtil.md5(SaSecureUtil.md5(form.getPassword()) + SaSecureUtil.md5(loginNameCheck.getSalt())))) {
            return ApiResult.error("用户名或密码错误");
        }
        StpUtil.login(loginNameCheck.getId(), form.getLoginDevice().name());
        return ApiResult.success(StpUtil.getTokenInfo());
    }

    @Override
    public ApiResult<String> logout() {
        StpUtil.logout();
        return ApiResult.success("注销成功");
    }

    @Override
    public ApiResult<UserInfoVo> getUserInfo() {
        SysUser user = userRepository.findById(StpUtil.getLoginIdAsString()).orElse(null);
        Assert.notNull(user, "用户不存在");
        UserInfoVo infoVo = new UserInfoVo();
        infoVo.setUsername(user.getUsername())
                .setPermissions(List.of("admin"))
//                .setAvatar(user.getAvatar())
        ;
        return ApiResult.success(infoVo);
    }

    @Override
    public ApiResult<String> resetPwd(UserResetPwd form) {
        SysUser user = userRepository.findById(form.getId()).orElse(null);
        Assert.notNull(user, "用户不存在");
        if (StrUtil.isBlank(form.getNewPassword())) {
            configService.getConfigValue(ConfigConstant.SYS_USER_INIT_PASSWORD_KEY).ifPresent(form::setNewPassword);
        }
        user.setSalt(IdUtil.fastSimpleUUID());
        user.setPassword(SaSecureUtil.md5(SaSecureUtil.md5(form.getNewPassword()) + SaSecureUtil.md5(user.getSalt())));
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateBy(StpUtil.getLoginIdAsString());
        userRepository.save(user);
        return ApiResult.success("重置密码成功");
    }

    @Override
    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    public SysUser findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "user:permissions", key = "#id", unless = "#result == null")
    public List<String> getPermissionList(String id) {
        SysUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        if (CollUtil.isNotEmpty(user.getRoles())) {
            return user.getRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .filter(p -> p.getMeta() != null && CollUtil.isNotEmpty(Collections.singleton(p.getMeta().getPermissions())))
                    .map(p -> p.getMeta().getPermissions())
                    .filter(StrUtil::isNotEmpty)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return null;
    }
}