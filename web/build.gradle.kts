import com.moowork.gradle.node.npm.NpmTask

plugins {
    base
    id("com.github.node-gradle.node") version "2.2.4"
}

node {
    version = "14.13.0"
    npmVersion = "6.14.8"
    download = true
}

tasks.named<NpmTask>("npm_run_build") {
    setEnvironment(mapOf("CI" to "true"))
    inputs.files(fileTree("public"))
    inputs.files(fileTree("src"))
    inputs.file("package.json")
    inputs.file("package-lock.json")

    outputs.dir("build")
}

tasks.assemble {
    dependsOn("npm_run_build")
}

val testsExecutedMarkerName: String = "${projectDir}/.tests.executed"

val test by tasks.registering(NpmTask::class) {
    dependsOn("assemble")

    setEnvironment(mapOf("CI" to "true"))
    setArgs(listOf("run", "test"))

    inputs.files(fileTree("src"))
    inputs.file("package.json")
    inputs.file("package-lock.json")

    doLast {
        File(testsExecutedMarkerName).appendText("delete this file to force re-execution JavaScript tests")
    }
    outputs.file(testsExecutedMarkerName)
}

tasks.check {
    dependsOn(test)
}

tasks.clean {
    delete(testsExecutedMarkerName)
}
