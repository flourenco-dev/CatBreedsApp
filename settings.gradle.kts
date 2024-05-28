pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("org.jmailen.kotlinter") version "3.13.0" apply false
        id("com.android.library") version "8.2.2"
        id("org.jetbrains.kotlin.android") version "1.9.10"
    }
}
rootProject.name = "Cat Breeds App"
include(":app")
include(":core-network")
include(":core-storage")
include(":core")
include(":common")
include(":ui-feature-breeds")
include(":ui-feature-favorites")
include(":ui-feature-details")
include(":ui-feature-paginatedBreeds")
include(":ui")
