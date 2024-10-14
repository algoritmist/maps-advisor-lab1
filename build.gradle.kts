plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "org.mapsAdvisor.mapsAdvisor"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

val mockitoKotlinVersion by extra("5.3.1")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
	implementation("org.springframework.boot:spring-boot-starter-validation:3.3.4")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-tomcat")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.testcontainers:testcontainers")
	testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
