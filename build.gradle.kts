import org.gradle.jvm.tasks.Jar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.0"
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
}


tasks.create("fatjar", Jar::class) {
    group = "my tasks" // OR, for example, "build"
    description = "Creates a self-contained fat JAR of the application that can be run."
    manifest.attributes["Main-Class"] = "com.github.bmhgh.CliApp"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    with(tasks.jar.get())
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
