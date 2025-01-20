import io.gitlab.arturbosch.detekt.Detekt

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) 
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.detekt) 
    alias(libs.plugins.firebaseCrashlytics)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.evothings.mhand"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.evothings.mhand"
        minSdk = 26
        targetSdk = 34
        versionCode = 202409013
        versionName = "3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions.add("mobileServices")
    productFlavors {
        create("google") {
            dimension = "mobileServices"
            plugins.apply("com.google.gms.google-services")
        }

        create("huawei") {
            dimension = "mobileServices"
            applicationIdSuffix = ".huawei"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        // Uncomment to enable compose metrics collecting
//        freeCompilerArgs += listOf(
//            "-P",
//            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + project.buildDir.absolutePath + "/compose_metrics"
//        )
//        freeCompilerArgs += listOf(
//            "-P",
//            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + project.buildDir.absolutePath + "/compose_reports"
//        )
    }
    buildFeatures {
        compose = true
        android.buildFeatures.buildConfig = true
    }
    detekt {
        toolVersion = "1.23.3"
        config.from("config/detekt.yaml")
    }
    tasks.withType<Detekt>().forEach {
        it.jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":widget"))
    implementation(project(":domain"))

    implementation(kotlin("reflect"))

    implementation(platform(libs.compose.bom))
    
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.immutableCollections)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.lifecycle.viewModel)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime.liveData)
    implementation(libs.compose.material3)
    implementation(libs.compose.material)

    implementation(libs.accompanist.permissions)

    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    implementation(libs.androidx.paging.compose)

    implementation(libs.compose.navigation)
    implementation(libs.compose.navigation.hilt)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)

    implementation(libs.hcaptcha)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.lifecycle.viewmodel.android)

    "googleImplementation"(libs.play.services)
    "googleImplementation"(libs.play.update)
    "googleImplementation"(libs.play.update.ktx)
    "googleImplementation"(libs.play.installref)
    "googleImplementation"(libs.play.adsIndentifier)

    "huaweiImplementation"(libs.huawei.connectCore)
    "huaweiImplementation"(libs.huawei.services)

    implementation(libs.mytracker)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.osmdroid)

    implementation(libs.androidx.dataStore)
    implementation(libs.play.services.location)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


}
