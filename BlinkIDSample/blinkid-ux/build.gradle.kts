plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.microblink.blinkid.ux"
    compileSdk = 36

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

    sourceSets {
        getByName("main") {
            java.srcDirs("../../libs/sources/blinkid-ux/src/main/java")
            res.srcDirs("../../libs/sources/blinkid-ux/src/main/res")
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
        // fix for: R8 fails with missing class java.lang.StringConcatFactory and unable to
        // override because not accessible - https://issuetracker.google.com/issues/250197571
        freeCompilerArgs.add("-Xstring-concat=inline")
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    api(libs.blinkid.core)
}