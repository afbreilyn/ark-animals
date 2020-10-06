package com.afb.arkanimals

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArkAnimalsConfiguration {

    @Bean
    fun databaseInitializer(mustachioRepository: MustachioRepository) = ApplicationRunner {
        mustachioRepository.save(
                Mustachio("general kenobi")
        )
    }
}