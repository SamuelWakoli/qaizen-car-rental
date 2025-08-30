# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep Kotlin metadata, useful for reflection used by some libraries
-keep class kotlin.Metadata { *; }

# === Firestore Data Model Rules ===
# IMPORTANT: If your admin module has its own data models or uses models
# from a different package than the app module,
# update "com.qaizen.car_rental_qaizen.domain.model.**" below
# to the correct package name for your admin module's data models.
# If the admin module does not use Firestore with data models,
# or if its models are already covered by another module's Proguard rules
# (e.g., a shared library), you may not need all of the following rules.

# Keep all data model classes in your admin app's domain.model package,
# along with all their members (fields, methods, constructors).
# This is crucial for Firestore to instantiate and populate your objects.
-keep class com.qaizen.car_rental_qaizen.domain.model.** { *; } # FIXME: Update this package if different for admin models

# Keep class members (fields and methods) in any class that are annotated
# with Firestore's @PropertyName. This ensures custom-named fields are mapped correctly.
# This rule is general and usually fine.
-keepclassmembers class * {
    @com.google.firebase.firestore.PropertyName *;
}

# Additionally, explicitly keep members in your data model classes that are
# annotated with other common Firebase/Firestore annotations.
# FIXME: Update "com.qaizen.car_rental_qaizen.domain.model.**" if different for admin models
-keepclassmembers class com.qaizen.car_rental_qaizen.domain.model.** {
    @com.google.firebase.firestore.Exclude *;
    @com.google.firebase.firestore.ServerTimestamp *;
    @com.google.firebase.firestore.DocumentId *;
    @com.google.firebase.firestore.IgnoreExtraProperties *;
    # Add any other Firebase or custom annotations you use for your models here
}

# Ensure public no-argument constructors are kept for your data model classes.
# Firestore often requires these for deserializing data into objects.
# FIXME: Update "com.qaizen.car_rental_qaizen.domain.model.**" if different for admin models
-keepclassmembers class com.qaizen.car_rental_qaizen.domain.model.** {
    public <init>();
}

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
