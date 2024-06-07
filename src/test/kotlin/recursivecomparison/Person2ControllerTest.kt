package com.example.demo.recursivecomparison

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
class Person2ControllerTest {
    @InjectMockKs
    private lateinit var subject: Person2Controller

    @MockK
    private lateinit var person2Repository: Person2Repository

    @MockK
    private lateinit var vehicleRepository: VehicleRepository

    @Test
    fun `it works`() {
        assert(true)
    }

    @Test
    fun `create with no vehicle`() {
        every { person2Repository.save(any()) } returns mockk()
        val actual = subject.create(Person2("any", "any", null))
        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `create with vehicle`() {
        every { person2Repository.save(any()) } returns mockk()
        every { vehicleRepository.save(any()) } returns mockk()
        val actual = subject.create(Person2("any", "any", Vehicle("any", "any")))
        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `found with no vehicle`() {
        val expected = Person2("axid", "name", null)
        every { person2Repository.findByAxid(any()) } returns Person2Do(0, "axid", "name", null)

        val actual = subject.getPerson("found")
//        assertThat(actual.body).usingRecursiveComparison().isEqualTo(expected)
        assertThat(actual.body).isEqualTo(expected)
    }

    @Test
    fun `found with vehicle`() {
        val expected = Person2("axid", "name", Vehicle("make", "model"))
        every { person2Repository.findByAxid(any()) } returns
            Person2Do(
                0,
                "axid",
                "name",
                VehicleDo(0, "make", "model"),
            )

        val actual = subject.getPerson("found")
//        assertThat(actual.body).usingRecursiveComparison().isEqualTo(expected)
        assertThat(actual.body).isEqualTo(expected)
    }

    @Test
    fun `not found`() {
        every { person2Repository.findByAxid(any()) } returns null

        val actual = subject.getPerson("found")
        assertThat(actual.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}
