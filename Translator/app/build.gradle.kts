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

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(project(Modules.CORE))
    implementation(project(Modules.REMOTE))
    implementation(project(Modules.DATABASE))

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

    // Retrofit
    implementation(Libraries.RETROFIT2)

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

    // Splash screen
    implementation(Libraries.CORE_SPLASH_SCREEN)

    testImplementation(Libraries.JUNIT)
    androidTestImplementation(Libraries.JUNIT_ANDROID)
    androidTestImplementation(Libraries.ESPRESSO)
    testImplementation(Libraries.CORE_TESTING)
    testImplementation(Libraries.MOCKITO_CORE)
    testImplementation(Libraries.MOCKITO_INLINE)
    testImplementation(Libraries.TEST_RUNNER_ANDROID)
    testImplementation(Libraries.TEST_EXT_TRUTH_ANDROID)
    testImplementation(Libraries.TEST_EXT_ANDROID)
    testImplementation(Libraries.ROBOLECTRIC)
}