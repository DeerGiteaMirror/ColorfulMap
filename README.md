<div align="center">

# ColorfulMap

<img src="https://ssl.lunadeer.cn:14437/i/2024/02/21/65d5e0d10b7d5.png" alt="" width="70%">

### [开源地址](https://ssl.lunadeer.cn:14446/zhangyuheng/ColorfulMap) | [文档地址](https://ssl.lunadeer.cn:14448/doc/26/)

### [下载页面](https://ssl.lunadeer.cn:14446/zhangyuheng/ColorfulMap/releases)

### [统计页面](https://bstats.org/plugin/bukkit/ColorfulMap/21443) | [Hangar](https://hangar.papermc.io/zhangyuheng/ColorfulMap)

</div>

## 简介

用于将图片转换为地图画，悬挂在展示框上增添装饰。

## 说明

本插件大约相当于 [ImageFrame](https://github.com/LOOHP/ImageFrame)
的简版以及 [ImageMaps](https://github.com/SydMontague/ImageMaps) 的高版本重制版。前者功能丰富，不过可能由于项目体量较大，对于新版本的兼容较慢，后者在
1.18 开始就停止了更新，切不支持 Folia 核心。

## 功能介绍

- 将图片转换为地图画；
- 图片缩放；
- 自动放置；
- 支持消耗金钱生成地图画（需要 Vault 前置）；

## 支持版本

- 1.20.1+ (Spigot、Paper、Folia)

## 安装方法

1. 将插件放入服务器的 `plugins` 目录下
2. 重启服务器
3. 在 `plugins/ColorfulMap/config.yml` 中配置
4. 重启服务器

## 玩家使用方法

1. 首先需要将你想要转换的图片上传到 [图床](https://ssl.lunadeer.cn:14437/)  ，便于本插件从网络读取图片内容。上传完成后会得到一个图片的网络地址，复制此地址。

2. 在游戏中输入指令：`/tomap <图片地址>` 即可获得一张地图

   ![](https://ssl.lunadeer.cn:14437/i/2024/07/30/66a89345ca899.png)

   地图的lore中记录了此地图画的大小，下图中的 8x10 表明此地图画需要长8格、高10格的展示框阵列才能放置。

   ![](https://ssl.lunadeer.cn:14437/i/2024/07/30/66a893ba56d7d.png)

3. 对着展示框阵列的**左下角展示框**摆放此地图，则会自动在墙上的剩余展示框内放置对应的地图。

4. 如果图片太大或太小可以尝试在指令后加入缩放倍率，例如 `/tomap <图片地址> 0.5` 表示将以原图的50%大小渲染。如果你希望将图片填满所有地图边缘处没有留白，那么你需要保证你的图片的长宽分辨率均为**128的倍数**，因为在MC中一张地图的分辨率为128x128。

## 管理员指南

## 指令 & 权限节点

> 以下指令中尖括号 `<>` 表示必填，方括号 `[]` 表示选填。

| 功能    | 指令                     | 权限节点               | 默认   |
|-------|------------------------|--------------------|------|
| 生成地图画 | `/tomap <图片地址> [缩放倍率]` | colorfulmap.tomap  | true |
| 重载配置  | `/reloadColorfulMap`   | colorfulmap.reload | op   |

## 如何查图

2.0版本开始地图画会保存一份玩家获取图片的原图，路径为 `plugins/ColorfulMap/maps/{图片uuid}/raw.png` ，方便服主快速查阅图片内容。

当玩家放置地图后还会同时保存一份元数据文件，`plugins/ColorfulMap/maps/{图片uuid}/meta.txt`，此文件记录了放置地图的玩家、放置时间、地图画的位置等信息。

## 配置文件参考

```yaml
MaxFrameX: 32

MaxFrameY: 18

CheckUpdate: true

Economy:
   Enable: false
   CostPerMap: 100.0

Debug: false
```

## 建议与反馈

Mail: [zhangyuheng@lunadeer.cn](mailto:zhangyuheng@lunadeer.cn)

QQ群：309428300

## 统计

![bstats](https://bstats.org/signatures/bukkit/ColorfulMap.svg)

