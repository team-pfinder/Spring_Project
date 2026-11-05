package com.lookation.global.seed

import com.lookation.global.security.AdminProperties
import com.lookation.global.security.AdminSeeder
import com.lookation.repository.AccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.core.annotation.AnnotationAwareOrderComparator
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.function.Supplier

class InitializeServiceActivationTest {

    private val runner = ApplicationContextRunner()
        .withUserConfiguration(InitializeService::class.java)

    @Test
    fun `not registered in the test profile even when enabled`() {
        runner.withPropertyValues("spring.profiles.active=test", "lookation.seed.enabled=true")
            .run { assertThat(it).doesNotHaveBean(InitializeService::class.java) }
    }

    @Test
    fun `not registered in a deployment profile even when enabled`() {
        runner.withPropertyValues("spring.profiles.active=prod", "lookation.seed.enabled=true")
            .run { assertThat(it).doesNotHaveBean(InitializeService::class.java) }
    }

    @Test
    fun `not registered in the local profile when disabled`() {
        runner.withPropertyValues("spring.profiles.active=local")
            .run { assertThat(it).doesNotHaveBean(InitializeService::class.java) }
    }

    @Test
    fun `registered in the local profile when enabled`() {
        runner.withPropertyValues("spring.profiles.active=local", "lookation.seed.enabled=true")
            .run { assertThat(it).hasSingleBean(InitializeService::class.java) }
    }

    @Test
    fun `runs after AdminSeeder`() {
        ApplicationContextRunner()
            .withUserConfiguration(AdminSeeder::class.java, InitializeService::class.java)
            .withBean(AdminProperties::class.java, Supplier { AdminProperties() })
            .withBean(AccountRepository::class.java, Supplier { mock(AccountRepository::class.java) })
            .withBean(PasswordEncoder::class.java, Supplier { mock(PasswordEncoder::class.java) })
            .withPropertyValues("spring.profiles.active=local", "lookation.seed.enabled=true")
            .run { context ->
                val runners = context.getBeansOfType(ApplicationRunner::class.java).values.toMutableList()
                AnnotationAwareOrderComparator.sort(runners)

                assertThat(runners.map { it.javaClass.simpleName })
                    .containsSubsequence("AdminSeeder", "InitializeService")
            }
    }
}
