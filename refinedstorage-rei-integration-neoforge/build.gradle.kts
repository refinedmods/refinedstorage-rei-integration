plugins {
    id("refinedarchitect.neoforge")
}

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/refinedmods/refinedstorage2")
        credentials {
            username = "anything"
            password = "\u0067hp_oGjcDFCn8jeTzIj4Ke9pLoEVtpnZMP4VQgaX"
        }
    }
    maven {
        name = "REI"
        url = uri("https://maven.shedaniel.me/")
    }
    maven {
        name = "Architectury"
        url = uri("https://maven.architectury.dev/")
    }
}

refinedarchitect {
    modId = "refinedstorage_rei_integration"
    neoForge()
    publishing {
        maven = true
    }
}

base {
    archivesName.set("refinedstorage-rei-integration-neoforge")
}

val refinedstorageVersion: String by project
val architecturyVersion: String by project
val clothConfigVersion: String by project
val reiVersion: String by project

val commonJava by configurations.existing
val commonResources by configurations.existing

dependencies {
    compileOnly(project(":refinedstorage-rei-integration-common"))
    commonJava(project(path = ":refinedstorage-rei-integration-common", configuration = "commonJava"))
    commonResources(project(path = ":refinedstorage-rei-integration-common", configuration = "commonResources"))
    api("com.refinedmods.refinedstorage:refinedstorage-neoforge:${refinedstorageVersion}")
    api("dev.architectury:architectury-neoforge:${architecturyVersion}")
    api("me.shedaniel.cloth:cloth-config-neoforge:${clothConfigVersion}")
    api("me.shedaniel:RoughlyEnoughItems-neoforge:${reiVersion}")
}
