package com.afb.arkanimals

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("ark-animals")
data class ArkAnimalsProperties(var title: String, val banner: Banner) {
    data class Banner(val title: String? = null, val content: String)
}
