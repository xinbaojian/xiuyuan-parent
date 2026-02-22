// MongoDB Init Data
// Database: xiuyuan-db
// Collections: 6

// === sysUser ===
db.sysUser.insertMany([
{
  _id: ObjectId('6953be5fd024de0401549bc8'),
  deptId: '6954bc7f06c6ecb531481a1a',
  loginName: 'admin',
  username: '超级管理员',
  userType: 'SYSTEM_USER',
  userSex: 'MALE',
  password: 'e70fcd91b89a84be9aa3df9f8358ffdc',
  salt: 'c496b7f2f21d480f9eaabe1d761d8297',
  avatar: '699a95e4591b8706d5291fd4',
  status: 'NORMAL',
  deleted: false,
  loginIp: '0:0:0:0:0:0:0:1',
  loginDate: ISODate('2026-02-22T06:54:56.166Z'),
  postId: '6954bc7f06c6ecb531481a19',
  roleIds: [
    '6953c65736aaf060e2eef6d5'
  ],
  createTime: ISODate('2025-12-31T06:02:39.250Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysUser'
}
,
{
  _id: ObjectId('698c5125d0bd6d57c630a9b0'),
  deptId: '6954bc7f06c6ecb531481a1a',
  loginName: 'xinbaojian',
  username: '辛保健',
  userType: 'SYSTEM_USER',
  userSex: 'MALE',
  password: 'b0a823c3be94ac9db7ce78ad98769e9d',
  salt: 'c0c12de4d03f40e0a081349ca8587fe9',
  avatar: '698c64ce318793c70b3c186a',
  status: 'NORMAL',
  deleted: false,
  loginIp: '0:0:0:0:0:0:0:1',
  loginDate: ISODate('2026-02-22T08:28:33.794Z'),
  roleIds: [
    '698c50f7d0bd6d57c630a9af'
  ],
  createTime: ISODate('2026-02-11T09:51:33.700Z'),
  updateTime: ISODate('2026-02-11T09:51:45.439Z'),
  updateBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysUser'
}
]);
// Exported: 2 / 2 documents

// === sysDept ===
db.sysDept.insertMany([
{
  _id: ObjectId('6954bc7f06c6ecb531481a1a'),
  parentId: '00',
  deptName: '修远集团',
  orderNum: 0,
  leader: '修远',
  phone: '18888888888',
  email: 'daddy@xiuyuan.xin',
  status: 'NORMAL',
  delFlag: false,
  createTime: ISODate('2025-12-31T06:02:39.226Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysDept'
}
]);
// Exported: 1 / 1 documents

// === sysConfig ===
db.sysConfig.insertMany([
{
  _id: ObjectId('6954bc7f06c6ecb531481a1d'),
  configName: '用户管理-账号初始密码',
  configKey: 'sys.user.initPassword',
  configValue: '123456',
  configType: 'Y',
  createTime: ISODate('2025-12-31T06:02:39.359Z'),
  remark: '用户管理-账号初始密码',
  _class: 'xin.xiuyuan.admin.entity.SysConfig'
}
]);
// Exported: 1 / 1 documents

// === sysPost ===
db.sysPost.insertMany([
{
  _id: ObjectId('6954bc7f06c6ecb531481a19'),
  postCode: 'CEO',
  postName: '董事长',
  orderNum: 1,
  status: 'NORMAL',
  createTime: ISODate('2025-12-31T06:02:39.201Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysPost'
}
]);
// Exported: 1 / 1 documents

// === sysMenuPermission ===
db.sysMenuPermission.insertMany([
{
  _id: ObjectId('6952450c7f34f5883eb9c6cc'),
  parentId: '00',
  type: 'MENU',
  path: '/',
  name: '',
  component: 'Layout',
  redirect: '',
  alwaysShow: false,
  orderNum: 1,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '首页',
    defaultOpen: false
  },
  createTime: ISODate('2025-12-31T06:02:39.283Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('6954bc7f06c6ecb531481a1b'),
  parentId: '6952450c7f34f5883eb9c6cc',
  type: 'MENU',
  path: '/index',
  name: 'index',
  component: '/index/index.vue',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 1,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '首页',
    icon: 'home',
    defaultOpen: false,
    permissions: 'menu:index'
  },
  createTime: ISODate('2025-12-31T06:02:39.283Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('69521743702b4a1843871a17'),
  parentId: '00',
  type: 'MENU',
  path: '/setting',
  name: 'Setting',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 2,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '系统设置',
    icon: 'cog',
    defaultOpen: false,
    permissions: 'setting:user:list'
  },
  createTime: ISODate('2025-12-31T06:02:39.283Z'),
  updateTime: ISODate('2026-02-11T09:07:12.664Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('69521bf0702b4a1843871a18'),
  parentId: '69521743702b4a1843871a17',
  type: 'MENU',
  path: 'role',
  name: 'role',
  component: '/setting/role/index.vue',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 2,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '角色管理',
    defaultOpen: false,
    permissions: 'setting:role:list'
  },
  createTime: ISODate('2025-12-31T06:02:39.311Z'),
  updateTime: ISODate('2026-02-11T09:13:51.297Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('695220f2702b4a1843871a19'),
  parentId: '69521743702b4a1843871a17',
  type: 'MENU',
  path: 'dept',
  name: 'Dept',
  component: '/setting/dept/index.vue',
  alwaysShow: false,
  orderNum: 3,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '部门管理',
    defaultOpen: false,
    permissions: 'setting:dept:list'
  },
  createTime: ISODate('2025-12-31T06:02:39.314Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('69522417702b4a1843871a1a'),
  parentId: '69521743702b4a1843871a17',
  type: 'MENU',
  path: 'post',
  name: 'Post',
  component: '/setting/post/index.vue',
  alwaysShow: false,
  orderNum: 4,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '岗位管理',
    defaultOpen: false,
    permissions: 'setting:post:list'
  },
  createTime: ISODate('2025-12-31T06:02:39.315Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('695226ab702b4a1843871a1b'),
  parentId: '69521743702b4a1843871a17',
  type: 'MENU',
  path: 'user',
  name: 'User',
  component: '/setting/user/index.vue',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 1,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '用户管理',
    defaultOpen: false,
    permissions: 'setting:user:list'
  },
  createTime: ISODate('2025-12-31T06:02:39.316Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('69522768702b4a1843871a1c'),
  parentId: '69521743702b4a1843871a17',
  type: 'MENU',
  path: 'menu',
  name: 'menu',
  component: '/setting/menu/index.vue',
  alwaysShow: false,
  orderNum: 5,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '菜单管理',
    defaultOpen: false,
    permissions: 'setting:menu:list'
  },
  createTime: ISODate('2025-12-31T06:02:39.318Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('6954bc7f06c6ecb531481a1c'),
  parentId: '69521743702b4a1843871a17',
  type: 'MENU',
  path: 'personal',
  name: 'Personal',
  component: '/setting/personal/index.vue',
  alwaysShow: false,
  orderNum: 6,
  status: 'NORMAL',
  hidden: true,
  delFlag: false,
  meta: {
    title: '个人中心',
    defaultOpen: false,
    permissions: 'setting:personal:list'
  },
  createTime: ISODate('2025-12-31T06:02:39.320Z'),
  createBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c4532d4fb89794d6ced93'),
  parentId: '695226ab702b4a1843871a1b',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 0,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '新增',
    defaultOpen: false,
    permissions: 'setting:user:add'
  },
  createTime: ISODate('2026-02-11T09:00:34.373Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c4699d0bd6d57c630a99f'),
  parentId: '695226ab702b4a1843871a1b',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 2,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '编辑',
    defaultOpen: false,
    permissions: 'setting:user:update'
  },
  createTime: ISODate('2026-02-11T09:06:33.358Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c46b1d0bd6d57c630a9a0'),
  parentId: '695226ab702b4a1843871a1b',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 3,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '删除',
    defaultOpen: false,
    permissions: 'setting:user:delete'
  },
  createTime: ISODate('2026-02-11T09:06:57.623Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c46d5d0bd6d57c630a9a1'),
  parentId: '695226ab702b4a1843871a1b',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 4,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '重置密码',
    defaultOpen: false,
    permissions: 'setting:user:resetPwd'
  },
  createTime: ISODate('2026-02-11T09:07:33.195Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c4826d0bd6d57c630a9a2'),
  parentId: '69521bf0702b4a1843871a18',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 1,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '新增',
    defaultOpen: false,
    permissions: 'setting:role:add'
  },
  createTime: ISODate('2026-02-11T09:13:10.571Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c4834d0bd6d57c630a9a3'),
  parentId: '69521bf0702b4a1843871a18',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 1,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '编辑',
    defaultOpen: false,
    permissions: 'setting:role:update'
  },
  createTime: ISODate('2026-02-11T09:13:24.981Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c4844d0bd6d57c630a9a4'),
  parentId: '69521bf0702b4a1843871a18',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 3,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '删除',
    defaultOpen: false,
    permissions: 'setting:role:delete'
  },
  createTime: ISODate('2026-02-11T09:13:40.612Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c4863d0bd6d57c630a9a5'),
  parentId: '69521bf0702b4a1843871a18',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 4,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '设置权限',
    defaultOpen: false,
    permissions: 'setting:role:permission:set'
  },
  createTime: ISODate('2026-02-11T09:14:11.117Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c489fd0bd6d57c630a9a6'),
  parentId: '695220f2702b4a1843871a19',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 1,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '新增',
    defaultOpen: false,
    permissions: 'sys:dept:add'
  },
  createTime: ISODate('2026-02-11T09:15:11.628Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c48bdd0bd6d57c630a9a7'),
  parentId: '695220f2702b4a1843871a19',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 2,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '编辑',
    defaultOpen: false,
    permissions: 'sys:dept:edit'
  },
  createTime: ISODate('2026-02-11T09:15:41.448Z'),
  updateTime: ISODate('2026-02-11T09:48:15.329Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c48cad0bd6d57c630a9a8'),
  parentId: '695220f2702b4a1843871a19',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 3,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '删除',
    defaultOpen: false,
    permissions: 'sys:dept:remove'
  },
  createTime: ISODate('2026-02-11T09:15:54.845Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c48f0d0bd6d57c630a9a9'),
  parentId: '69522417702b4a1843871a1a',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 1,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '新增',
    defaultOpen: false,
    permissions: 'setting:post:add'
  },
  createTime: ISODate('2026-02-11T09:16:32.798Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c4900d0bd6d57c630a9aa'),
  parentId: '69522417702b4a1843871a1a',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 2,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '编辑',
    defaultOpen: false,
    permissions: 'setting:post:edit'
  },
  createTime: ISODate('2026-02-11T09:16:48.915Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c490fd0bd6d57c630a9ab'),
  parentId: '69522417702b4a1843871a1a',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 3,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '删除',
    defaultOpen: false,
    permissions: 'setting:post:delete'
  },
  createTime: ISODate('2026-02-11T09:17:03.106Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c492ed0bd6d57c630a9ac'),
  parentId: '69522768702b4a1843871a1c',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 1,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '新增',
    defaultOpen: false,
    permissions: 'setting:menu:add'
  },
  createTime: ISODate('2026-02-11T09:17:34.268Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c493bd0bd6d57c630a9ad'),
  parentId: '69522768702b4a1843871a1c',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 2,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '编辑',
    defaultOpen: false,
    permissions: 'setting:menu:edit'
  },
  createTime: ISODate('2026-02-11T09:17:47.045Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('698c494ad0bd6d57c630a9ae'),
  parentId: '69522768702b4a1843871a1c',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 3,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '删除',
    defaultOpen: false,
    permissions: 'setting:menu:delete'
  },
  createTime: ISODate('2026-02-11T09:18:02.267Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('699a9f3a38f2b5d260c02e46'),
  parentId: '69521743702b4a1843871a17',
  type: 'MENU',
  path: 'global-config',
  name: 'GlobalConfig',
  component: '/setting/config/index.vue',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 7,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '参数配置',
    defaultOpen: false,
    permissions: 'setting:config:list'
  },
  createTime: ISODate('2026-02-22T06:16:26.404Z'),
  updateTime: ISODate('2026-02-22T07:10:25.742Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('699aa59e38f2b5d260c02e47'),
  parentId: '699a9f3a38f2b5d260c02e46',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 1,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '新增',
    defaultOpen: false,
    permissions: 'setting:config:add'
  },
  createTime: ISODate('2026-02-22T06:43:42.046Z'),
  updateTime: ISODate('2026-02-22T07:09:08.169Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('699aa5b138f2b5d260c02e48'),
  parentId: '699a9f3a38f2b5d260c02e46',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 2,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '编辑',
    defaultOpen: false,
    permissions: 'setting:config:edit'
  },
  createTime: ISODate('2026-02-22T06:44:01.845Z'),
  updateTime: ISODate('2026-02-22T07:09:13.820Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
,
{
  _id: ObjectId('699aa5c438f2b5d260c02e49'),
  parentId: '699a9f3a38f2b5d260c02e46',
  type: 'BUTTON',
  component: 'Layout',
  redirect: 'noRedirect',
  alwaysShow: false,
  orderNum: 3,
  status: 'NORMAL',
  hidden: false,
  delFlag: false,
  meta: {
    title: '删除',
    defaultOpen: false,
    permissions: 'setting:config:delete'
  },
  createTime: ISODate('2026-02-22T06:44:20.078Z'),
  updateTime: ISODate('2026-02-22T07:09:17.916Z'),
  _class: 'xin.xiuyuan.admin.entity.SysMenuPermission'
}
]);
// Exported: 30 / 30 documents

// === sysRole ===
db.sysRole.insertMany([
{
  _id: ObjectId('6953c65736aaf060e2eef6d5'),
  roleName: '超级管理员',
  roleKey: 'admin',
  orderNum: 1,
  status: 'NORMAL',
  permissionIds: [
    '6952450c7f34f5883eb9c6cc',
    '6954bc7f06c6ecb531481a1b',
    '69521743702b4a1843871a17',
    '695226ab702b4a1843871a1b',
    '698c4532d4fb89794d6ced93',
    '698c4699d0bd6d57c630a99f',
    '698c46b1d0bd6d57c630a9a0',
    '698c46d5d0bd6d57c630a9a1',
    '69521bf0702b4a1843871a18',
    '698c4834d0bd6d57c630a9a3',
    '698c4826d0bd6d57c630a9a2',
    '698c4844d0bd6d57c630a9a4',
    '698c4863d0bd6d57c630a9a5',
    '695220f2702b4a1843871a19',
    '698c489fd0bd6d57c630a9a6',
    '698c48bdd0bd6d57c630a9a7',
    '698c48cad0bd6d57c630a9a8',
    '69522417702b4a1843871a1a',
    '698c48f0d0bd6d57c630a9a9',
    '698c4900d0bd6d57c630a9aa',
    '698c490fd0bd6d57c630a9ab',
    '69522768702b4a1843871a1c',
    '698c492ed0bd6d57c630a9ac',
    '698c493bd0bd6d57c630a9ad',
    '698c494ad0bd6d57c630a9ae',
    '6954bc7f06c6ecb531481a1c',
    '699a9f3a38f2b5d260c02e46',
    '699aa59e38f2b5d260c02e47',
    '699aa5b138f2b5d260c02e48',
    '699aa5c438f2b5d260c02e49'
  ],
  dataScope: 'ALL',
  createTime: ISODate('2025-12-31T06:02:39.056Z'),
  updateTime: ISODate('2026-02-22T08:18:38.826Z'),
  createBy: '6953be5fd024de0401549bc8',
  updateBy: '6953be5fd024de0401549bc8',
  remark: '超级管理员',
  _class: 'xin.xiuyuan.admin.entity.SysRole'
}
,
{
  _id: ObjectId('698c50f7d0bd6d57c630a9af'),
  roleName: '普通用户',
  roleKey: 'normal',
  orderNum: 2,
  status: 'NORMAL',
  permissionIds: [
    '6952450c7f34f5883eb9c6cc',
    '6954bc7f06c6ecb531481a1b',
    '69521743702b4a1843871a17',
    '695226ab702b4a1843871a1b',
    '698c4532d4fb89794d6ced93',
    '698c4699d0bd6d57c630a99f',
    '698c46b1d0bd6d57c630a9a0',
    '698c46d5d0bd6d57c630a9a1'
  ],
  dataScope: 'SELF',
  createTime: ISODate('2026-02-11T09:50:47.854Z'),
  updateTime: ISODate('2026-02-22T09:22:06.047Z'),
  createBy: '6953be5fd024de0401549bc8',
  updateBy: '6953be5fd024de0401549bc8',
  _class: 'xin.xiuyuan.admin.entity.SysRole'
}
]);
// Exported: 2 / 2 documents

