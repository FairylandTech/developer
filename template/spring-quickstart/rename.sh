#!/bin/bash

################################################################################
# Spring DDD 项目名称修改脚本 (Mac/Linux)
# 功能: 将项目名称从 quickstart 修改为指定的新名称
# 用法: bash rename.sh <new-project-name>
# 示例: bash rename.sh web-move
################################################################################

set -e

# 颜色定义用于终端输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 检查参数
if [ -z "$1" ]; then
    echo -e "${RED}错误: 请提供新的项目名称${NC}"
    echo "用法: bash rename.sh <new-project-name>"
    echo "示例: bash rename.sh web-move"
    exit 1
fi

OLD_NAME="quickstart"
NEW_NAME="$1"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# 打印开始信息
echo "================================================================================"
echo "Spring DDD 项目名称修改脚本"
echo "================================================================================"
echo "旧项目名称: $OLD_NAME"
echo "新项目名称: $NEW_NAME"
echo "项目路径: $SCRIPT_DIR"
echo "================================================================================"
echo ""

# 检查目录是否存在
if [ ! -d "$SCRIPT_DIR/$OLD_NAME-bootstrap" ]; then
    echo -e "${RED}错误: 项目目录不存在或项目名称不是 $OLD_NAME${NC}"
    exit 1
fi

# ============================================================================
# [1/3] 重命名目录
# ============================================================================
echo -e "${YELLOW}[1/3] 重命名目录...${NC}"

modules=("bootstrap" "interface" "facade" "application" "domain" "infrastructure")

for module in "${modules[@]}"; do
    old_dir="$SCRIPT_DIR/${OLD_NAME}-${module}"
    new_dir="$SCRIPT_DIR/${NEW_NAME}-${module}"
    
    if [ -d "$old_dir" ]; then
        mv "$old_dir" "$new_dir"
        echo -e "  ${GREEN}✓${NC} ${OLD_NAME}-${module} → ${NEW_NAME}-${module}"
    fi
done
echo ""

# ============================================================================
# [2/3] 修改根目录 settings.gradle.kts
# ============================================================================
echo -e "${YELLOW}[2/3] 修改根目录 settings.gradle.kts...${NC}"

SETTINGS_FILE="$SCRIPT_DIR/settings.gradle.kts"
if [ -f "$SETTINGS_FILE" ]; then
    # 在 macOS 和 Linux 中使用不同的 sed 参数
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        sed -i '' "s/rootProject.name = \"$OLD_NAME\"/rootProject.name = \"$NEW_NAME\"/g" "$SETTINGS_FILE"
        for module in "${modules[@]}"; do
            sed -i '' "s/include(\"$OLD_NAME-$module\")/include(\"$NEW_NAME-$module\")/g" "$SETTINGS_FILE"
        done
    else
        # Linux
        sed -i "s/rootProject.name = \"$OLD_NAME\"/rootProject.name = \"$NEW_NAME\"/g" "$SETTINGS_FILE"
        for module in "${modules[@]}"; do
            sed -i "s/include(\"$OLD_NAME-$module\")/include(\"$NEW_NAME-$module\")/g" "$SETTINGS_FILE"
        done
    fi
    echo -e "  ${GREEN}✓${NC} settings.gradle.kts 已更新"
else
    echo -e "  ${RED}✗${NC} settings.gradle.kts 不存在"
fi
echo ""

# ============================================================================
# [3/3] 修改所有 build.gradle.kts 文件
# ============================================================================
echo -e "${YELLOW}[3/3] 修改所有 build.gradle.kts 文件...${NC}"

# 修改根目录 build.gradle.kts
BUILD_FILE="$SCRIPT_DIR/build.gradle.kts"
if [ -f "$BUILD_FILE" ]; then
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        sed -i '' "s/description = \"$OLD_NAME-parent\"/description = \"$NEW_NAME-parent\"/g" "$BUILD_FILE"
    else
        # Linux
        sed -i "s/description = \"$OLD_NAME-parent\"/description = \"$NEW_NAME-parent\"/g" "$BUILD_FILE"
    fi
    echo -e "  ${GREEN}✓${NC} build.gradle.kts (根目录) 已更新"
fi

# 修改各子模块 build.gradle.kts
for module in "${modules[@]}"; do
    MODULE_BUILD="$SCRIPT_DIR/${NEW_NAME}-${module}/build.gradle.kts"
    
    if [ -f "$MODULE_BUILD" ]; then
        # 替换所有 project() 依赖引用
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # macOS
            for dep_module in "${modules[@]}"; do
                sed -i '' "s/project(\":$OLD_NAME-$dep_module\")/project(\":$NEW_NAME-$dep_module\")/g" "$MODULE_BUILD"
            done
        else
            # Linux
            for dep_module in "${modules[@]}"; do
                sed -i "s/project(\":$OLD_NAME-$dep_module\")/project(\":$NEW_NAME-$dep_module\")/g" "$MODULE_BUILD"
            done
        fi
        echo -e "  ${GREEN}✓${NC} build.gradle.kts (${NEW_NAME}-${module}) 已更新"
    fi
done
echo ""

# ============================================================================
# 完成信息
# ============================================================================
echo "================================================================================"
echo -e "${GREEN}✓ 项目名称修改完成！${NC}"
echo "================================================================================"
echo "下一步建议:"
echo "  1. 在新项目目录下运行: ./gradlew clean build"
echo "  2. 更新 IDEA/IDE 以重新加载项目配置"
echo "================================================================================"

exit 0
