package com.example.mockito.service.impl

import com.example.mockito.models.Product
import com.example.mockito.repository.ProductRepository
import com.example.mockito.service.ProductService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheConfig
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["products"])
class IProductService (
    private val productRepository: ProductRepository
) : ProductService {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun list(): List<Product> {
        log.info("Getting list of products")
        return productRepository.findAll()
    }

    override fun detail(id: Long): Product? {
        log.info("Getting product with id {}", id)
        return productRepository.findByIdOrNull(id)
    }

    override fun create(product: Product): Product {
        log.info("Creating product: {}", product)
        return productRepository.save(product)
    }

    override fun update(id: Long, updateProduct: Product): Product {
        log.info("Updating product with id {}", id)
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product not found") }
        product.apply {
            name = updateProduct.name
            description = updateProduct.description
            price = updateProduct.price
        }
        return productRepository.save(product)
    }
    override fun delete(id: Long) {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product not found") }
        log.info("Deleting product with id {}", id)
        productRepository.delete(product)
    }
}