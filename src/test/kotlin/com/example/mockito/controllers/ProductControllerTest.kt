package com.example.mockito.controllers

import com.example.mockito.models.Product
import com.example.mockito.service.ProductService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [ProductController::class])
@ExtendWith(MockitoExtension::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var productService: ProductService

    private lateinit var product: Product

    @BeforeEach
    fun init() {
        product = Product(name = "motor" , description = "from cambodia" , price = 100.00)
    }

    @Test
    @Throws(Exception::class)
    fun created() {
        // Mocking the service behavior
        given(productService.create(product)).willAnswer { invocation -> invocation.getArgument(0) }

        // Performing an HTTP POST request to create a product
        val response = mockMvc.perform(
            post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
        )

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`(product.name)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.`is`(product.description)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.`is`(product.price)))
    }
}
