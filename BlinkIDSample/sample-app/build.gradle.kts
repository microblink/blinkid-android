plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.microblink.blinkid.sample"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.microblink.blinkid.sample"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
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
    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(project(":lib-common"))
    implementation(libs.blinkid.ux)
    // use following set of dependencies if you want to use blinkid-ux library module
    // instead of maven dependency, and remove implementation(libs.blinkid.ux) dependency
//     implementation(project(":blinkid-ux"))
}