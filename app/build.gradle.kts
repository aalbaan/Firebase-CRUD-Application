plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.firebasecrudapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.firebasecrudapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // AndroidX libraries
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    // Material Design components
    implementation("com.google.android.material:material:1.12.0")

    // Image loading library
    implementation("com.squareup.picasso:picasso:2.71828")

    // Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.google.firebase:firebase-database:21.0.0")

    // Testing libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
