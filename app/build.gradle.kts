plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.dado.quicknews"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dado.quicknews"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val newsApiKey = System.getenv("NEWS_API_KEY") ?: "20801b10ff1b4e5bb0b3f1138187f998"
        buildConfigField("String", "NEWS_API_KEY", "\"$newsApiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.lifeCycleRuntimeKtx)
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeUI)
    implementation(Dependencies.composeUIGraphics)
    implementation(Dependencies.composeUIToolingPreview)
    implementation(Dependencies.composeMaterial3)

    implementation(project(Modules.utils))

    // dagger - hilt
    implementation (Dependencies.hiltNavigationComposeCompose)
    implementation(Dependencies.hiltNavigationCompose)
    implementation(Dependencies.hiltAndroid)
    testImplementation("junit:junit:4.12")
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltAndroidCompiler)

    // REST API
    implementation(Dependencies.retrofit)
    implementation(Dependencies.okhttp)
    implementation(Dependencies.moshi)
    implementation(Dependencies.moshiConverter)
    implementation(Dependencies.loggingInterceptor)

    // Room DB
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomKtx)

    // Coroutines
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)
    testImplementation(Dependencies.coroutinesTest)

    // Splash Screen
    implementation(Dependencies.splashScreen)

    // Coil library
    implementation(Dependencies.coil)

    // Pull refresh
    implementation(Dependencies.pullRefresh)

    // Mockito
    testImplementation(Dependencies.mockito)
    testImplementation(Dependencies.mockitoKotlin)

    // Gson serialization
    implementation(Dependencies.gson)
}

kapt {
    correctErrorTypes = true
}