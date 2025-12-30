db.getCollection('sysPost').insertMany(
    [
        {
            "_id": ObjectId("694204d84a8a69eb0e777825"),
            "_class": "xin.xiuyuan.admin.entity.SysPost",
            "createTime": {"$date": "2025-12-17T01:18:16.878Z"},
            "orderNum": 1,
            "postCode": "CEO",
            "postName": "董事长",
            "remark": "董事长",
            "status": "NORMAL"
            },
        {
            "_id": ObjectId("694204f54a8a69eb0e777826"),
            "_class": "xin.xiuyuan.admin.entity.SysPost",
            "createTime": {"$date": "2025-12-17T01:18:45.704Z"},
            "orderNum": 2,
            "postCode": "SE",
            "postName": "项目经理",
            "remark": "项目经理",
            "status": "NORMAL",
            "updateTime": {"$date": "2025-12-17T01:20:08.756Z"}
            },
        {
            "_id": ObjectId("694205034a8a69eb0e777827"),
            "_class": "xin.xiuyuan.admin.entity.SysPost",
            "createTime": {"$date": "2025-12-17T01:18:59.844Z"},
            "orderNum": 3,
            "postCode": "HR",
            "postName": "人力资源",
            "remark": "人力资源",
            "status": "NORMAL"
            },
        {
            "_id": ObjectId("694205114a8a69eb0e777828"),
            "_class": "xin.xiuyuan.admin.entity.SysPost",
            "createTime": {"$date": "2025-12-17T01:19:13.525Z"},
            "orderNum": 4,
            "postCode": "USER",
            "postName": "普通员工",
            "remark": "普通员工",
            "status": "NORMAL"
            }
        ]
)