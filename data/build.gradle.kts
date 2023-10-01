plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "live.lafi.data"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk

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

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.junitExt)
    androidTestImplementation(Dep.Test.espressoCore)

    // Timber 사용을 위한 의존성 추가
    implementation(Dep.Timber.core)

    // Room 사용을 위한 의존성 추가
    implementation(Dep.Room.runtime)
    implementation(Dep.Room.ktx)
    kapt(Dep.Room.compiler)

    // retrofit2 & okhttp 사용을 위한 의존성 추가
    implementation(Dep.Square.retrofit)
    implementation(Dep.Square.converterGson)
    implementation(Dep.Square.converterScalars)
    implementation(Dep.Square.okhttp3)
    implementation(Dep.Square.loggingInterceptor)
}