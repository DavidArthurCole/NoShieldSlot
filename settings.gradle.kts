pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://maven.architectury.dev/") {
            name = "Architectury"
        }
        maven("https://files.minecraftforge.net/maven/") {
            name = "Forge"
        }
        gradlePluginPortal()
    }
}
