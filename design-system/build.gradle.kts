plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "br.com.evj.design_system"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.emanoel2712"
                artifactId = "design-system"
                version = "0.0.5"
                from(components["release"])

                pom.withXml {
                    asNode().appendNode("dependencies").apply {
                        configurations["implementation"].allDependencies.forEach { dep ->
                            if (dep.group != null && dep.version != null && dep.name != null && dep !is ProjectDependency) {
                                appendNode("dependency").apply {
                                    appendNode("groupId", dep.group)
                                    appendNode("artifactId", dep.name)
                                    appendNode("version", dep.version)

                                    // Escopo da dependência
                                    appendNode("scope", "compile")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//afterEvaluate {
//    publishing {
//        publications {
//            create<MavenPublication>("release") {
//                groupId = "com.github.emanoel2712"
//                artifactId = "design-system"
//                version = "0.0.4"
//                from(components["release"])
//
//                // Configurar o arquivo pom.xml
//                pom.withXml {
//                    asNode().appendNode("dependencies").apply {
//                        configurations["implementation"].allDependencies.forEach { dep ->
//                            if (dep.group != null && dep.version != null && dep.name != null && dep !is ProjectDependency) {
//                                appendNode("dependency").apply {
//                                    appendNode("groupId", dep.group)
//                                    appendNode("artifactId", dep.name)
//                                    appendNode("version", dep.version)
//
//                                    // Escopo da dependência
//                                    appendNode("scope", "compile")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
