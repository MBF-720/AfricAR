plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "pfe.africar"
    compileSdk = 34

    defaultConfig {
        applicationId = "pfe.africar"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("com.google.firebase:firebase-firestore:24.3.1")
    implementation ("com.google.firebase:firebase-storage:20.1.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-firestore:24.11.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation ("com.google.firebase:firebase-auth:VERSION")
    implementation ("com.google.firebase:firebase-firestore:23.0.3")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")

    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:21.1.1")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))

    implementation ("com.google.firebase:firebase-firestore:24.2.0")
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.0")


    implementation ("com.squareup.picasso:picasso:2.5.2")

    implementation("com.vanniktech:android-image-cropper:4.5.0")








}


