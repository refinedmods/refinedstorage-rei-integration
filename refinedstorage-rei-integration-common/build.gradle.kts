plugins {
    id("refinedarchitect.common")
}

refinedarchitect {
    common()
    publishing {
        maven = true
    }
}

base {
    archivesName.set("refinedstorage-rei-integration-common")
}
