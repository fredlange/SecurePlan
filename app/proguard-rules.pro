# SecurePlan ProGuard rules
-keepattributes *Annotation*
-keepclassmembers class * extends androidx.room.RoomDatabase { abstract *; }

# Keep Gson models (needed for JSON serialization of domain models)
-keepclassmembers class se.secureplan.app.core.domain.model.** { *; }

# Keep Hilt generated code
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep Room entities
-keep class se.secureplan.app.core.data.local.entity.** { *; }

# Apache POI — keep all classes needed for .xlsx generation
-keep class org.apache.poi.** { *; }
-keep class org.openxmlformats.** { *; }
-keep class com.microsoft.schemas.** { *; }
-dontwarn org.apache.poi.**
-dontwarn org.openxmlformats.**
-dontwarn com.microsoft.schemas.**
-dontwarn org.apache.xmlbeans.**
-dontwarn org.etsi.**
-dontwarn org.w3.**
-dontwarn com.graphbuilder.**
-dontwarn javax.xml.stream.**
-dontwarn org.osgi.**
-dontwarn aQute.bnd.**
-dontwarn org.apache.logging.**
-dontwarn org.slf4j.**

# Gson
-keepattributes Signature
-keepattributes EnclosingMethod
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory { *; }
-keep class * implements com.google.gson.JsonSerializer { *; }
-keep class * implements com.google.gson.JsonDeserializer { *; }

# Navigation Compose
-keepnames class androidx.navigation.** { *; }
