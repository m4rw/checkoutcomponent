package org.m4rw


import org.junit.runner.RunWith
import org.m4rw.products.{Product, ProductInMemoryRepositoryImpl}
import org.scalatest.{FlatSpec, GivenWhenThen}
import org.scalatest.PrivateMethodTester._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ShoppingCartScannerImplSpecTest extends FlatSpec with GivenWhenThen {

  behavior of "Shopping cart scanner"

  it should "get correct standard price after adding two milks and one soda. Scanner does not use Product Repository" in {
    Given("Shopping cart is empty")
    val scanner =  new ShoppingCartScannerImpl(new java.util.HashMap[String, SpecialDeal](), new ProductInMemoryRepositoryImpl());

    When("when I add two milks and one soda")
    val milk = Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build()
    val soda = Product.builder().productCode("sodaCode").productName("soda").unitPrice(10).build()
    //scanning two same milks
    scanner.scanProduct(milk.getProductCode,milk)
    val twoMilks = scanner.scanProduct(milk.getProductCode,milk)
    scanner.scanProduct(soda.getProductCode,soda)

    Then("get right total price")
    assert(scanner.getStandardTotalPrice === 21)

    Then("get right amount of milk items")
    //a trick to be able to test private method 'getNumberOfItems'
    val getNumberOfItems = PrivateMethod[String]('getNumberOfItems)
    assert(scanner.invokePrivate(getNumberOfItems("milkCode")) === 2)
    assert(twoMilks === 2)

    Then("get correct amount of items in total")
    assert(scanner.getNumberOfScannedItems() === 3)
  }

  it should "get correct standard price after adding two milks and one soda. Scanner uses Product Repository" in {
    Given("Shopping cart is empty. Product repository contains milk and soda products")

    val scanner =  new ShoppingCartScannerImpl(new java.util.HashMap[String, SpecialDeal](), getProductRepositoryStub());

    When("when I scan two milks and one soda")

    //scanning two same milks
    scanner.scanProduct("milkCode")
    scanner.scanProduct("milkCode")
    scanner.scanProduct("sodaCode")

    Then("get right total price")
    assert(scanner.getStandardTotalPrice === 21 )

    Then("get right amount of milk items")
    //a trick to be able to test private method 'getNumberOfItems'
    val getNumberOfItems = PrivateMethod[String]('getNumberOfItems)
    assert(scanner.invokePrivate(getNumberOfItems("milkCode"))  === 2)

    Then("get correct amount of items in total")
    assert(scanner.getNumberOfScannedItems() === 3)
  }


  it should "get correct standard price after adding products that don't have respective special deals" in {
    Given("Shopping cart is empty")
    val scanner =  new ShoppingCartScannerImpl(new java.util.HashMap[String, SpecialDeal](), new ProductInMemoryRepositoryImpl);

    When("when I add two milks and one soda")
    val milk = Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build()
    val soda = Product.builder().productCode("sodaCode").productName("soda").unitPrice(10).build()
    //scanning two same milks
    scanner.scanProduct(milk.getProductCode,milk)
    scanner.scanProduct(milk.getProductCode,milk)
    scanner.scanProduct(soda.getProductCode,soda)

    Then("standard Total Price should equal standard price")
    val standardPrice = scanner.getStandardTotalPrice;
    assert(standardPrice === scanner.getTotalPrice )
    info("since there is no special deals for this shopping cart total price equals standard price")
    assert(scanner.getStandardTotalPrice === 21)
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
