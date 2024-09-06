package br.com.challenge2.product.application.useCaseImpl

import br.com.challenge2.product.avro.ProductAvro
import br.com.challenge2.product.core.domain.Product
import br.com.challenge2.product.core.useCase.SendListOfProductsUseCase
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class SendListOfProductsUseCaseImpl : SendListOfProductsUseCase{
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, List<ProductAvro>>
    @Autowired
    private lateinit var mapper: ModelMapper

    override fun sendListOfProducts(products: MutableSet<Product>) {
        val productsAvro : List<ProductAvro> = products.map {
            product: Product -> mapper.map(product, ProductAvro::class.java) }

        kafkaTemplate.send("product", productsAvro)
    }
}