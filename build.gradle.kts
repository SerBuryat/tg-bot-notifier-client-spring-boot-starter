buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("java")
    id("maven-publish")
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.thunderbase.tg.client"
version = "0.2"
java.sourceCompatibility = JavaVersion.VERSION_17

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    //Spring Boot
    implementation ("org.springframework.boot:spring-boot-autoconfigure")
    implementation ("org.springframework.boot:spring-boot-starter-web")

    //Lombok
    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
}

tasks.bootJar {
    enabled = false
}

publishing {
    repositories {
        mavenLocal()
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/SerBuryat/tg-bot-notifier-client-spring-boot-starter")
            credentials {
//                add `gradle.properties` file with `gprUser` and `grpToken values`
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}