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
-keep class com.example.qoutecalculator.Model.* { *; }
#------------------------------------------------------------------------------------
#Firebase
-dontnote com.google.firebase.**
-dontwarn com.google.firebase.crash.**
#------------------------------------------------------------------------------------
#Firebase Realtime Database
-keepattributes Signature
-keepclassmembers class com.example.qoutecalculator.models.** {
      *;
    }
#------------------------------------------------------------------------------------
#------------------------------------------------------------------------------------
#Firebase Authentication
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses
#------------------------------------------------------------------------------------
#------------------------------------------------------------------------------------
# RxJava 2
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}
#------------------------------------------------------------------------------------
#------------------------------------------------------------------------------------
# Google Play Services
-keep class com.google.android.gms.* {  *; }
-dontwarn com.google.android.gms.internal.measurement.**
-dontwarn com.google.android.gms.internal.**
-dontwarn com.google.android.gms.ads.**
#------------------------------------------------------------------------------------