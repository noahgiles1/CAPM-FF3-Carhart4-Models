plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.thea.mcs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kandy-api:0.7.1")
    implementation("org.jetbrains.kotlinx:dataframe:0.9.1")
    implementation("org.apache.commons:commons-math3:3.6.1")

    // Additional dependencies that might be needed
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin:4.1.0")
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:3.0.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}