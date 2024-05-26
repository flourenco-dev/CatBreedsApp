pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("org.jmailen.kotlinter") version "3.13.0" apply false
    }
}
rootProject.name = "Cat Breeds App"
include(":app")
 