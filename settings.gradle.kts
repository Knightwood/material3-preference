pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://www.jitpack.io")

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.aliyun.com/repository/jcenter") //jcenter()
        maven("https://maven.aliyun.com/repository/google")  //google()
        maven("https://www.jitpack.io")
    }
}
rootProject.name = "PreferenceX"
include(":app")
include(":preferencelibrary")
