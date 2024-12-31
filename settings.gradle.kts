pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "weatherforecast"
include(":app")
include(":core:common")
include(":core:datasource")
include(":features:home:data")
include(":features:home:domain")
include(":features:home:ui")
include(":features:memories:data")
include(":features:memories:domain")
include(":features:memories:ui")
include(":core:navigation")
