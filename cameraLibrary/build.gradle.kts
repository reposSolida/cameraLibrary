plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.mylibrary"
    compileSdk = 34

    buildFeatures.buildConfig = true

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // Falta agregar canarias
        buildConfigField ("String", "BASE_URL_PRODUCTION", "\"https://api.solidatec.com/sd_16/servlet/\"")
        buildConfigField ("String", "BASE_URL_PREPRODUCTION", "\"https://preproduccion.solidatec.com/sd_16/servlet/\"")
        buildConfigField ("String", "BASE_URL_VALIDATION", "\"https://validacion.solidatec.com/sd_validacion_16/servlet/\"")
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Service Dependencies
    implementation(libs.gson)
    implementation(libs.okhttp)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    implementation(libs.androidx.room.common)

    // LottieAnimation
    implementation (libs.lottie)

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.13.2")
    kapt ("com.github.bumptech.glide:compiler:4.13.2")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}