buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.4")
    }
    repositories{
        google()
        mavenCentral()
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}