buildscript {
    extra.apply {
        // Sdk and tools
        set("compileSdkVersion", 34)
        set("minSdkVersion", 24)
        set("targetSdkVersion", 34)
        set("versionCode", 1)
        set("versionName", "1.0")
        set("kotlinterVersion", "3.13.0")
        set("gradleVersion", "8.2.2")

        // App dependencies
        set("activityComposeVersion", "1.9.0")
        set("composeVersion", "1.5.1")
        set("composeCompilerVersion", "1.5.3")
        set("kotlinVersion", "1.9.10")
        set("hiltVersion", "2.46.1")
        set("hiltNavigationComposeVersion", "1.2.0")
        set("retrofitVersion", "2.9.0")
        set("loggingInterceptorVersion", "4.10.0")
        set("lifecycleVersion", "2.8.0")
        set("timberVersion", "5.0.1")
        set("coreKtxVersion", "1.13.1")
        set("appCompatVersion", "1.6.1")
        set("material3Version", "1.2.1")
        set("koilVersion", "2.2.2")

        // Test dependencies
        set("junitVersion", "4.13.2")
        set("coroutinesTestVersion", "1.7.3")
        set("mockitoVersion", "5.11.0")
        set("mockitoInlineVersion", "5.2.0")
        set("mockitoKotlinVersion", "5.0.0")
        set("kotlinTestJunitVersion", "1.9.10")
        set("archCoreTestingVersion", "2.2.0")
    }

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${property("gradleVersion")}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${property("kotlinVersion")}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${property("hiltVersion")}")
        classpath("org.jmailen.gradle:kotlinter-gradle:${property("kotlinterVersion")}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
