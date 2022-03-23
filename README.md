# react-native-push

## Getting started

`$ npm install react-native-push --save`

### Mostly automatic installation

`$ react-native link react-native-push`

## Usage
```javascript
import ReactNativePush from 'react-native-push';

// TODO: What to do with the module?
ReactNativePush;
```


### 华为

https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides/android-config-agc-0000001050170137
- 华为后台创建项目与应用
- 签名校验SHA1256
- 开通推送服务
```
// build.gradle
buildscript {
    repositories {
        google()
        // 配置HMS Core SDK的Maven仓地址。
        maven {url 'https://developer.huawei.com/repo/'}
    }
}
```
```xml

<!--AndroidManifest.xml-->
<meta-data
    android:name="com.huawei.hms.client.appid"
    android:value="appId"/>

```
