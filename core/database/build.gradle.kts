plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.monomi.core.database"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        // The schemas directory contains a schema file for each version of the Room database.
        // This is required to enable Room auto migrations.
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
        ksp {
            // Room 스키마 위치
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    // 테스트 소스셋에서 스키마 파일에 접근할 수 있도록 설정
    sourceSets.getByName("test") {
        assets.srcDir(files("$projectDir/schemas"))
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.androidx.core.ktx)

    implementation(libs.hilt.android.core)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)

    // coroutines
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.multidex)
    ksp(libs.room.compiler)

    // json parsing
    implementation(libs.kotlinx.serialization.json)
}