import org.gradle.api.JavaVersion

object Config {
    const val APPLICATION_ID = "com.rino.translator"
    const val COMPILE_SDK = 31
    const val MIN_SDK = 28
    const val TARGET_SDK = 31
    const val KOTLIN_JVM_TARGET = "1.8"
    val JAVA_VERSION = JavaVersion.VERSION_1_8

    const val VERSION_CODE = 28
    const val VERSION_NAME = "1.5.2"
}

object Modules {
    const val CORE = ":core"
    const val REMOTE = ":remote"
    const val DATABASE = ":database"
}

object BuildTypes {
    const val RELEASE = "release"
}

object Versions {
    const val CORE_KTX = "1.7.0"
    const val APPCOMPAT = "1.4.1"
    const val MATERIAL = "1.5.0"
    const val CONSTRAINT_LAYOUT = "2.1.3"
    const val LIFECYCLE_LIVEDATA_KTX = "2.4.1"
    const val LIFECYCLE_VIEW_MODEL_KTX = "2.4.1"
    const val RXJAVA3_RXANDROID = "3.0.0"
    const val RXJAVA3 = "3.0.0"
    const val OKHTTP3 = "4.9.1"
    const val OKHTTP3_LOGGING = "4.9.1"
    const val RETROFIT2 = "2.9.0"
    const val GLIDE = "4.12.0"
    const val KOIN = "3.1.2"
    const val COROUTINES = "1.5.2"
    const val ROOM = "2.4.2"
    const val NAVIGATION = "2.4.2"
    const val JUNIT = "4.13.2"
    const val JUNIT_ANDROID = "1.1.3"
    const val ESPRESSO = "3.4.0"
}

object Libraries {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

    // Lifecycle
    const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_LIVEDATA_KTX}"
    const val LIFECYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VIEW_MODEL_KTX}"

    // RxJava
    const val RXJAVA3_RXANDROID = "io.reactivex.rxjava3:rxandroid:${Versions.RXJAVA3_RXANDROID}"
    const val RXJAVA3 = "io.reactivex.rxjava3:rxjava:${Versions.RXJAVA3}"

    // OkHttp
    const val OKHTTP3 = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP3}"
    const val OKHTTP3_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3_LOGGING}"

    // Retrofit
    const val RETROFIT2 = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT2}"
    const val RETROFIT2_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT2}"
    const val RETROFIT2_ADAPTER_RXJAVA3 = "com.squareup.retrofit2:adapter-rxjava3:${Versions.RETROFIT2}"

    // Glide
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"

    // Koin
    const val KOIN = "io.insert-koin:koin-android:${Versions.KOIN}"

    // Kotlin Coroutines
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"

    // Room
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"

    // Navigation
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val JUNIT_ANDROID = "androidx.test.ext:junit:${Versions.JUNIT_ANDROID}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
}
