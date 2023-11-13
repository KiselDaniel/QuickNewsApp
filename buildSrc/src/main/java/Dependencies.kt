object Dependencies {

    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.compose}" }
    val composeUI by lazy { "androidx.compose.ui:ui:" }
    val composeUIGraphics by lazy { "androidx.compose.ui:ui-graphics:" }
    val composeUIToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val composeMaterial3 by lazy { "androidx.compose.material3:material3" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }
    val composeTestJUnit4 by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.compose}" }
    val composeUITooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.compose}" }
    val composeUITestManifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.compose}" }
    val lifeCycleViewModelKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleViewModelKtx}" }
    val lifeCycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycleRuntimeKtx}" }

    // dagger hilt
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" }
    val hiltNavigationComposeCompose by lazy { "androidx.navigation:navigation-compose:${Versions.hiltNavigationComposeCompose}" }

    // REST API
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val okhttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttp}" }
    val gsonConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}" }
    val moshi by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.moshi}" }
    val moshiConverter by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.moshiConverter}" }
    val loggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}" }

    // Coroutines
    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val coroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}" }

    // Room DB
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }

    // Splash screen
    val splashScreen by lazy { "androidx.core:core-splashscreen:${Versions.splashScreen}" }

    // Coil library
    val coil by lazy { "io.coil-kt:coil-compose:${Versions.coil}" }

    // Pull refresh
    val pullRefresh by lazy { "androidx.compose.material:material:${Versions.pullRefresh}" }

    // Mockito
    val mockito by lazy { "org.mockito:mockito-core:${Versions.mockito}" }
    val mockitoKotlin by lazy {"org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}"}
}

object Modules {

    const val utils = ":utils"
}