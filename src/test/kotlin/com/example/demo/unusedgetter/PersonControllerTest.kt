package com.example.demo.unusedgetter

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus

@ExtendWith(MockKExtension::class)
class PersonControllerTest {
    @InjectMockKs
    private lateinit var subject: PersonController

    @MockK()
    private lateinit var repo: PersonRepository

    @Test
    fun `it works`() {
        assert(true)
    }

    @Test
    fun create() {
        every { repo.save(any()) } returns mockk()
        val actual = subject.create(Person("any", "any"))
        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `found`() {
        val expected = Person("axid", "name")
        every { repo.findByAxid(any()) } returns PersonDo(0, "axid", "name")

        val actual = subject.getPerson("found")
        assertThat(actual.body).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `not found`() {
        every { repo.findByAxid(any()) } returns null

        val actual = subject.getPerson("found")
        assertThat(actual.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}
