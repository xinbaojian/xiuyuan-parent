package xin.xiuyuan.admin.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xin.xiuyuan.admin.entity.*;
import xin.xiuyuan.admin.repository.*;
import xin.xiuyuan.common.types.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class SysUserServiceImplTest {

    private final String adminId = "6953be5fd024de0401549bc8";
    @Autowired
    private SysRoleRepository roleRepository;
    @Autowired
    private SysUserRepository userRepository;
    @Autowired
    private MenuPermissionRepository permissionRepository;
    @Autowired
    private SysPostRepository postRepository;

    @Autowired
    private SysDeptRepository deptRepository;

    @Autowired
    private SysConfigRepository configRepository;

    @Test
    void init() {
        log.info("初始化基础数据");
        String roleId = initRole();
        String postId = initPost();
        String deptId = initDept();
        initUser(roleId, postId, deptId);
        initMenu();
        setRolePermission();
        initConfig();
    }

    private String initRole() {
        log.info("初始化超级管理员角色");
        SysRole role = new SysRole();
        role.setId("6953c65736aaf060e2eef6d5");
        role.setRoleName("超级管理员");
        role.setRoleKey("admin");
        role.setStatus(CommonStatus.NORMAL);
        role.setOrderNum(1);
        role.setRemark("超级管理员");
        role.setCreateBy(adminId);
        roleRepository.save(role);
        return role.getId();
    }

    private void initUser(String roleId, String postId, String deptId) {
        log.info("初始化超级管理员用户");
        SysUser user = new SysUser();
        user.setId(adminId);
        user.setLoginName("admin");
        user.setUsername("超级管理员");
        user.setUserType(UserType.SYSTEM_USER);
        user.setUserSex(UserSex.MALE);
        user.setSalt(IdUtil.fastSimpleUUID());
        user.setPassword(SaSecureUtil.md5(SaSecureUtil.md5("123456") + SaSecureUtil.md5(user.getSalt())));
        user.setStatus(CommonStatus.NORMAL);
        user.setRoleIds(List.of(roleId));
        user.setCreateBy(adminId);
        user.setPostId(postId);
        user.setDeptId(deptId);
        userRepository.save(user);
    }

    private void initMenu() {
        log.info("初始化菜单");
        List<SysMenuPermission> permissions = new ArrayList<>();
        SysMenuPermission menuIndex = new SysMenuPermission();
        menuIndex.setId("6952450c7f34f5883eb9c6cc");
        menuIndex.setParentId("00");
        menuIndex.setName("");
        menuIndex.setPath("/");
        menuIndex.setComponent("Layout");
        menuIndex.setRedirect("");
        menuIndex.setAlwaysShow(false);
        menuIndex.setOrderNum(1);
        menuIndex.setStatus(CommonStatus.NORMAL);
        menuIndex.setType(MenuType.MENU);
        menuIndex.setMeta(
                new SysMenuPermission.Meta()
                        .setTitle("首页")
                        .setDefaultOpen(false)
        );
        menuIndex.setCreateBy(adminId);
        permissions.add(menuIndex);

        SysMenuPermission menuIndex1 = new SysMenuPermission();
        menuIndex1.setParentId("6952450c7f34f5883eb9c6cc");
        menuIndex1.setName("index");
        menuIndex1.setPath("/index");
        menuIndex1.setComponent("/index/index.vue");
        menuIndex1.setRedirect("noRedirect");
        menuIndex1.setAlwaysShow(false);
        menuIndex1.setOrderNum(1);
        menuIndex1.setStatus(CommonStatus.NORMAL);
        menuIndex1.setType(MenuType.MENU);
        menuIndex1.setMeta(
                new SysMenuPermission.Meta()
                        .setTitle("首页")
                        .setIcon("home")
                        .setDefaultOpen(false)
                        .setPermissions("menu:index")
        );
        menuIndex1.setCreateBy(adminId);
        permissions.add(menuIndex1);
        // 系统设置
        SysMenuPermission menuSystem = new SysMenuPermission();
        menuSystem.setId("69521743702b4a1843871a17");
        menuSystem.setParentId("00");
        menuSystem.setName("Setting");
        menuSystem.setPath("/setting");
        menuSystem.setComponent("Layout");
        menuSystem.setAlwaysShow(false);
        menuSystem.setOrderNum(2);
        menuSystem.setCreateBy(adminId);
        menuSystem.setMeta(
                new SysMenuPermission.Meta()
                        .setTitle("系统设置")
                        .setIcon("cog")
                        .setDefaultOpen(false)
                        .setPermissions("menu:setting")
        );

        permissions.add(menuSystem);
        permissionRepository.saveAll(permissions);
        initSystemMenus();
    }

    private void initSystemMenus() {
        log.info("初始化系统设置菜单");

        // 角色管理
        SysMenuPermission menuRole = new SysMenuPermission();
        menuRole.setId("69521bf0702b4a1843871a18");
        menuRole.setParentId("69521743702b4a1843871a17");
        menuRole.setName("role");
        menuRole.setPath("role");
        menuRole.setComponent("/setting/role/index.vue");
        menuRole.setAlwaysShow(false);
        menuRole.setOrderNum(2);
        menuRole.setStatus(CommonStatus.NORMAL);
        menuRole.setType(MenuType.MENU);
        menuRole.setMeta(
                new SysMenuPermission.Meta()
                        .setTitle("角色管理")
                        .setDefaultOpen(false)
                        .setPermissions("setting:role:list")
        );
        menuRole.setCreateBy(adminId);
        permissionRepository.save(menuRole);

        // 部门管理
        SysMenuPermission menuDept = new SysMenuPermission();
        menuDept.setId("695220f2702b4a1843871a19");
        menuDept.setParentId("69521743702b4a1843871a17");
        menuDept.setName("Dept");
        menuDept.setPath("dept");
        menuDept.setComponent("/setting/dept/index.vue");
        menuDept.setAlwaysShow(false);
        menuDept.setOrderNum(3);
        menuDept.setStatus(CommonStatus.NORMAL);
        menuDept.setType(MenuType.MENU);
        menuDept.setMeta(
                new SysMenuPermission.Meta()
                        .setTitle("部门管理")
                        .setDefaultOpen(false)
                        .setPermissions("setting:dept:list")
        );
        menuDept.setCreateBy(adminId);
        permissionRepository.save(menuDept);

        // 岗位管理
        SysMenuPermission menuPost = new SysMenuPermission();
        menuPost.setId("69522417702b4a1843871a1a");
        menuPost.setParentId("69521743702b4a1843871a17");
        menuPost.setName("Post");
        menuPost.setPath("post");
        menuPost.setComponent("/setting/post/index.vue");
        menuPost.setAlwaysShow(false);
        menuPost.setOrderNum(4);
        menuPost.setStatus(CommonStatus.NORMAL);
        menuPost.setType(MenuType.MENU);
        menuPost.setMeta(
                new SysMenuPermission.Meta()
                        .setTitle("岗位管理")
                        .setDefaultOpen(false)
                        .setPermissions("setting:post:list")
        );
        menuPost.setCreateBy(adminId);
        permissionRepository.save(menuPost);

        // 用户管理
        SysMenuPermission menuUser = new SysMenuPermission();
        menuUser.setId("695226ab702b4a1843871a1b");
        menuUser.setParentId("69521743702b4a1843871a17");
        menuUser.setName("User");
        menuUser.setPath("user");
        menuUser.setComponent("/setting/user/index.vue");
        menuUser.setRedirect("noRedirect");
        menuUser.setAlwaysShow(false);
        menuUser.setOrderNum(1);
        menuUser.setStatus(CommonStatus.NORMAL);
        menuUser.setType(MenuType.MENU);
        menuUser.setMeta(
                new SysMenuPermission.Meta()
                        .setTitle("用户管理")
                        .setDefaultOpen(false)
                        .setPermissions("setting:user:list")
        );
        menuUser.setCreateBy(adminId);
        permissionRepository.save(menuUser);

        // 菜单管理
        SysMenuPermission menuMenu = new SysMenuPermission();
        menuMenu.setId("69522768702b4a1843871a1c");
        menuMenu.setParentId("69521743702b4a1843871a17");
        menuMenu.setName("menu");
        menuMenu.setPath("menu");
        menuMenu.setComponent("/setting/menu/index.vue");
        menuMenu.setAlwaysShow(false);
        menuMenu.setOrderNum(5);
        menuMenu.setStatus(CommonStatus.NORMAL);
        menuMenu.setType(MenuType.MENU);
        menuMenu.setMeta(
                new SysMenuPermission.Meta()
                        .setTitle("菜单管理")
                        .setDefaultOpen(false)
                        .setPermissions("setting:menu:list")
        );
        menuMenu.setCreateBy(adminId);
        permissionRepository.save(menuMenu);

        // 个人中心
        SysMenuPermission menuPersonal = new SysMenuPermission();
//        menuPersonal.setId("695228a8702b4a1843871a1d");
        menuPersonal.setParentId("69521743702b4a1843871a17");
        menuPersonal.setName("Personal");
        menuPersonal.setPath("personal");
        menuPersonal.setComponent("/setting/personal/index.vue");
        menuPersonal.setAlwaysShow(false);
        menuPersonal.setOrderNum(6);
        menuPersonal.setStatus(CommonStatus.NORMAL);
        menuPersonal.setType(MenuType.MENU);
        menuPersonal.setHidden(true);
        menuPersonal.setMeta(
                new SysMenuPermission.Meta()
                        .setTitle("个人中心")
                        .setDefaultOpen(false)
                        .setPermissions("setting:personal:list")
        );
        menuPersonal.setCreateBy(adminId);
        permissionRepository.save(menuPersonal);
        log.info("系统设置菜单初始化完成");
    }

    private String initPost() {
        log.info("初始化岗位");
        SysPost post = new SysPost();
        post.setOrderNum(1);
        post.setPostName("董事长");
        post.setPostCode("CEO");
        post.setStatus(CommonStatus.NORMAL);
        post.setCreateBy(adminId);
        postRepository.save(post);
        return post.getId();
    }

    private void setRolePermission() {
        log.info("设置角色权限");
        SysRole role = roleRepository.findById("6953c65736aaf060e2eef6d5").orElse(null);
        if (role != null) {
            List<SysMenuPermission> permissions = permissionRepository.findAll();
            role.setPermissionIds(permissions.stream().map(SysMenuPermission::getId).toList());
            roleRepository.save(role);
        }
    }

    private String initDept() {
        log.info("初始化部门");
        SysDept dept = new SysDept();
        dept.setDeptName("修远集团");
        dept.setParentId("00");
        dept.setStatus(CommonStatus.NORMAL);
        dept.setCreateBy(adminId);
        dept.setOrderNum(0);
        dept.setLeader("修远");
        dept.setPhone("18888888888");
        dept.setEmail("daddy@xiuyuan.xin");
        deptRepository.save(dept);
        return dept.getId();
    }

    private void initConfig() {
        log.info("初始化系统配置");
        SysConfig config = new SysConfig();
        config.setConfigName("用户管理-账号初始密码");
        config.setConfigKey("sys.user.initPassword");
        config.setConfigValue("123456");
        config.setRemark("用户管理-账号初始密码");
        config.setConfigType(ConfigType.Y);
        configRepository.save(config);
    }
}