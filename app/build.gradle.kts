
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.cameralibrary"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cameralibrary"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding{
        viewBinding{
            enable = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("androidx.fragment:fragment-ktx:1.3.6")

    implementation (project(":cameraLibrary"))

    implementation ("com.github.livefront:bridge:v2.0.2")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation ("com.google.android.material:material:1.8.0")

    implementation ("com.github.livefront:bridge:v2.0.2")
    kapt ("com.evernote:android-state-processor:1.4.1")
    implementation ("com.evernote:android-state:1.4.1")


}