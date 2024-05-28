plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jmailen.kotlinter")
}

android {
    namespace = "com.fabiolourenco.corestorage"
    compileSdk = rootProject.ext["compileSdkVersion"] as Int

    defaultConfig {
        minSdk = property("minSdkVersion") as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.ext["kotlinVersion"]}")
    implementation("androidx.core:core-ktx:${rootProject.ext["coreKtxVersion"]}")

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.ext["lifecycleVersion"]}")

    // Logging
    implementation("com.jakewharton.timber:timber:${rootProject.ext["timberVersion"]}")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:${rootProject.ext["hiltVersion"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.ext["hiltVersion"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.ext["hiltNavigationComposeVersion"]}")

    // Database
    implementation("androidx.room:room-runtime:${rootProject.ext["roomVersion"]}")
    kapt("androidx.room:room-compiler:${rootProject.ext["roomVersion"]}")
    implementation("androidx.room:room-ktx:${rootProject.ext["roomVersion"]}")

    // Android Tests
    androidTestImplementation("androidx.test.ext:junit:${rootProject.ext["junitAndroidVersion"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.ext["espressoVersion"]}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${rootProject.ext["espressoVersion"]}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${rootProject.ext["hiltTestingVersion"]}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${rootProject.ext["hiltTestingVersion"]}")
    androidTestImplementation("androidx.paging:paging-common-ktx:${rootProject.ext["pagingTestVersion"]}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.ext["composeUiTestVersion"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.ext["composeUiTestVersion"]}")
    androidTestImplementation("com.google.accompanist:accompanist-testharness:${rootProject.ext["testHarnessVersion"]}")
}

kapt {
    correctErrorTypes = true
}