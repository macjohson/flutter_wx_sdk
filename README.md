# wx_sdk

微信sdk插件

## Getting Started

1. 使用前需要先调用init，后面的接口直接看源文件

## 目前实现的功能
1. 微信登录
2. 分享小程序
3. 打开小程序

## 混淆

```
-keep class com.tencent.mm.opensdk.** {
    *;
}

-keep class com.tencent.wxop.** {
    *;
}

-keep class com.tencent.mm.sdk.** {
    *;
}
```
