package com.lookation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LookationApplication

fun main(args: Array<String>) {
    runApplication<LookationApplication>(*args)
}
