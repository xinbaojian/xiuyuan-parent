db.getCollection('sysMenuPermission').insertMany(
    [
        {
            "_id": ObjectId("69521743702b4a1843871a17"),
            "_class": "xin.xiuyuan.admin.entity.SysMenuPermission",
            "alwaysShow": false,
            "component": "Layout",
            "createTime": {"$date": "2025-12-29T05:53:07.862Z"},
            "delFlag": false,
            "meta": {
                "title": "系统设置",
                "icon": "cog",
                "defaultOpen": false,
                "permissions": "menu:setting"
                },
            "name": "Setting",
            "orderNum": 2,
            "parentId": "00",
            "path": "/setting",
            "status": "NORMAL",
            "type": "MENU"
            },
        {
            "_id": ObjectId("69521bf0702b4a1843871a18"),
            "_class": "xin.xiuyuan.admin.entity.SysMenuPermission",
            "alwaysShow": false,
            "component": "/setting/role/index.vue",
            "createTime": {"$date": "2025-12-29T06:13:04.884Z"},
            "delFlag": false,
            "meta": {
                "title": "角色管理",
                "defaultOpen": false,
                "permissions": "menu:setting:role:list"
                },
            "name": "role",
            "orderNum": 2,
            "parentId": "69521743702b4a1843871a17",
            "path": "role",
            "status": "NORMAL",
            "type": "MENU",
            "updateTime": {"$date": "2025-12-29T07:02:08.529Z"}
            },
        {
            "_id": ObjectId("695220f2702b4a1843871a19"),
            "_class": "xin.xiuyuan.admin.entity.SysMenuPermission",
            "alwaysShow": false,
            "component": "/setting/dept/index.vue",
            "createTime": {"$date": "2025-12-29T06:34:26.853Z"},
            "delFlag": false,
            "meta": {
                "title": "部门管理",
                "defaultOpen": false,
                "permissions": "menu:setting:dept:list"
                },
            "name": "Dept",
            "orderNum": 3,
            "parentId": "69521743702b4a1843871a17",
            "path": "dept",
            "status": "NORMAL",
            "type": "MENU",
            "updateTime": {"$date": "2025-12-29T07:02:13.087Z"}
            },
        {
            "_id": ObjectId("69522417702b4a1843871a1a"),
            "_class": "xin.xiuyuan.admin.entity.SysMenuPermission",
            "alwaysShow": false,
            "component": "/setting/post/index.vue",
            "createTime": {"$date": "2025-12-29T06:47:51.584Z"},
            "delFlag": false,
            "meta": {
                "title": "岗位管理",
                "defaultOpen": false,
                "permissions": "menu:setting:post:list"
                },
            "name": "Post",
            "orderNum": 4,
            "parentId": "69521743702b4a1843871a17",
            "path": "post",
            "status": "NORMAL",
            "type": "MENU",
            "updateTime": {"$date": "2025-12-29T06:59:36.939Z"}
            },
        {
            "_id": ObjectId("695226ab702b4a1843871a1b"),
            "_class": "xin.xiuyuan.admin.entity.SysMenuPermission",
            "alwaysShow": false,
            "component": "/setting/user/index.vue",
            "createTime": {"$date": "2025-12-29T06:58:51.841Z"},
            "delFlag": false,
            "meta": {
                "title": "用户管理",
                "defaultOpen": false,
                "permissions": "setting:user:list"
                },
            "name": "User",
            "orderNum": 1,
            "parentId": "69521743702b4a1843871a17",
            "path": "user",
            "status": "NORMAL",
            "type": "MENU",
            "updateTime": {"$date": "2025-12-30T09:43:30.647Z"},
            "redirect": "noRedirect"
            },
        {
            "_id": ObjectId("69522768702b4a1843871a1c"),
            "_class": "xin.xiuyuan.admin.entity.SysMenuPermission",
            "alwaysShow": false,
            "component": "/setting/menu/index.vue",
            "createTime": {"$date": "2025-12-29T07:02:00.847Z"},
            "delFlag": false,
            "meta": {
                "title": "菜单管理",
                "defaultOpen": false,
                "permissions": "menu:setting:menu:list"
                },
            "name": "menu",
            "orderNum": 5,
            "parentId": "69521743702b4a1843871a17",
            "path": "menu",
            "status": "NORMAL",
            "type": "MENU",
            "updateTime": {"$date": "2025-12-29T07:02:48.177Z"}
            },
        {
            "_id": ObjectId("6952450c7f34f5883eb9c6cc"),
            "_class": "xin.xiuyuan.admin.entity.SysMenuPermission",
            "alwaysShow": false,
            "component": "Layout",
            "createTime": {"$date": "2025-12-29T09:08:28.265Z"},
            "delFlag": false,
            "meta": {
                "title": "index",
                "defaultOpen": false
                },
            "name": "",
            "orderNum": 1,
            "parentId": "00",
            "path": "/",
            "status": "NORMAL",
            "type": "MENU",
            "redirect": ""
            },
        {
            "_id": ObjectId("695245cd7f34f5883eb9c6cd"),
            "_class": "xin.xiuyuan.admin.entity.SysMenuPermission",
            "alwaysShow": true,
            "component": "/index/index.vue",
            "createTime": {"$date": "2025-12-29T09:11:41.992Z"},
            "delFlag": false,
            "meta": {
                "title": "首页",
                "icon": "home",
                "defaultOpen": false,
                "permissions": "menu:index"
                },
            "name": "Index",
            "orderNum": 1,
            "parentId": "6952450c7f34f5883eb9c6cc",
            "path": "/index",
            "status": "NORMAL",
            "type": "MENU",
            "redirect": "noRedirect"
            }
        ]
);