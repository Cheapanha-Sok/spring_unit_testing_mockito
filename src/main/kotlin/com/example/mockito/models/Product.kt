package com.example.mockito.models

import jakarta.persistence.*


@Entity
@Table(name = "products")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var name: String? = null,
    var description: String? = null,
    var price: Double? = 0.00,
)