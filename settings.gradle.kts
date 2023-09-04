pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "CryptoFeed"
include(":app")
include(":remote_module")
include(":http_module")
include(":presenter_module")
include(":cache_module")
include(":domain_module")
include(":ui_module")
include(":composite_module")
