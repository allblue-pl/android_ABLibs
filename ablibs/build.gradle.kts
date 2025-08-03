import java.net.NetworkInterface
import java.net.SocketException

plugins {
    id(libs.plugins.android.library.get().pluginId)
}

android {
    namespace = "pl.allblue.ablibs"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

fun getLocalIP(): String {
    try {
        for (ni in NetworkInterface.networkInterfaces()) {
            for (niAddress in ni.interfaceAddresses) {
                val ip = niAddress.address.hostAddress
                println("Checking network interface IP: $ip")

                if (ip.length <= 15 && !ip.equals("127.0.0.1") &&
                    ip.indexOf(":") == -1) {
                    println("Determined network interface IP: $ip")
                    return ip
                }
            }
        }
    } catch (e: SocketException) {
        println("Error getting local IP: " + e.message)
    }

    return "127.0.0.1"
}