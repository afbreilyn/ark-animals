import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
	kotlin("plugin.allopen")
	kotlin("kapt")
}

//group = "com.afb"
//version = "0.0.1-SNAPSHOT"
//java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("com.h2database:h2")

	testImplementation("org.mockito:mockito-core:2.+")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {}
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("com.ninja-squad:springmockk:2.0.3")

	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

	kapt("org.springframework.boot:spring-boot-configuration-processor")
}
allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<Copy>("copyNpmBuild") {
	dependsOn(":web:build")
	from("${projectDir}/../web/build")
	into("src/main/resources/static")
}

tasks.assemble {
	dependsOn("copyNpmBuild")
	mustRunAfter("copyNpmBuild")
}

tasks.clean {
	delete(fileTree("src/main/resources/static/"))
}