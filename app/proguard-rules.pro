# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-keep class com.example.editphotovideo.library.removebackgr.** { *; }
#-keepclassmembers class kotlinx.coroutines.flow.** { *; }
#-keepclassmembers class kotlinx.coroutines.** { *; }
#-keep class kotlinx.coroutines.flow.** { *; }
# Giữ các class và method cần thiết cho coroutine Flow (nếu bạn vẫn sử dụng Flow)
# Giữ tất cả class liên quan đến coroutines để coroutine hoạt động đúng
# Giữ nguyên class/method native JNI
# Giữ toàn bộ thư viện RemoveBg
-keep class org.pytorch.** { *; }
-keep class org.pytorch.torchvision.** { *; }
-keep class com.facebook.jni.** { *; }
-keepclassmembers class * {
  native <methods>;
}


