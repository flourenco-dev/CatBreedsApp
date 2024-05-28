plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jmailen.kotlinter")
}

android {
    namespace = "com.fabiolourenco.uifeaturepaginatedbreeds"
    compileSdk = rootProject.ext["compileSdkVersion"] as Int
    defaultConfig {
        minSdk = property("minSdkVersion") as Int
        testInstrumentationRunner = "com.fabiolourenco.catbreedsapp.CatBreedsAppTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "../proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["composeCompilerVersion"] as String
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":core"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.ext["kotlinVersion"]}")
    implementation("androidx.core:core-ktx:${rootProject.ext["coreKtxVersion"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.ext["appCompatVersion"]}")

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.ext["lifecycleVersion"]}")

    // Jetpack Compose
    implementation("androidx.activity:activity-compose:${rootProject.ext["activityComposeVersion"]}")
    implementation("androidx.compose.ui:ui:${rootProject.ext["composeVersion"]}")
    implementation("androidx.compose.material3:material3:${rootProject.extra["material3Version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.ext["composeVersion"]}")
    implementation("androidx.constraintlayout:constraintlayout-compose:${rootProject.ext["constraintLayoutVersion"]}")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:${rootProject.ext["hiltVersion"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.ext["hiltVersion"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.ext["hiltNavigationComposeVersion"]}")

    // Image Loading
    implementation("io.coil-kt:coil-compose:${rootProject.ext["koilVersion"]}")

    // Logging
    implementation("com.jakewharton.timber:timber:${rootProject.ext["timberVersion"]}")

    // Navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.ext["navigationVersion"]}")

    // Paging
    implementation("androidx.paging:paging-compose:${rootProject.ext["pagingVersion"]}")
    implementation("androidx.paging:paging-runtime-ktx:${rootProject.ext["pagingVersion"]}")

    // Swipe to Refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:${rootProject.ext["swipeRefreshVersion"]}")
}