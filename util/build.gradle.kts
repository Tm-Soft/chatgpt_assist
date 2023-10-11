plugins {
    id("com.android.library")
    //id("com.google.dagger.hilt.android")

    kotlin("android")
    kotlin("kapt")

    id("kotlin-parcelize")
}

android {
    namespace = "live.lafi.util"
    compileSdk = 33

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
    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(Dep.AndroidX.core)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.material)
    implementation(Dep.AndroidX.constraintLayout)
    implementation(Dep.AndroidX.activity)
    implementation(Dep.AndroidX.fragment)

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.junitExt)
    androidTestImplementation(Dep.Test.espressoCore)

    // Timber 사용을 위한 의존성 추가
    implementation(Dep.Timber.core)

    // Gpt Token 사용을 위한 의존성 추가
    implementation(Dep.Gpt.jtokkit)

    // Hilt 사용을 위한 의존성 추가.
//    implementation(Dep.Hlit.hilt)
//    kapt(Dep.Hlit.compiler)
}