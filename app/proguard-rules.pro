# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep Kotlin metadata, useful for reflection used by some libraries
-keep class kotlin.Metadata { *; }

# Keep all data model classes in the app's domain.model package,
# along with all their members (fields, methods, constructors).
# This is crucial for Firestore to instantiate and populate the objects.
-keep class com.qaizen.car_rental_qaizen.domain.model.** { *; }

# Keep class members (fields and methods) in any class that are annotated
# with Firestore's @PropertyName. This ensures custom-named fields are mapped correctly.
-keepclassmembers class * {
    @com.google.firebase.firestore.PropertyName *;
}

# Additionally, explicitly keep members in the data model classes that are
# annotated with other common Firebase/Firestore annotations.
-keepclassmembers class com.qaizen.car_rental_qaizen.domain.model.** {
    @com.google.firebase.firestore.Exclude *;
    @com.google.firebase.firestore.ServerTimestamp *;
    @com.google.firebase.firestore.DocumentId *;
    @com.google.firebase.firestore.IgnoreExtraProperties *;
    # Add any other Firebase or custom annotations used for the models here
}

# Ensure public no-argument constructors are kept for the data model classes.
# Firestore often requires these for deserializing data into objects.
# If the models are Kotlin data classes, they usually have a primary constructor,
# but Firestore might also use a no-arg constructor if available or if all
# properties have default values.
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
