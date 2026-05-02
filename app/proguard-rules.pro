# SecurePlan ProGuard rules
-keepattributes *Annotation*
-keepclassmembers class * extends androidx.room.RoomDatabase { abstract *; }
# Keep Gson models
-keepclassmembers class se.secureplan.app.core.domain.model.** { *; }
# Keep Hilt generated code
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
# Keep Room entities
-keep class se.secureplan.app.core.data.local.entity.** { *; }
