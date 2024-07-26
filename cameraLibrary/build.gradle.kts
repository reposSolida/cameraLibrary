import io.grpc.internal.SharedResourceHolder.release

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    id   ("kotlin-kapt")
    `maven-publish`
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
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
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Service Dependencies
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation (libs.interceptor)

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
    implementation (libs.glide)
    kapt (libs.glide.compiler)

    // CrashLYtics
    implementation ("com.google.firebase:firebase-crashlytics:19.0.3")
    implementation ("com.google.firebase:firebase-analytics:22.0.2")

    // Timber
    implementation ("com.jakewharton.timber:timber:5.0.1")

    // Fragments
    implementation ("androidx.fragment:fragment-ktx:1.8.1")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.github.reposSolida"
                artifactId = "cameraLibrary"
                version = "1.0.6"

                from(components["release"])
            }
        }
    }
}
