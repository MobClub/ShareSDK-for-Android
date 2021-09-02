# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\as_dev_tools\android/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}

-keep class com.mob.**{*;}
-dontwarn com.mob.**

#fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*

-keep class com.bytedance.**{*;}
-keep class com.tencent.wework.api.** {*;}











-dontoptimize
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-dontwarn
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes SourceFile,LineNumberTable,Exceptions,InnerClasses,EnclosingMethod,Signature,*Annotation*
-keepparameternames

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.View
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends cn.sharesdk.framework.Platform
-keep public class * extends cn.sharesdk.framework.Service
-keep public interface * extends cn.sharesdk.framework.PlatformActionListener

-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class cn.sharesdk.framework.ShareSDK
-keep class cn.sharesdk.framework.utils.ShareSDKR
-keep class cn.sharesdk.framework.Platform
-keep class cn.sharesdk.framework.Platform$ShareParams
-keep class cn.sharesdk.framework.InnerShareParams
-keep class cn.sharesdk.framework.Service
-keep class cn.sharesdk.framework.Service$ServiceEvent
-keep class eclipse.local.sdk.**{*;}
-keep class com.sina.**{*;}
-keep class com.mob.**{*;}
-keep class com.alipay.share.sdk.**{*;}
-keep class com.mob.logcollector.db.MessageModel

#======================================  loopshare  ================================================
#-keep class cn.sharesdk.loopshare.MobLink
#-keep class cn.sharesdk.loopshare.Scene

-keep class cn.sharesdk.framework.utils.SSDKLog
-keep class cn.sharesdk.loopshare.beans.ConfigData
-keep class cn.sharesdk.loopshare.beans.LinkData
-keep class cn.sharesdk.loopshare.beans.LogData
-keep class cn.sharesdk.loopshare.beans.ServerData

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.View
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends com.mob.tools.proguard.ClassKeeper

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class **.R {
    public *;
}

-keep class **.R$* {
    *;
}

-keep interface * {
    *;
}

-keep class * extends java.lang.Throwable {
	*;
}

-keep class * extends com.mob.tools.proguard.PublicMemberKeeper {
	public *;
}

-keep class * extends com.mob.tools.proguard.ProtectedMemberKeeper {
	protected *;
}

-keepclassmembers class * extends com.mob.tools.proguard.PrivateMemberKeeper {
	private *;
}

-keepclassmembers class * extends com.mob.tools.proguard.ConstructorKeeper {
   public <init>(...);
}

-keep class * extends com.mob.tools.proguard.EverythingKeeper {
	*;
}
#=================================  loopshare  ======================================================

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * extends cn.sharesdk.framework.Platform {
    public static <fields>;
    public <init>(android.content.Context);
    public *;
	protected *;
}

-keepclassmembers class * extends cn.sharesdk.framework.Service {
    public *;
}

-keep class **$ShareParams {
    *;
}

-keep class cn.sharesdk.framework.Service {
    public static <fields>;
    public <init>(android.content.Context);
    public *;
    protected *;
}

-keep class **$ServiceEvent {
    *;
}

-keep class cn.sharesdk.framework.FakeActivity {
    *;
}

-keep class **.R {
    public *;
}

-keep class cn.sharesdk.framework.utils.BitmapHelper {
    public *;
}

-keep class **.R$* {
    *;
}

-keep class cn.sharesdk.framework.utils.ShareSDKR {
    public *;
}

-keep class cn.sharesdk.framework.utils.UIHandler {
    *;
}

-keep class cn.sharesdk.framework.TitleLayout {
	*;
}

-keep class cn.sharesdk.framework.PlatformDb {
	*;
}

-keepclasseswithmembers class * {
    *** getHTML(java.lang.String);
}

-keep interface * {
    *;
}

-keep class * extends java.lang.Throwable {
	*;
}

-keep class cn.sharesdk.framework.authorize.AuthorizeAdapter {
	*;
}

-keep class cn.sharesdk.tencent.qzone.QZoneWebShareAdapter {
	*;
}

-keep class cn.sharesdk.tencent.qq.QQWebShareAdapter {
	*;
}

-keep class cn.sharesdk.tencent.qq.ReceiveActivity {
	*;
}

-keep class cn.sharesdk.incentive.IncentivePageAdapter {
	*;
}

-keep class cn.sharesdk.incentive.OnRewardListener {
	*;
}

-keep class * implements cn.sharesdk.wechat.utils.WXMediaMessage$IMediaObject {
	*;
}

-keep class cn.sharesdk.wechat.utils.WXMediaMessage {
	public *;
}

-keep class * extends cn.sharesdk.wechat.utils.WXMediaMessage$IMediaObject {
	public *;
}

-keep class cn.sharesdk.wechat.utils.WechatHandlerActivity {
	public *;
}

-keep class cn.sharesdk.framework.ShareSDK {
    public static *;
}

-keep class cn.sharesdk.yixin.utils.YXMessage {
	public *;
}

-keep class * extends cn.sharesdk.yixin.utils.YXMessage$YXMessageData {
	public *;
}

-keep class * implements cn.sharesdk.yixin.utils.YXMessage$YXMessageData {
	*;
}

-keep class cn.sharesdk.yixin.utils.YXMessage$MessageType {
	*;
}

-keep class cn.sharesdk.yixin.utils.YixinHandlerActivity {
	public *;
}

-keep class cn.sharesdk.kakao.utils.KakaoWebViewClient {
	public *;
}

-keep class * implements cn.sharesdk.dingding.utils.DDMediaMessage$IMediaObject {
	*;
}

-keep class cn.sharesdk.dingding.utils.DDMediaMessage {
	public *;
}

-keep class * extends cn.sharesdk.dingding.utils.DDMediaMessage$IMediaObject {
	public *;
}

-keep class cn.sharesdk.dingding.utils.DingdingHandlerActivity {
	public *;
}

-keep class * extends cn.sharesdk.meipai.entity.MeipaiBaseObject {
	*;
}

-keep class cn.sharesdk.meipai.entity.MeipaiBaseObject {
	public *;
}

-keep class cn.sharesdk.framework.ReflectablePlatformActionListener {
	public *;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keep class cn.sharesdk.facebook.FBWebShareAdapter {
    *;
}

-keep class cn.sharesdk.twitter.MappedFileReader{
    *;
}
-keep class cn.sharesdk.twitter.UpLoadViewCallBack{
    *;
}
-keep class cn.sharesdk.google.WebShareActivity{
    *;
}
-keep class cn.sharesdk.google.GooglePlusAuthorizeWebviewClient{
    *;
}
-keep class cn.sharesdk.google.GooglePlusWebShareAdapter{
    *;
}
-keep class cn.sharesdk.youtube.YoutubeAuthorizeWebviewClient{
    *;
}
-keep class cn.sharesdk.facebookmessenger.ReceiveActivity{
    *;
}
-keep class cn.sharesdk.kakao.talk.ReceiveActivity{
    *;
}
