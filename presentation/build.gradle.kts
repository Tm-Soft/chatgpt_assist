plugins {
    id("com.android.library")
    //id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")

    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "live.lafi.presentation"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":util"))

    implementation(Dep.AndroidX.core)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.material)
    implementation(Dep.AndroidX.constraintLayout)
    implementation(Dep.AndroidX.activity)
    implementation(Dep.AndroidX.fragment)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.junitExt)
    androidTestImplementation(Dep.Test.espressoCore)

    // Timber 사용을 위한 의존성 추가
    implementation(Dep.Timber.core)

    // Room 사용을 위한 의존성 추가
    implementation(Dep.Room.runtime)
    implementation(Dep.Room.ktx)
    kapt(Dep.Room.compiler)

    // DataStore 사용을 위한 의존성 추가
    //implementation(Dep.DataStore.core)
    implementation(Dep.DataStore.preferences)

    // ViewModel 사용을 위한 의존성 추가
    implementation(Dep.Lifecycle.viewModel)
    implementation(Dep.Lifecycle.livedata)

    // Hilt 사용을 위한 의존성 추가.
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")
    implementation(Dep.Hilt.hilt)
    kapt(Dep.Hilt.compiler)

    // retrofit2 & okhttp 사용을 위한 의존성 추가
    implementation(Dep.Square.retrofit)
    implementation(Dep.Square.converterGson)
    implementation(Dep.Square.converterScalars)
    implementation(Dep.Square.okhttp3)
    implementation(Dep.Square.loggingInterceptor)

    // Gilde 사용을 위한 의존성 추가
    implementation(Dep.Gilde.okhttp3)

    // 애드몹 사용을 위한 의존성 추가
    implementation(Dep.AdMob.ads)

    implementation(Dep.ExpandableLayout.expandableLayout)
}