pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.8"
    id("dev.kikugie.loom-back-compat") version "0.4.2"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        // SVC Fabric ranges that current Loom + loom-back-compat can build.
        // https://github.com/henkelmax/simple-voice-chat
        // https://github.com/BambooOrg/CustomDiscs-SVC
        versions("1.20.1", "1.21.1", "1.21.8", "1.21.11")
        version("26.1.x", "26.1.2")
        version("26.2.x", "26.2")
        version("26.3.x", "26.3")
        // 1.16.x–1.19.x: Voicechat API is compatible, but fabric-loom-remap
        // (loom-back-compat) is not published for those Loom lines.
        vcsVersion = "26.2.x"
    }
}

rootProject.name = "turnitdown"
