plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // serialization
    kotlin("plugin.serialization") version "2.0.21"
    // hilt
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // serialization
    implementation(libs.kotlin.serialization)

    // kotlin-serialization-converter
    implementation(libs.kotlin.serialization.converter)

    // Hilt
    implementation(libs.hilt.android)
    ksp (libs.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit)
    // okHttp3
    implementation(libs.okhttp3)
}