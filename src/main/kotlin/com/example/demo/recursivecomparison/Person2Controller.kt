package com.example.demo.recursivecomparison

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
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
@RequestMapping("/person2")
class Person2Controller(
    private val personRepository: Person2Repository,
    private val vehicleRepository: VehicleRepository,
) {
    @GetMapping("/{personAxid}")
    fun getPerson(
        @PathVariable personAxid: String,
    ): ResponseEntity<Person2> =
        personRepository.findByAxid(personAxid)
            ?.let {
                ResponseEntity.ok(
                    Person2(
                        it.axid,
                        it.name,
                        it.vehicle?.let {
                            Vehicle(it.make, it.model)
                        },
                    ),
                )
            }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    fun create(
        @RequestBody body: Person2,
    ): ResponseEntity<Unit> {
        val vehicle =
            body.vehicle?.let {
                vehicleRepository.save(
                    VehicleDo(
                        id = 0,
                        make = it.make,
                        model = it.model,
                    ),
                )
            }

        personRepository.save(
            Person2Do(
                id = 0,
                axid = body.axid,
                name = body.name,
                vehicle = vehicle,
            ),
        )
        return ResponseEntity.ok().build()
    }
}

@Repository
interface Person2Repository : CrudRepository<Person2Do, Long> {
    fun findByAxid(axid: String): Person2Do?
}

@Repository
interface VehicleRepository : CrudRepository<VehicleDo, Long>

@Entity
@Table(name = "person2")
data class Person2Do(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,
    @Column(unique = true)
    val axid: String,
    @Column
    val name: String,
    @OneToOne
    val vehicle: VehicleDo?,
)

@Entity
@Table(name = "vehicle")
data class VehicleDo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,
    @Column
    val make: String,
    @Column
    val model: String,
)

data class Person2(
    val axid: String,
    val name: String,
    val vehicle: Vehicle?,
)

data class Vehicle(
    val make: String,
    val model: String,
)
