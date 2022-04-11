plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        applicationId = Config.APPLICATION_ID
        minSdk = Config.MIN_SDK
        targetSdk = Config.TARGET_SDK
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildTypes.forEach {
        it.buildConfigField(
            "String",
            "DICTIONARY_BASE_URL",
            "\"https://dictionary.skyeng.ru/api/public/v1/\""
        )
    }

    compileOptions {
        sourceCompatibility = Config.JAVA_VERSION
        targetCompatibility = Config.JAVA_VERSION
    }

    kotlinOptions {
        jvmTarget = Config.KOTLIN_JVM_TARGET
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(Modules.CORE))

    implementation(Libraries.CORE_KTX)
    implementation(Libraries.APPCOMPAT)
    implementation(Libraries.MATERIAL)
    implementation(Libraries.CONSTRAINT_LAYOUT)

    // Lifecycle
    implementation(Libraries.LIFECYCLE_LIVEDATA_KTX)
    implementation(Libraries.LIFECYCLE_VIEW_MODEL_KTX)

    // RxJava
    implementation(Libraries.RXJAVA3_RXANDROID)
    implementation(Libraries.RXJAVA3)

    // OkHttp
    implementation(Libraries.OKHTTP3)
    implementation(Libraries.OKHTTP3_LOGGING)

    // Retrofit
    implementation(Libraries.RETROFIT2)
    implementation(Libraries.RETROFIT2_CONVERTER_GSON)
    implementation(Libraries.RETROFIT2_ADAPTER_RXJAVA3)

    // Glide
    implementation(Libraries.GLIDE)

    // Koin
    implementation(Libraries.KOIN)

    // Kotlin Coroutines
    implementation(Libraries.COROUTINES_CORE)
    implementation(Libraries.COROUTINES_ANDROID)

    //Room
    implementation(Libraries.ROOM_RUNTIME)
    kapt(Libraries.ROOM_COMPILER)
    implementation(Libraries.ROOM_KTX)

    // Navigation
    implementation(Libraries.NAVIGATION_FRAGMENT_KTX)
    implementation(Libraries.NAVIGATION_UI_KTX)

    testImplementation(Libraries.JUNIT)
    androidTestImplementation(Libraries.JUNIT_ANDROID)
    androidTestImplementation(Libraries.ESPRESSO)
}