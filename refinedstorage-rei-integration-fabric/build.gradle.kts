plugins {
    id("refinedarchitect.fabric")
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
        name = "Cloth Config, REI"
        url = uri("https://maven.shedaniel.me/")
    }
    maven {
        name = "ModMenu"
        url = uri("https://maven.terraformersmc.com/")
    }
}

refinedarchitect {
    modId = "refinedstorage_rei_integration"
    fabric()
    publishing {
        maven = true
    }
}

base {
    archivesName.set("refinedstorage-rei-integration-fabric")
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
    modApi("com.refinedmods.refinedstorage:refinedstorage-fabric:${refinedstorageVersion}")
    modApi("dev.architectury:architectury-fabric:${architecturyVersion}")
    modApi("me.shedaniel.cloth:cloth-config-fabric:${clothConfigVersion}")
    modApi("me.shedaniel:RoughlyEnoughItems-fabric:${reiVersion}")
}
