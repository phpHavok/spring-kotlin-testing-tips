package com.example.demo.unusedgetter

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController(
    private val personRepository: PersonRepository,
) {
    @GetMapping("/{personAxid}")
    fun getPerson(
        @PathVariable personAxid: String,
    ): ResponseEntity<Person> =
        personRepository.findByAxid(personAxid)
            ?.let { ResponseEntity.ok(Person(it.axid, it.name)) }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    fun create(
        @RequestBody body: Person,
    ): ResponseEntity<Unit> {
        personRepository.save(
            PersonDo(
                id = 0,
                axid = body.axid,
                name = body.name
            )
        )
        return ResponseEntity.ok().build()
    }
}

@Repository
interface PersonRepository : CrudRepository<PersonDo, Long> {
    fun findByAxid(axid: String): PersonDo?
}

@Entity
@Table(name = "person")
data class PersonDo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column
    val axid: String,
    @Column
    val name: String,
)

data class Person(
    val axid: String,
    val name: String,
)
