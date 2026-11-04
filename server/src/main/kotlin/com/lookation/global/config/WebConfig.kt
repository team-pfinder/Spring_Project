package com.lookation.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths

@Configuration
class WebConfig(@Value("\${lookation.upload-dir}") uploadDir: String) : WebMvcConfigurer {

    private val location: String = Paths.get(uploadDir).toAbsolutePath().normalize().toUri().toString()
        .let { if (it.endsWith("/")) it else "$it/" }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations(location)
    }
}
