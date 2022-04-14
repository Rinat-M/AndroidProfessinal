plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Config.JAVA_VERSION
    targetCompatibility = Config.JAVA_VERSION
}

dependencies {
    implementation(project(Modules.CORE))

    // OkHttp
    implementation(Libraries.OKHTTP3)
    implementation(Libraries.OKHTTP3_LOGGING)

    // RxJava
    implementation(Libraries.RXJAVA3)

    // Retrofit
    implementation(Libraries.RETROFIT2)
    implementation(Libraries.RETROFIT2_CONVERTER_GSON)
    implementation(Libraries.RETROFIT2_ADAPTER_RXJAVA3)

}