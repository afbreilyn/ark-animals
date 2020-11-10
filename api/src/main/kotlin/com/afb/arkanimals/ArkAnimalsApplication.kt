package com.afb.arkanimals

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
//@EnableConfigurationProperties(ArkAnimalsProperties::class)
class ArkAnimalsApplication

fun main(args: Array<String>) {
	runApplication<ArkAnimalsApplication>(*args)
}
