import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("androidx.navigation.safeargs.kotlin")
    id ("com.google.dagger.hilt.android")
    id ("com.google.devtools.ksp")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.ai.creavision"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ai.creavision"
        minSdk = 23
        targetSdk = 34
        versionCode = 21
        versionName = "3.1"


        var properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String","REPLICATE_API_KEY",  "\"${properties.getProperty("REPLICATE_API_KEY")}\"")
        buildConfigField("String","REWARDED_AD_EXAMPLE",  "\"${properties.getProperty("REWARDED_AD_EXAMPLE")}\"")
        buildConfigField("String","REWARDED_AD",  "\"${properties.getProperty("REWARDED_AD")}\"")
        buildConfigField("String","ADAPTY_API_KEY",  "\"${properties.getProperty("ADAPTY_API_KEY")}\"")


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.runtime.android)
    implementation(libs.play.services.analytics.impl)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //Navigation
    implementation ("androidx.navigation:navigation-runtime-ktx:2.7.4")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.4")


    //Hilt
    implementation ("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")
    ksp("androidx.hilt:hilt-compiler:1.0.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.airbnb.android:lottie:3.4.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    implementation ("com.google.android.gms:play-services-ads:23.1.0")


    implementation("com.android.billingclient:billing:7.0.0")

    implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.0")

    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-analytics")
    //implementation("com.github.akshaaatt:Google-IAP:1.6.0")

    implementation("io.adapty:android-sdk:2.11.2")
    implementation("io.adapty:android-ui:2.11.0")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    //Room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    implementation("androidx.core:core-splashscreen:1.0.0")

    implementation("com.google.android.play:review-ktx:2.0.1")








}

