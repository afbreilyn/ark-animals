package com.afb.arkanimals

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArkAnimalsConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            cardRepository: CardRepository) = ApplicationRunner {
        val smaldini = userRepository.save(User("smaldini", "Stéphane", "Maldini"))
        cardRepository.save(Card(
                title = "Reactor Bismuth is out",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = smaldini
        ))
        cardRepository.save(Card(
                title = "Reactor Aluminium has landed",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = smaldini
        ))
    }
}