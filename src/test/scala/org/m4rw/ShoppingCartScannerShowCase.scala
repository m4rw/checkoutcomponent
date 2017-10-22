package org.m4rw

import org.m4rw.products.{Product, ProductInMemoryRepositoryImpl, ProductRepository}
import org.scalatest.{FlatSpec, GivenWhenThen}

class ShoppingCartScannerShowCase extends FlatSpec with GivenWhenThen{

  val scanner = new ShoppingCartScannerImpl(getSpecialDealForMilkSub, getProductRepositoryStub());

  behavior of "Shopping cart scanner"

  it should "get correct price of the busket that have mutli priced product. Scanner uses Product Repository" in {

    Given("Shopping cart is empty. Product repository contains milk and soda products")
    When("when I scan two milks and one soda")

    //scanning three milk items (same product)
    scanner.scanProduct("milkCode")
    scanner.scanProduct("milkCode")
    scanner.scanProduct("milkCode")

    scanner.scanProduct("sodaCode")

    Then("get right total price")
    assert(scanner.getTotalPrice === 20 )
    info("special deal is taken into account")


    When ("remove one milk from the basket ")
    scanner.removeProduct("milkCode",Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build())
    Then("Get right total price. Now, standard unit prices should apply to milk products")
    info("Special price cannot be taken into account")
    assert(scanner.getTotalPrice === 21)


    Then("get correct amount of items in total")
    assert(scanner.getNumberOfScannedItems() === 3)
  }

  def getSpecialDealForMilkSub(): java.util.HashMap[String,SpecialDeal] = {
    val specialDeal = new java.util.HashMap[String, SpecialDeal]()
    specialDeal.put("milkCode", new SpecialDeal(3,10))
    specialDeal
  }


  // factory method for ProductRepository stub
  def getProductRepositoryStub(): ProductRepository = {
    val productRepo = new ProductInMemoryRepositoryImpl()
    val milk = Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build()
    val soda = Product.builder().productCode("sodaCode").productName("soda").unitPrice(10).build()
    productRepo.addNewProduct(milk.getProductCode, milk)
    productRepo.addNewProduct(soda.getProductCode, soda)
    productRepo
  }

}
