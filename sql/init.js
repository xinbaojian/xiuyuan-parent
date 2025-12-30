db.getCollection('sysRole').insertMany([
    {
        "_id": ObjectId("69421944a154ccab9ddea6f5"),
        "_class": "xin.xiuyuan.admin.entity.SysRole",
        "createTime": ISODate("2025-12-17T02:45:24.422Z"),
        "orderNum": 0,
        "permissions": [
            {
                "_id": ObjectId("69521743702b4a1843871a17"),
                "parentId": "00",
                "type": "MENU",
                "path": "/setting",
                "name": "Setting",
                "component": "Layout",
                "alwaysShow": false,
                "orderNum": 2,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "系统设置",
                    "icon": "cog",
                    "defaultOpen": false,
                    "permissions": "menu:setting"
                    },
                "createTime": ISODate("2025-12-29T05:53:07.862Z")
                },
            {
                "_id": ObjectId("69521bf0702b4a1843871a18"),
                "parentId": "69521743702b4a1843871a17",
                "type": "MENU",
                "path": "role",
                "name": "role",
                "component": "/setting/role/index.vue",
                "alwaysShow": false,
                "orderNum": 2,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "角色管理",
                    "defaultOpen": false,
                    "permissions": "menu:setting:role:list"
                    },
                "createTime": ISODate("2025-12-29T06:13:04.884Z"),
                "updateTime": ISODate("2025-12-29T07:02:08.529Z")
                },
            {
                "_id": ObjectId("695220f2702b4a1843871a19"),
                "parentId": "69521743702b4a1843871a17",
                "type": "MENU",
                "path": "dept",
                "name": "Dept",
                "component": "/setting/dept/index.vue",
                "alwaysShow": false,
                "orderNum": 3,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "部门管理",
                    "defaultOpen": false,
                    "permissions": "menu:setting:dept:list"
                    },
                "createTime": ISODate("2025-12-29T06:34:26.853Z"),
                "updateTime": ISODate("2025-12-29T07:02:13.087Z")
                },
            {
                "_id": ObjectId("69522417702b4a1843871a1a"),
                "parentId": "69521743702b4a1843871a17",
                "type": "MENU",
                "path": "post",
                "name": "Post",
                "component": "/setting/post/index.vue",
                "alwaysShow": false,
                "orderNum": 4,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "岗位管理",
                    "defaultOpen": false,
                    "permissions": "menu:setting:post:list"
                    },
                "createTime": ISODate("2025-12-29T06:47:51.584Z"),
                "updateTime": ISODate("2025-12-29T06:59:36.939Z")
                },
            {
                "_id": ObjectId("695226ab702b4a1843871a1b"),
                "parentId": "69521743702b4a1843871a17",
                "type": "MENU",
                "path": "user",
                "name": "User",
                "component": "/setting/user/index.vue",
                "redirect": "noRedirect",
                "alwaysShow": false,
                "orderNum": 1,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "用户管理",
                    "defaultOpen": false,
                    "permissions": "menu:setting:user:list"
                    },
                "createTime": ISODate("2025-12-29T06:58:51.841Z"),
                "updateTime": ISODate("2025-12-29T10:00:14.123Z")
                },
            {
                "_id": ObjectId("69522768702b4a1843871a1c"),
                "parentId": "69521743702b4a1843871a17",
                "type": "MENU",
                "path": "menu",
                "name": "menu",
                "component": "/setting/menu/index.vue",
                "alwaysShow": false,
                "orderNum": 5,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "菜单管理",
                    "defaultOpen": false,
                    "permissions": "menu:setting:menu:list"
                    },
                "createTime": ISODate("2025-12-29T07:02:00.847Z"),
                "updateTime": ISODate("2025-12-29T07:02:48.177Z")
                },
            {
                "_id": ObjectId("6952450c7f34f5883eb9c6cc"),
                "parentId": "00",
                "type": "MENU",
                "path": "/",
                "name": "",
                "component": "Layout",
                "redirect": "",
                "alwaysShow": false,
                "orderNum": 1,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "index",
                    "defaultOpen": false
                    },
                "createTime": ISODate("2025-12-29T09:08:28.265Z")
                },
            {
                "_id": ObjectId("695245cd7f34f5883eb9c6cd"),
                "parentId": "6952450c7f34f5883eb9c6cc",
                "type": "MENU",
                "path": "/index",
                "name": "Index",
                "component": "/index/index.vue",
                "redirect": "noRedirect",
                "alwaysShow": true,
                "orderNum": 1,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "首页",
                    "icon": "home",
                    "defaultOpen": false,
                    "permissions": "menu:index"
                    },
                "createTime": ISODate("2025-12-29T09:11:41.992Z")
                }
            ],
        "roleKey": "admin",
        "roleName": "超级管理员",
        "status": "NORMAL"
        },
    {
        "_id": ObjectId("694622b45b1556d0447c68c6"),
        "_class": "xin.xiuyuan.admin.entity.SysRole",
        "createTime": ISODate("2025-12-20T04:14:44.208Z"),
        "orderNum": 1,
        "permissions": [
            {
                "_id": ObjectId("69521743702b4a1843871a17"),
                "parentId": "00",
                "type": "MENU",
                "path": "/setting",
                "name": "Setting",
                "component": "Layout",
                "alwaysShow": false,
                "orderNum": 2,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "系统设置",
                    "icon": "cog",
                    "defaultOpen": false,
                    "permissions": "menu:setting"
                    },
                "createTime": ISODate("2025-12-29T05:53:07.862Z")
                },
            {
                "_id": ObjectId("695226ab702b4a1843871a1b"),
                "parentId": "69521743702b4a1843871a17",
                "type": "MENU",
                "path": "user",
                "name": "User",
                "component": "/setting/user/index.vue",
                "redirect": "noRedirect",
                "alwaysShow": false,
                "orderNum": 1,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "用户管理",
                    "defaultOpen": false,
                    "permissions": "menu:setting:user:list"
                    },
                "createTime": ISODate("2025-12-29T06:58:51.841Z"),
                "updateTime": ISODate("2025-12-29T10:00:14.123Z")
                },
            {
                "_id": ObjectId("6952450c7f34f5883eb9c6cc"),
                "parentId": "00",
                "type": "MENU",
                "path": "/",
                "name": "",
                "component": "Layout",
                "redirect": "",
                "alwaysShow": false,
                "orderNum": 1,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "index",
                    "defaultOpen": false
                    },
                "createTime": ISODate("2025-12-29T09:08:28.265Z")
                },
            {
                "_id": ObjectId("695245cd7f34f5883eb9c6cd"),
                "parentId": "6952450c7f34f5883eb9c6cc",
                "type": "MENU",
                "path": "/index",
                "name": "Index",
                "component": "/index/index.vue",
                "redirect": "noRedirect",
                "alwaysShow": true,
                "orderNum": 1,
                "status": "NORMAL",
                "delFlag": false,
                "meta": {
                    "title": "首页",
                    "icon": "home",
                    "defaultOpen": false,
                    "permissions": "menu:index"
                    },
                "createTime": ISODate("2025-12-29T09:11:41.992Z")
                }
            ],
        "roleKey": "common",
        "roleName": "普通用户",
        "status": "NORMAL",
        "createBy": "6944cd0b8d4cb0720e49dcb1"
        }
    ]);


db.getCollection("sysUser1").insertOne(
{
    "_id": ObjectId("6953a1d638b3aad9b7b98310"),
    "_class": "xin.xiuyuan.admin.entity.SysUser",
    "createTime": {"$date": "2025-12-30T09:56:38.489Z"},
    "deleted": false,
    "loginName": "admin",
    "password": "9d4d00b563f04c599f04f1a4cc29370f",
    "roles": [
      {
        "_id": ObjectId("69421944a154ccab9ddea6f5"),
        "roleName": "超级管理员",
        "roleKey": "admin",
        "orderNum": 0,
        "status": "NORMAL",
        "permissions": [
          {
            "_id": ObjectId("69521743702b4a1843871a17"),
            "parentId": "00",
            "type": "MENU",
            "path": "/setting",
            "name": "Setting",
            "component": "Layout",
            "alwaysShow": false,
            "orderNum": 2,
            "status": "NORMAL",
            "delFlag": false,
            "meta": {
              "title": "系统设置",
              "icon": "cog",
              "defaultOpen": false,
              "permissions": "menu:setting"
            },
            "createTime": {"$date": "2025-12-29T05:53:07.862Z"}
          },
          {
            "_id": ObjectId("69521bf0702b4a1843871a18"),
            "parentId": "69521743702b4a1843871a17",
            "type": "MENU",
            "path": "role",
            "name": "role",
            "component": "/setting/role/index.vue",
            "alwaysShow": false,
            "orderNum": 2,
            "status": "NORMAL",
            "delFlag": false,
            "meta": {
              "title": "角色管理",
              "defaultOpen": false,
              "permissions": "menu:setting:role:list"
            },
            "createTime": {"$date": "2025-12-29T06:13:04.884Z"},
            "updateTime": {"$date": "2025-12-29T07:02:08.529Z"}
          },
          {
            "_id": ObjectId("695220f2702b4a1843871a19"),
            "parentId": "69521743702b4a1843871a17",
            "type": "MENU",
            "path": "dept",
            "name": "Dept",
            "component": "/setting/dept/index.vue",
            "alwaysShow": false,
            "orderNum": 3,
            "status": "NORMAL",
            "delFlag": false,
            "meta": {
              "title": "部门管理",
              "defaultOpen": false,
              "permissions": "menu:setting:dept:list"
            },
            "createTime": {"$date": "2025-12-29T06:34:26.853Z"},
            "updateTime": {"$date": "2025-12-29T07:02:13.087Z"}
          },
          {
            "_id": ObjectId("69522417702b4a1843871a1a"),
            "parentId": "69521743702b4a1843871a17",
            "type": "MENU",
            "path": "post",
            "name": "Post",
            "component": "/setting/post/index.vue",
            "alwaysShow": false,
            "orderNum": 4,
            "status": "NORMAL",
            "delFlag": false,
            "meta": {
              "title": "岗位管理",
              "defaultOpen": false,
              "permissions": "menu:setting:post:list"
            },
            "createTime": {"$date": "2025-12-29T06:47:51.584Z"},
            "updateTime": {"$date": "2025-12-29T06:59:36.939Z"}
          },
          {
            "_id": ObjectId("695226ab702b4a1843871a1b"),
            "parentId": "69521743702b4a1843871a17",
            "type": "MENU",
            "path": "user",
            "name": "User",
            "component": "/setting/user/index.vue",
            "redirect": "noRedirect",
            "alwaysShow": false,
            "orderNum": 1,
            "status": "NORMAL",
            "delFlag": false,
            "meta": {
              "title": "用户管理",
              "defaultOpen": false,
              "permissions": "menu:setting:user:list"
            },
            "createTime": {"$date": "2025-12-29T06:58:51.841Z"},
            "updateTime": {"$date": "2025-12-29T10:00:14.123Z"}
          },
          {
            "_id": ObjectId("69522768702b4a1843871a1c"),
            "parentId": "69521743702b4a1843871a17",
            "type": "MENU",
            "path": "menu",
            "name": "menu",
            "component": "/setting/menu/index.vue",
            "alwaysShow": false,
            "orderNum": 5,
            "status": "NORMAL",
            "delFlag": false,
            "meta": {
              "title": "菜单管理",
              "defaultOpen": false,
              "permissions": "menu:setting:menu:list"
            },
            "createTime": {"$date": "2025-12-29T07:02:00.847Z"},
            "updateTime": {"$date": "2025-12-29T07:02:48.177Z"}
          },
          {
            "_id": ObjectId("6952450c7f34f5883eb9c6cc"),
            "parentId": "00",
            "type": "MENU",
            "path": "/",
            "name": "",
            "component": "Layout",
            "redirect": "",
            "alwaysShow": false,
            "orderNum": 1,
            "status": "NORMAL",
            "delFlag": false,
            "meta": {
              "title": "index",
              "defaultOpen": false
            },
            "createTime": {"$date": "2025-12-29T09:08:28.265Z"}
          },
          {
            "_id": ObjectId("695245cd7f34f5883eb9c6cd"),
            "parentId": "6952450c7f34f5883eb9c6cc",
            "type": "MENU",
            "path": "/index",
            "name": "Index",
            "component": "/index/index.vue",
            "redirect": "noRedirect",
            "alwaysShow": true,
            "orderNum": 1,
            "status": "NORMAL",
            "delFlag": false,
            "meta": {
              "title": "首页",
              "icon": "home",
              "defaultOpen": false,
              "permissions": "menu:index"
            },
            "createTime": {"$date": "2025-12-29T09:11:41.992Z"}
          }
        ],
        "createTime": {"$date": "2025-12-17T02:45:24.422Z"}
      }
    ],
    "salt": "918d6a098dab43268cab99b7f85124ac",
    "status": "NORMAL",
    "updateBy": "6953a1d638b3aad9b7b98310",
    "updateTime": {"$date": "2025-12-30T10:00:34.715Z"},
    "userSex": "MALE",
    "userType": "SYSTEM_USER",
    "username": "超级管理员"
  }
);