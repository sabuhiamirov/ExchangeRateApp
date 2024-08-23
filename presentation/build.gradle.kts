plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id(libs.plugins.daggerHiltAndroid.get().pluginId)
}

android {
    namespace = "com.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 28
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.livedata)
    implementation(libs.lifecycle.viewmodel.livedata.core)
    implementation(libs.accompanist.flowlayout)

    // ----- Navigation Component ----- //
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // ----- Retrofit Dependency ----- //
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2.adapter.rxjava2)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)


    // ----- Timber Dependency ----- //
    implementation(libs.timber)


    // ----- Coroutines Dependency ----- //
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // ----- Room Dependency ----- //
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    androidTestImplementation(libs.room.testing)

    // ----- Dagger - Hilt Dependency ----- //
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    implementation(libs.work.runtime.ktx)

    //Ted permission
    implementation(libs.ted.permission)
}