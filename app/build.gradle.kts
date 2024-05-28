plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jmailen.kotlinter")
}

android {
    namespace = "com.fabiolourenco.catbreedsapp"
    compileSdk = rootProject.ext["compileSdkVersion"] as Int
    defaultConfig {
        applicationId = "com.fabiolourenco.catbreedsapp"
        minSdk = property("minSdkVersion") as Int
        targetSdk = property("targetSdkVersion") as Int
        versionCode = property("versionCode") as Int
        versionName = property("versionName") as String
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

    // Network
    implementation("com.squareup.retrofit2:retrofit:${rootProject.ext["retrofitVersion"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.ext["retrofitVersion"]}")
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.ext["loggingInterceptorVersion"]}")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:${rootProject.ext["hiltVersion"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.ext["hiltVersion"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.ext["hiltNavigationComposeVersion"]}")

    // Image Loading
    implementation("io.coil-kt:coil-compose:${rootProject.ext["koilVersion"]}")

    // Logging
    implementation("com.jakewharton.timber:timber:${rootProject.ext["timberVersion"]}")

    // Status Bar
    implementation("com.google.accompanist:accompanist-systemuicontroller:${rootProject.ext["systemUiControllerVersion"]}")

    // Navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.ext["navigationVersion"]}")

    // Database
    implementation("androidx.room:room-runtime:${rootProject.ext["roomVersion"]}")
    kapt("androidx.room:room-compiler:${rootProject.ext["roomVersion"]}")
    implementation("androidx.room:room-ktx:${rootProject.ext["roomVersion"]}")

    // Paging
    implementation("androidx.paging:paging-compose:${rootProject.ext["pagingVersion"]}")
    implementation("androidx.paging:paging-runtime-ktx:${rootProject.ext["pagingVersion"]}")

    // Swipe to Refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:${rootProject.ext["swipeRefreshVersion"]}")

    // Unit Tests
    testImplementation("junit:junit:${rootProject.ext["junitVersion"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.ext["coroutinesTestVersion"]}")
    testImplementation("org.mockito:mockito-core:${rootProject.ext["mockitoVersion"]}")
    testImplementation("org.mockito:mockito-inline:${rootProject.ext["mockitoInlineVersion"]}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${rootProject.ext["mockitoKotlinVersion"]}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${rootProject.ext["kotlinTestJunitVersion"]}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.ext["archCoreTestingVersion"]}")

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