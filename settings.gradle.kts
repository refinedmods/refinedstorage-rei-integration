pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://maven.pkg.github.com/refinedmods/refinedarchitect")
            credentials {
                username = "anything"
                password = "\u0067hp_oGjcDFCn8jeTzIj4Ke9pLoEVtpnZMP4VQgaX"
            }
        }
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
    }
    plugins {
        val refinedarchitectVersion: String by settings
        id("refinedarchitect.root").version(refinedarchitectVersion)
        id("refinedarchitect.base").version(refinedarchitectVersion)
        id("refinedarchitect.common").version(refinedarchitectVersion)
        id("refinedarchitect.neoforge").version(refinedarchitectVersion)
        id("refinedarchitect.fabric").version(refinedarchitectVersion)
    }
}

rootProject.name = "refinedstorage-rei-integration"
include("refinedstorage-rei-integration-common")
include("refinedstorage-rei-integration-neoforge")
include("refinedstorage-rei-integration-fabric")
