package com.example.mockito.service

import com.example.mockito.models.Product

interface ProductService {
    fun list(): List<Product>
    fun detail(id: Long): Product?
    fun create(product: Product): Product
    fun update(id: Long, updateProduct: Product): Product
    fun delete(id: Long)
}