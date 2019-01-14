# ShareSDK for Android

文档语言 : 中文 | [English](https://github.com/MobClub/ShareSDK-for-Android/blob/master/README_EN.md)

- ShareSDK是全球最流行的应用和手机游戏社交SDK !到目前为止，我们已经支持了10多万个客户。
ShareSDK可以轻松支持世界上40多个社交平台的第三方登录、分享和与好友列表操作。短短几个小时，这个小程序包将使您的应用程序完全社会化!
想在中国社交平台上发布你的应用吗?这可能是你最好的选择!

- website -- http://www.mob.com
- wiki -- http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97
- bbs -- http://bbs.mob.com/forum-36-1.html

# 第一步:下载SDK

请浏览我们的[官方网站](http://www.mob.com/)及下载最新版本的ShareSDK。提取下载文件后,你会发现以下目录结构:

![directory structure](http://a1.qpic.cn/psb?/V14GftmO22fJgW/D2jYRnjuvUQiyfrGferrGKctas.joCRNNESfma6IB0M!/b/dBUAAAAAAAAA&bo=hAPIAQAAAAAFB2o!&rf=viewer_4)

打开Android**目录下的**ShareSDK，你会发现**MainLibs**和**OnekeyShare**。ShareSDK存储在MainLibs目录中，OnekeyShare是开发人员通过ShareSDK快速完成共享特性的GUI工具。

# 步骤二:将ShareSDK导入项目

有两种方法可以将ShareSDK导入到您的项目中:**引用ShareSDK项目**或**将jar和资源复制到您的项目**中。如果您选择第二种方式，我们提供以下工具帮助您快速完成这些操作:

![quick integrating tool](http://a3.qpic.cn/psb?/V14GftmO22fJgW/qx8h1C30NL54RLlMd7R9BKh*yL4b37aHk3otu.*G1Sc!/b/dAsAAAAAAAAA&bo=EwOAAgAAAAAFALE!&rf=viewer_4)

执行此工具并将其产品复制到项目中。

ShareSDK鼓励您通过引用ShareSDK的项目来集成它，因为这样会简单得多。步骤如下:

(1) 将提取的SDK复制到Eclipse的工作区中

(2) 导入SDK项目:

![projects of ShareSDK](http://a3.qpic.cn/psb?/V14GftmO22fJgW/9tjZSx7IlbFqvbZB2d1Nh.Z9rEPHLjFUJGFpy89QPdU!/b/dCwAAAAAAAAA&bo=sAJMAgAAAAAFAN8!&rf=viewer_4)

选择 MainLibs 和 OnekeyShare

![select lib-projects](http://a3.qpic.cn/psb?/V14GftmO22fJgW/BwUzD2sMwuZCm8YVYRkTg9*1U6QrJsCgxxvhvTmNecE!/b/dE0AAAAAAAAA&bo=sAJOAgAAAAADANs!&rf=viewer_4)

(3) 将项目的依赖项更改为OnekeyShare(如果需要此GUI工具)或mainlib

![change dependency](http://a3.qpic.cn/psb?/V14GftmO22fJgW/WM8W63pq8nuXMARQOSW7FuvuqS3belOTaYngPE9Gn1A!/b/dE0AAAAAAAAA&bo=FgJXAgAAAAAFAGI!&rf=viewer_4)

# 第三步:添加应用程序信息

有三种方法可以将应用程序信息添加到ShareSDK中:在ShareSDK的应用程序控制台注册**，配置“assets/ShareSDK”。文件，或由ShareSDK修改。setPlatformDevInfo(String, HashMap<String, Object>)方法
下面是“assets/ShareSDK”的示例:
```
<ShareSDK
   AppKey="add appkey you got from ShareSDK here" />

<Facebook
    Id="int字段，自定义值，供开发人员识别此平台"
    SortId="int字段，注册平台中的优先级"
    ConsumerKey="从Facebook获得的Key"
    ConsumerSecret="你从Facebook获得的密钥"
    Enable="Boolean字段，false表示从注册平台中删除平台" />


所有应用程序信息都在“assets/ShareSDK”中注册。ShareSDK示例项目的xml。

# 第四步:配置AndroidManifest.xml

在AndroidMenifest.xml中添加以下权限:

<uses-permission android:name="android.permission.GET_TASKS" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.MANAGE_ACCOUNTS"/>
<uses-permission android:name="android.permission.GET_ACCOUNTS"/>

如果希望使用KakaoTalk共享msg，应该在launchActivity中添加intent过滤器。
<!--
	If you share msg in KakaoTalk, your share-params of executeUrl should set the value "kakaoTalkTest://starActivity"
	So it do, when the user to click the share-msg, then startActivity of your app's launch-activity. 
	When you use the lib of onekeyshare, you can use the method of 
    setExecuteUrl("kakaoTalkTest://starActivity") to set executeUrl.
-->
    <intent-filter>
        <data android:scheme="kakaoTalkTest" android:host="starActivity"/>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.BROWSABLE" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
		
ShareSDK的gui的单一Activity:

<activity
   android:name="cn.sharesdk.framework.ShareSDKUIShell"
   android:theme="@android:style/Theme.Translucent.NoTitleBar"
   android:configChanges="keyboardHidden|orientation|screenSize"
   android:screenOrientation="portrait"
   android:windowSoftInputMode="stateHidden|adjustResize" />


如果你集成了微信，添加这个回调Activity:

<activity
   android:name=".wxapi.WXEntryActivity"
   android:theme="@android:style/Theme.Translucent.NoTitleBar"
   android:configChanges="keyboardHidden|orientation|screenSize"
   android:exported="true"
   android:screenOrientation="portrait" />

如果你集成Yixin，添加这个回调Activity:

<activity
   android:name=".yxapi.YXEntryActivity"
   android:theme="@android:style/Theme.Translucent.NoTitleBar"
   android:configChanges="keyboardHidden|orientation|screenSize"
   android:exported="true"
   android:screenOrientation="portrait" />

第五步:添加代码

在**入口activity**的**onCreate**方法中添加如下行:

ShareSDK.initSDK(this);


并在**上次Activity**的**onDestroy**方法中添加如下行:

ShareSDK.stopSDK(this);
```

# 截图
![logo grid view of onekeyshare](http://a3.qpic.cn/psb?/V14GftmO22fJgW/1cUPaAxmqxnyzWXbeWEWkOVNFcxv7laksaKs*d7Aq4c!/b/dAsAAAAAAAAA&bo=UQFXAgAAAAAFACY!&rf=viewer_4)
![edit page of onekeyshare](http://a3.qpic.cn/psb?/V14GftmO22fJgW/.wTX0fqSedSyZW8VZSvhY3oZLVLAqrcAqLKHwkYigdc!/b/dAsAAAAAAAAA&bo=UQFXAgAAAAAFACY!&rf=viewer_4)
![image preview](http://a3.qpic.cn/psb?/V14GftmO22fJgW/a*51wOzdPkjZJP*GBvmREgDhZa*txPSj6FU6nuFKFi8!/b/dBQAAAAAAAAA&bo=UQFXAgAAAAAFACY!&rf=viewer_4)
![authorizes](http://a1.qpic.cn/psb?/V14GftmO22fJgW/WJQD2wK4WBpfTBdNR24vDf0taBOzBTqThEcxIRtdfk0!/b/dA8AAAAAAAAA&bo=UQFXAgAAAAAFACY!&rf=viewer_4)


有关如何整合ShareSDK或如何使用ShareSDK获取好友列表、关注某人、分享状态等更多信息，请访问我们的网站 [official wiki](http://wiki.sharesdk.cn/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97).

