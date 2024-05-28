plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jmailen.kotlinter")
}

android {
    namespace = "com.fabiolourenco.core"
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
    implementation(project(":common"))
    implementation(project(":core-network"))
    implementation(project(":core-storage"))

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

    // Unit Tests
    testImplementation("junit:junit:${rootProject.ext["junitVersion"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.ext["coroutinesTestVersion"]}")
    testImplementation("org.mockito:mockito-core:${rootProject.ext["mockitoVersion"]}")
    testImplementation("org.mockito:mockito-inline:${rootProject.ext["mockitoInlineVersion"]}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${rootProject.ext["mockitoKotlinVersion"]}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${rootProject.ext["kotlinTestJunitVersion"]}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.ext["archCoreTestingVersion"]}")
}

kapt {
    correctErrorTypes = true
}