package org.m4rw

import org.junit.runner.RunWith
import org.m4rw.Product._
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, GivenWhenThen}

@RunWith(classOf[JUnitRunner])
class ProductInMemoryRepositorySpecTest extends FlatSpec with GivenWhenThen{


  behavior of "Product Repository"

  it should "return empty list of product if not initialized" in {
    Given("Product repository is not initialized")
    val productRepository =  new ProductInMemoryRepositoryImpl()

    When("when I get list of all products")
    val emptyList = productRepository.getListOfAllProducts

    Then("list of product is empty")
    assert(emptyList.size() === 0 )
  }

  it should "properly add one product" in {
    Given("Product repository is not initialized")
    val productRepository =  new ProductInMemoryRepositoryImpl()

    When("add one product")
    val product = initializeProduct

    productRepository.addNewProduct(product.getProductCode, product)


    Then("you can get one product from the repository")
    val emptyList = productRepository.getListOfAllProducts
    assert(emptyList.size() === 1 )

    Then("you can get product by its product code")
    val persistedProduct = productRepository.getProductByItsCode(product.getProductCode)
    assert(persistedProduct.equals(product))
    info("product seems right")

    Then("you should get an exception when trying to get product with incorrect code")
    intercept[NoSuchElementException] {
      productRepository.getProductByItsCode("WRONG CODE")
    }

  }


  it should "let update product using proper product code" in {
    Given("Product repository has one product")
    val productRepository =  new ProductInMemoryRepositoryImpl()
    var product = initializeProduct
    productRepository.addNewProduct(product.getProductCode,product)
    assert(productRepository.getListOfAllProducts.size() == 1)

    When("try to update product with proper product code")
    product.setProductName("changed product name")
    productRepository.updateProduct(product.getProductCode,product)

    Then("product is correctly updated")
    productRepository.getProductByItsCode(product.getProductCode)
    assert(product.getProductName === "changed product name")
    info("updated product seems right")
    assert(productRepository.getListOfAllProducts.size() === 1)
  }

  it should "should not let you update product that does not exist" in {
    Given("Product repository has one product")
    val productRepository =  new ProductInMemoryRepositoryImpl()
    val product = initializeProduct
    productRepository.addNewProduct(product.getProductCode,product)
    assert(productRepository.getListOfAllProducts.size() == 1)

    When("try to update product that does not exist")
    val otherProduct = Product.builder()
        .productCode("other code")
        .productName("othername")
        .unitPrice(33.33)
      .build()

    Then("should throw NoSuchElementException")
    intercept[NoSuchElementException] {
      productRepository.updateProduct("other code", otherProduct)
      product
    }
  }

  private def initializeProduct = {
    builder()
      .productCode("some random product")
      .productName("some random product name")
      .unitPrice(20.1)
      .build()
  }
}