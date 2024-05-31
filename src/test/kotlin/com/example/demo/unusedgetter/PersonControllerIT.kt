package com.example.demo.unusedgetter

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

// @Sql("/sql/person.sql")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PersonControllerIT {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun create() {
        mockMvc.perform(
            post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"axid":  "axid2", "name":  "jacoco"}""")
        )
            .andExpectAll(
                status().isOk,
            )
    }

    @Test
    fun `getPerson - returns 200 on happy path`() {
        mockMvc.perform(
            get("/person/axid1")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpectAll(
                status().isOk,
                jsonPath("$.axid").value("axid1"),
                jsonPath("$.name").value("jacobo person"),
            )
    }

    @Test
    fun `getPerson - returns 404 when axid is not found`() {
        mockMvc.perform(
            get("/person/axid2")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(
                status().isNotFound,
            )
    }
}
