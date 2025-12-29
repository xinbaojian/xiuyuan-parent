package xin.xiuyuan.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xin.xiuyuan.admin.dto.menu.MenuPermissionForm;
import xin.xiuyuan.admin.entity.SysMenuPermission;
import xin.xiuyuan.admin.mapper.menu.MenuPermissionMapper;
import xin.xiuyuan.admin.repository.MenuPermissionRepository;
import xin.xiuyuan.admin.service.IMenuPermissionService;
import xin.xiuyuan.admin.vo.menu.MenuTreeVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.MenuType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限 ServiceImpl
 *
 * @author xinbaojian
 * @create 2025-12-29 11:36
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MenuPermissionServiceImpl implements IMenuPermissionService {

    private final MenuPermissionRepository menuPermissionRepository;

    private final MongoTemplate mongoTemplate;

    private final MenuPermissionMapper menuPermissionMapper;

    @Override
    public ApiResult<String> save(MenuPermissionForm form) {
        // 校验路由名称是否已存在
        SysMenuPermission nameCheck = menuPermissionRepository.findByName(form.getName());
        Assert.isNull(nameCheck, "路由名称已存在");

        SysMenuPermission sysMenuPermission = menuPermissionMapper.toEntity(form);

        // 构建元数据对象
        SysMenuPermission.Meta meta = new SysMenuPermission.Meta();
        meta.setTitle(form.getTitle());
        meta.setIcon(form.getIcon());
        meta.setDefaultOpen(form.getDefaultOpen());
        meta.setPermissions(form.getPermissions());
        sysMenuPermission.setMeta(meta);

        menuPermissionRepository.save(sysMenuPermission);
        return ApiResult.success("新增菜单成功");
    }

    @Override
    public ApiResult<String> update(String id, MenuPermissionForm form) {
        // 校验菜单 ID 是否存在
        SysMenuPermission sysMenuPermission = menuPermissionRepository.findById(id).orElse(null);
        Assert.notNull(sysMenuPermission, "菜单不存在");

        // 校验路由名称是否已存在
        SysMenuPermission nameCheck = menuPermissionRepository.findByName(form.getName());
        Assert.isTrue(nameCheck == null || nameCheck.getId().equals(id), "路由名称已存在");

        // 更新基本信息
        menuPermissionMapper.updateEntity(form, sysMenuPermission);

        // 更新元数据对象
        SysMenuPermission.Meta meta = sysMenuPermission.getMeta();
        if (meta == null) {
            meta = new SysMenuPermission.Meta();
        }
        meta.setTitle(form.getTitle());
        meta.setIcon(form.getIcon());
        meta.setDefaultOpen(form.getDefaultOpen());
        meta.setPermissions(form.getPermissions());
        sysMenuPermission.setMeta(meta);

        sysMenuPermission.setUpdateTime(LocalDateTime.now());
        menuPermissionRepository.save(sysMenuPermission);
        return ApiResult.success("编辑菜单成功");
    }

    @Override
    public ApiResult<String> delete(String id) {
        // 校验菜单是否存在
        SysMenuPermission sysMenuPermission = menuPermissionRepository.findById(id).orElse(null);
        Assert.notNull(sysMenuPermission, "菜单不存在");
        // 检查是否有子级菜单
        if (menuPermissionRepository.existsByParentId(id)) {
            return ApiResult.error("请先删除子级菜单");
        }

        menuPermissionRepository.delete(sysMenuPermission);
        return ApiResult.success("删除菜单成功");
    }

    @Override
    public ApiResult<List<MenuTreeVO>> getMenuTree(String title, CommonStatus status, MenuType menuType) {
        Criteria criteria = Criteria.where("delFlag").is(false);

        if (StrUtil.isNotBlank(title)) {
            criteria.and("meta.title").regex(title, "i");
        }
        if (status != null) {
            criteria.and("status").is(status);
        }
        if (menuType != null) {
            criteria.and("type").is(menuType);
        }
        Query query = new Query(criteria).with(Sort.by(Sort.Direction.ASC, "orderNum"));
        List<SysMenuPermission> allList = mongoTemplate.find(query, SysMenuPermission.class);

        // 构建树形结构
        List<MenuTreeVO> tree = buildMenuTree(allList, "00");

        return ApiResult.success(tree);
    }

    /**
     * 递归构建菜单树
     */
    private List<MenuTreeVO> buildMenuTree(List<SysMenuPermission> allList, String parentId) {
        List<MenuTreeVO> tree = new ArrayList<>();

        for (SysMenuPermission menu : allList) {
            // 找到当前父级下的子菜单
            if (parentId.equals(menu.getParentId())) {
                MenuTreeVO vo = convertToTreeVO(menu);

                // 递归查找子菜单
                List<MenuTreeVO> children = buildMenuTree(allList, menu.getId());
                if (!children.isEmpty()) {
                    vo.setChildren(children);
                }

                tree.add(vo);
            }
        }

        return tree;
    }

    /**
     * 将 MenuPermission 转换为 MenuTreeVO
     */
    private MenuTreeVO convertToTreeVO(SysMenuPermission menu) {
        MenuTreeVO vo = new MenuTreeVO();
        vo.setId(menu.getId());
        vo.setParentId(menu.getParentId());
        vo.setType(menu.getType());
        vo.setPath(menu.getPath());
        vo.setComponent(menu.getComponent());
        vo.setRedirect(menu.getRedirect());
        vo.setName(menu.getName());
        vo.setAlwaysShow(menu.getAlwaysShow());
        vo.setStatus(menu.getStatus());
        vo.setOrderNum(menu.getOrderNum());
        if (menu.getMeta() != null) {
            MenuTreeVO.Meta meta = new MenuTreeVO.Meta();
            meta.setTitle(menu.getMeta().getTitle());
            meta.setIcon(menu.getMeta().getIcon());
            meta.setDefaultOpen(menu.getMeta().getDefaultOpen());
            meta.setPermissions(menu.getMeta().getPermissions());
            vo.setMeta(meta);
        }

        return vo;
    }

    @Override
    public ApiResult<List<MenuTreeVO>> getCurrentMenuTree() {
        Criteria criteria = Criteria.where("delFlag").is(false);
        criteria.and("status").is(CommonStatus.NORMAL);
        criteria.and("type").is(MenuType.MENU);
        Query query = new Query(criteria).with(Sort.by(Sort.Direction.ASC, "orderNum"));
        List<SysMenuPermission> allList = mongoTemplate.find(query, SysMenuPermission.class);
        // 构建树形结构
        List<MenuTreeVO> tree = buildMenuTree(allList, "00");
        return ApiResult.success(tree);
    }
}
