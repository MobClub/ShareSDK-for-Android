# ShareSDK 集成文档
注：使用ShareSDK Gradle集成方式，不需要在AndroidMainfest.xml下面配置任何权限和Activity

注意：需要先申请Mob的appkey与appsecret,[请点击这里查看申请步骤](http://bbs.mob.com/forum.php?mod=viewthread&tid=8212&extra=page%3D1)

文档语言 : 中文 | [English](https://github.com/MobClub/ShareSDK-for-Android/blob/master/README_EN.md)

- ShareSDK是全球最流行的应用和手机游戏社交SDK !到目前为止，我们已经支持了几十万名客户。
ShareSDK可以轻松支持世界上40多个社交平台的第三方登录、分享和与好友列表操作。短短几个小时，这个小程序包将使您的应用程序完全社会化!
想在中国社交平台上发布你的应用吗?这可能是你最好的选择!

- website -- http://www.mob.com
- wiki --  http://wiki.mob.com/sdk-share-android-3-0-0/
- bbs --   http://bbs.mob.com/forum.php


# 一、配置gradle
1、打开项目根目录的build.gradle，在buildscrip–>dependencies 模块下面添加  classpath ‘com.mob.sdk:MobSDK:2018.0319.1724’，如下所示；

    buildscript {
    repositories {
         jcenter() 
    }

    dependencies {
        ...
        classpath "com.mob.sdk:MobSDK:2018.0319.1724"

    }
	}

![directory structure](http://wiki.mob.com/wp-content/uploads/2017/05/QQ%E6%88%AA%E5%9B%BE20181016175240.png)

2、在使用到Mob产品的module下面的build.gradle文件里面添加引用

    apply plugin: 'com.mob.sdk'

![](http://wiki.mob.com/wp-content/uploads/2017/11/2.jpg)

3、然后添加MobSDK方法，配置mob的key和秘钥 （与第2步是一个gradle中；注意：MobSDK方法是配置到文件根目录，与android并列，不要配置到android里面哦）

如果还没有key的，申请Mob的appkey与appsecret[请点击这里查看](http://bbs.mob.com/forum.php?mod=viewthread&tid=8212&extra=page%3D1)

Onekeyshare是ShareSDK的GUI界面，如果不需要，则需要添加”gui false”，因为默认是使用gui，version字段为SDK的版本号，不设置则使用最新的版本；

    MobSDK {
    appKey "d580ad56b4b5"
    appSecret "7fcae59a62342e7e2759e9e397c82bdd"

    ShareSDK {}
   
	}
4、Gradle集成方式可以在Mob产品的module下面的build.gradle文件里面配置ShareSDK各个社交平台的key信息
    MobSDK {
    appKey "d580ad56b4b5"
    appSecret "7fcae59a62342e7e2759e9e397c82bdd"

    ShareSDK {
        //平台配置信息
        devInfo {
            SinaWeibo {
                appKey "568898243"
                appSecret "38a4f8204cc784f81f9f0daaf31e02e3"
                callbackUri "http://www.sharesdk.cn"
                shareByAppClient false
            }
            Wechat {
                appId "wx4868b35061f87885"
                appSecret "64020361b8ec4c99936c0e3999a9f249"
            }
            QQ {
                appId "100371282"
                appKey "aed9b0303e3ed1e27bae87c33761161d"
                shareByAppClient true
            }
            Facebook {
                appKey "1412473428822331"
                appSecret "a42f4f3f867dc947b9ed6020c2e93558"
                callbackUri "https://mob.com"
            }
        }
    }
	}

其中的devInfo为来自社交平台的应用信息。

注：如果您没有在AndroidManifest中设置appliaction的类名，MobSDK会将这个设置为com.mob.MobApplication，但如果您设置了，请在您自己的Application类中调用：
    MobSDK.init(this);
并且在Manifest清单文件中配置：tools:replace=”android:name”，如下所示:

    <application
		android:name = ".MyApplication"
		tools:replace="android:name">

# 二、配置字段说明

配置支持的平台全部属性字段有以下这些（注：区分大小写）


|字段|说明|
|:---|:----|
|id	|数字，平台的id，可以设置为任何不重复的数字,(可选字段)|
|sortId	|数字，九宫格界面平台的排序，越小越靠前，可以设置为任何不重复的数字,(可选字段)|
|appId	|文本，对应ShareSDK.xml中的AppId、ClientID、ApplicationId、ChannelID|
|appKey	|文本，对应ShareSDK.xml中的AppKey、ConsumerKey、ApiKey、OAuthConsumerKey|
|appSecret	|文本，对应ShareSDK.xml中的AppSecret、ConsumerSecret、SecretKey、Secret、ClientSecret、ApiSecret、ChannelSecret|
|callbackUri	|文本，对应ShareSDK.xml中的RedirectUrl、RedirectUri、CallbackUrl|
|shareByAppClient	|布尔值，shareByAppClient标识是否使用客户端分享|
|bypassApproval	|布尔值,bypassApproval表示是否绕过审核|
|enable	|布尔值，enable字段表示此平台是否启用|
|userName	|文本，userName在微信小程序中使用|
|path	|文本，path在微信小程序中使用|
|hostType	|文本，表示服务器类型，在YouDao和Evernote中使用|
|withShareTicket	|布尔值，分享微信小程序时，分享出去的小程序被二次打开时可以获取到更多信息，true为能获取，反之不能；仅在微信中使用|
|miniprogramType	|数字，分享微信小程序时，表示小程序的开发状态，取值范围：0-正式，1-开发，2-体验，仅在微信中使用|

配置完以上信息后同步（sync）一下代码就可以调用相关的接口了

# 三、添加代码
1、初始化MobSDK

如果您没有在AndroidManifest中设置appliaction的类名，MobSDK会将这个设置为com.mob.MobApplication，但如果您设置了，请在您自己的Application类中调用：

    MobSDK.init(this);

以初始化MobSDK。

添加配置后，即可调用授权、获取资料、分享等操作，如一键分享的代码：
    private void showShare() {
     OnekeyShare oks = new OnekeyShare();
     //关闭sso授权
     oks.disableSSOWhenAuthorize(); 

     // title标题，微信、QQ和QQ空间等平台使用
     oks.setTitle(getString(R.string.share));
     // titleUrl QQ和QQ空间跳转链接
     oks.setTitleUrl("http://sharesdk.cn");
     // text是分享文本，所有平台都需要这个字段
     oks.setText("我是分享文本");
     // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
     oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
     // url在微信、微博，Facebook等平台中使用
     oks.setUrl("http://sharesdk.cn");
     // comment是我对这条分享的评论，仅在人人网使用
     oks.setComment("我是测试评论文本");
    // 启动分享GUI
    oks.show(this);
	}
	}

# 四、混淆设置
ShareSDK已经做了混淆处理，再次混淆会导致不可预期的错误，请在您的混淆脚本中添加如下的配置，跳过对ShareSDK的混淆操作：

    -keep class cn.sharesdk.**{*;}
	-keep class com.sina.**{*;}
	-keep class **.R$* {*;}
	-keep class **.R{*;}
	-keep class com.mob.**{*;}
	-keep class m.framework.**{*;}
	-dontwarn cn.sharesdk.**
	-dontwarn com.sina.**
	-dontwarn com.mob.**
	-dontwarn **.R$*

# 五、可用的社交平台

任何配置在devInfo下的社交平台都是可用的，他们包括：

|分类	|平台名称|
|:-------|:------|
|常用平台	|SinaWeibo（新浪微博）、Wechat（微信好友）、WechatMoments（微信朋友圈）、QQ（QQ好友）、Facebook、FacebookMessenger|
|其它主流平台	|TencentWeibo（腾讯微博）、QZone（QQ空间）、Renren（人人网）、Twitter、Douban（豆瓣）、Tumblr、GooglePlus（Google+）、Pinterest、Line、Instagram、Alipay（支付宝好友）、AlipayMoments（支付宝朋友动态）、Youtube、Meipai（美拍）|
|其它平台	|WechatFavorite（微信收藏）、KaiXin（开心网）、Email（电子邮件）、ShortMessage（短信）、YouDao（有道云笔记）、Evernote（印象笔记）、LinkedIn（领英）、FourSquare、Flickr、Dropbox、VKontakte、Yixin（易信）、YixinMoments（易信朋友圈）、Mingdao（明道）、KakaoTalk、KakaoStory、WhatsApp、Pocket、Instapaper、Dingding（钉钉）、Telegram|

# 六、注意事项
1. ShareSDK默认会添加OnekeyShare库，如果您不需要这个库，可以在ShareSDK下设置“gui false”来关闭OnekeyShare
2. MobSDK默认为ShareSDK提供最新版本的集成，如果您想锁定某个版本，可以在ShareSDK下设置“version “某个版本””来固定使用这个版本
3. 如果使用MobSDK的模块会被其它模块依赖，请确保依赖它的模块也引入MobSDK插件

其他集成详细文档可以参考：http://wiki.mob.com/%E5%AE%8C%E6%95%B4%E9%9B%86%E6%88%90%E6%96%87%E6%A1%A3%EF%BC%88gradle%EF%BC%89/#map-5



