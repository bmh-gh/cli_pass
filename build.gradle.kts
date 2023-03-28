plugins {
    id("java")
    id("org.graalvm.buildtools.native") version "0.9.20"
}



group = "com.github.bmhgh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("info.picocli:picocli:4.7.1")
    annotationProcessor("info.picocli:picocli-codegen:4.7.1")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.github.bmhgh:cli_pass:1.0-SNAPSHOT")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

graalvmNative {
    toolchainDetection.set(false)
    binaries.all {
        resources.autodetect()
    }
    toolchainDetection.set(false)
    binaries {
        named("main") {
            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(17))
                vendor.set(JvmVendorSpec.matching("GraalVM Community"))
            })
            imageName.set("clipwm")
            verbose.set(true)
            useFatJar.set(true)
        }
    }
}

