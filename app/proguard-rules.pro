# Stack traces — оставляем номера строк, имя файла прячем
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Hilt / Dagger
-keep class * extends dagger.hilt.android.internal.managers.* { *; }
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.HiltAndroidApp
-keep @dagger.hilt.android.AndroidEntryPoint class *

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Yandex Mobile Ads
-keep class com.yandex.mobile.ads.** { *; }
-keep class com.yandex.metrica.** { *; }
-dontwarn com.yandex.mobile.ads.**
-dontwarn com.yandex.metrica.**

# Kotlin metadata + reflection (на случай если рефлекшн где-то используется)
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations,AnnotationDefault
-keep class kotlin.Metadata { *; }

# Compose рантайм — обычно не нужно править, но на всякий
-dontwarn androidx.compose.**
