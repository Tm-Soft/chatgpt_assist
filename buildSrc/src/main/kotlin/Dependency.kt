object Version {
    const val compileSdk = 34

    const val minSdk = 24
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Dep {
    const val version = "1.8.0"
    const val coroutineVersion = "1.5.1"

    const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
    const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.2.1"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2"
    const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.10.0"
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"

        const val activity = "androidx.activity:activity-ktx:1.7.1"
        const val fragment = "androidx.fragment:fragment-ktx:1.5.7"
        const val material = "com.google.android.material:material:1.9.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    }
    object Lifecycle {
        const val version = "2.6.1"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
    }
    object Hilt {
        const val version = "2.48"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
    }
    object Square {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val converterScalars = "com.squareup.retrofit2:converter-scalars:2.6.4"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.4.0"
        const val okhttp3 = "com.squareup.okhttp3:okhttp:4.4.0"
    }
    object Gilde {
        const val core = "com.github.bumptech.glide:glide:4.12.0"
        const val compiler = "com.github.bumptech.glide:compiler:4.12.0"
        const val okhttp3 = "com.github.bumptech.glide:okhttp3-integration:4.12.0"
    }
    object Timber {
        const val core = "com.jakewharton.timber:timber:4.7.1"
    }
    object Room {
        const val version = "2.5.1"
        const val runtime = "androidx.room:room-runtime:$version"
        const val compiler = "androidx.room:room-compiler:$version"
        const val ktx = "androidx.room:room-ktx:$version"
    }
    object DataStore {
        const val version = "1.0.0"
        const val core = "androidx.datastore:datastore-preferences-core:$version"
        const val preferences = "androidx.datastore:datastore-preferences:$version"
    }
    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.5"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
    }
    object AdMob {
        const val ads = "com.google.android.gms:play-services-ads:22.4.0"
    }
    object Gpt {
        const val jtokkit = "com.knuddels:jtokkit:0.6.1"
    }
    // https://github.com/skydoves/ExpandableLayout
    object ExpandableLayout {
        const val expandableLayout = "com.github.skydoves:expandablelayout:1.0.7"
    }
    object Lottie {
        const val version = "3.4.0"
        const val lottie = "com.airbnb.android:lottie:$version"
    }
}