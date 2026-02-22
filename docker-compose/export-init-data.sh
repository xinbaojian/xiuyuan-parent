#!/bin/bash

# MongoDB 数据导出脚本
# 从现有数据库生成 init-data.js 文件

set -e

CONTAINER_NAME="xiuyuan-mongo-1"
DB_USER="xinbaojian"
DB_PASSWORD="JD0PjLYMZd1QPrQHyHWF"
AUTH_DB="admin"
DB_NAME="xiuyuan-db"
OUTPUT_DIR="mongo-init"
OUTPUT_FILE="$OUTPUT_DIR/init-data.js"

echo "=========================================="
echo "MongoDB Data Export"
echo "=========================================="
echo ""

# 检查 Docker 容器是否运行
if ! docker ps --format "{{.Names}}" | grep -q "mongo"; then
    echo "[ERROR] MongoDB container not running!"
    echo "Please run: docker-compose up -d"
    exit 1
fi

echo "[OK] MongoDB is running"
echo ""

# 创建输出目录
mkdir -p "$OUTPUT_DIR"

echo "Exporting data from MongoDB..."
echo ""

# 执行导出
docker exec "$CONTAINER_NAME" mongosh \
    --username="$DB_USER" \
    --password="$DB_PASSWORD" \
    --authenticationDatabase="$AUTH_DB" \
    --quiet \
    "$DB_NAME" \
    --eval '
var cols = db.getCollectionNames().filter(function(n) {
    return !n.startsWith("system.") && n !== "sysAnnex";
});

print("// MongoDB Init Data");
print("// Database: xiuyuan-db");
print("// Collections: " + cols.length);
print("");

cols.forEach(function(col) {
    if (col !== "system.indexes") {
        print("// === " + col + " ===");
        var count = db.getCollection(col).countDocuments();
        var limit = Math.min(count, 100);

        if (limit > 0) {
            print("db." + col + ".insertMany([");
            var i = 0;
            db.getCollection(col).find().limit(limit).forEach(function(doc) {
                if (i++ > 0) print(",");
                printjson(doc);
            });
            print("]);");
            print("// Exported: " + i + " / " + count + " documents");
        } else {
            print("// Empty collection");
        }
        print("");
    }
});
' > "$OUTPUT_FILE"

# 检查结果
if [ $? -eq 0 ] && [ -s "$OUTPUT_FILE" ]; then
    echo "=========================================="
    echo "[SUCCESS] Export completed!"
    echo "=========================================="
    echo ""
    echo "File: $OUTPUT_FILE"
    echo "Size: $(wc -c < "$OUTPUT_FILE") bytes"
    echo "Lines: $(wc -l < "$OUTPUT_FILE")"
    echo ""
    echo "Next steps:"
    echo "  1. View file: cat $OUTPUT_FILE"
    echo "  2. Test: docker-compose down -v && rm -rf datadir && docker-compose up -d"
    echo ""

    # 显示前 20 行预览
    echo "=========================================="
    echo "Preview (first 20 lines):"
    echo "=========================================="
    head -n 20 "$OUTPUT_FILE"
    echo "=========================================="
    echo ""
else
    echo "[ERROR] Export failed!"
    echo ""
    echo "Error output:"
    cat "$OUTPUT_FILE"
    exit 1
fi
