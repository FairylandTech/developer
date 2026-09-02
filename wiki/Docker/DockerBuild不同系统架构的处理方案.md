# Docker Build 不同系统架构的处理方案

> @software: IntelliJ IDEA  
> @author: [Beau Dean](https://fairy.host)  
> @contact: [Blog](https://blog.fairy.host/) | [GitHub](https://github.com/FairylandTech) | [Telegram](https://t.me/FairylandFuture)  
> @organization: [GitHub·FairylandFuture](https://github.com/FairylandFuture)  
> @datetime: 2026-09-02 10:26:44 UTC+08:00

[![Author](https://img.shields.io/badge/Author-Beau_Dean-orange)](https://t.me/FairylandFuture) [![github](https://img.shields.io/badge/Github-FairylandFuture-green)](https://github.com/FairylandTech) [![GitBook](https://img.shields.io/badge/GitBook-Interesting-green)](https://interestingbooks.gitbook.io/) [![Editor](https://img.shields.io/badge/Editor-Typora-yellow)]() [![Language](https://img.shields.io/badge/Language-Markdown-orange)]() [![Version](https://img.shields.io/badge/Version-Release-blue)]() [![Docs](https://img.shields.io/badge/Docs-Passing-brightgreen)]() [![Type](https://img.shields.io/badge/Type-Documents-blue)]() [![wakatime](https://wakatime.com/badge/user/fa851759-c657-4b1e-8bcb-3ec3a693a2cd.svg)](https://wakatime.com/@fa851759-c657-4b1e-8bcb-3ec3a693a2cd) [![Sign](https://img.shields.io/badge/%E7%AD%89%E6%88%91%E4%BB%A3%E7%A0%81%E7%BC%96%E6%88%90-%E5%A8%B6%E4%BD%A0%E4%B8%BA%E5%A6%BB%E5%8F%AF%E5%A5%BD-red)](https://github.com/FairylandTech)

---

> 在部署服务时由于生产环境和测试环境的一系列差异性问题，包括不限于系统架构，docker 版本，网络情况等因素需要使用`docker save`和`docker load`来进行 Image 的迁移的运行。

## 使用 Docker buildx 进行打包

Docker 23+ 的 `docker build` 默认走引擎内置的 BuildKit，不依赖 buildx 插件

服务器是 **linux/amd64**，要在上面构建 arm64 镜像，无论用 `docker build` 还是 `docker buildx`，都得先注册 QEMU 模拟器

```shell
# 注册 QEMU 模拟器
docker run --privileged --rm tonistiigi/binfmt --install all
```

创建多架构 builder

```shell
# 创建多架构 builder
docker buildx create --name multiarch --driver docker-container --use
docker buildx inspect --bootstrap
# 验证，应看到 multiarch (docker-container) 带 * 号，且 platforms 包含 arm64
docker buildx ls
```
