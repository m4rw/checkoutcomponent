package org.m4rw


import org.m4rw.products.Product
import org.scalatest.{FlatSpec, GivenWhenThen}

class PriceCalculatorSpecTest extends FlatSpec with GivenWhenThen{

  behavior of "Price calculator"

  it should "get standard price of product (3 items of milk). No special price applicable" in {
    When(" I calculate a standard price products (3 milk items).")
    val milk = Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build()
    val list = new java.util.ArrayList[Product]()
    list.add(milk)
    list.add(milk)
    list.add(milk)

    Then("get right standard price")
    val calculatedPrice = PriceCalculator.calculateStandardPriceOfAllItems(list);
    assert(calculatedPrice === 16.5)
  }

  it should "get total price of zero when you pass no products" in {
    When(" I calculate a standard price products when passing empty list of products.")
    val list = new java.util.ArrayList[Product]()

    Then("get right standard price")
    val calculatedPrice = PriceCalculator.calculateStandardPriceOfAllItems(list);
    assert(calculatedPrice === 0)
  }





  it should "get total price of multipriced product (15 milk items). Special price is 3 for 10" in {
    When(" I calculate a total price of multipriced product (15 milk items). Special price for milk is 3 for 10")
    val milk = Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build()
    val calculatedPrice = PriceCalculator.calculateTotalPricePerProduct(milk,new SpecialDeal(3,10),15);
    Then("get right total price")
    assert(calculatedPrice === 50)
  }

  it should "get total price of multipriced product (16 milk items). Special price is 3 for 10" in {
    When(" I calculate a total price of multipriced product (16 milk items). Special price for milk is 3 for 10")
    val milk = Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build()
    val calculatedPrice = PriceCalculator.calculateTotalPricePerProduct(milk,new SpecialDeal(3,10),16);
    Then("get right total price")
    assert(calculatedPrice === 55.5)
  }

  it should "get total price of multipriced product (2 milk items). Special price is 3 for 10" in {
    When(" I calculate a total price of multipriced product (2 milk items). Special price for milk is 3 for 10")
    val milk = Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build()
    val calculatedPrice = PriceCalculator.calculateTotalPricePerProduct(milk,new SpecialDeal(3,10),2);
    Then("get right total price")
    assert(calculatedPrice === 11)
  }

  it should "get total price of multipriced product (0 milk items). Special price is 3 for 10" in {
    When(" I calculate a total price of multipriced product (0 milk items). Special price for milk is 3 for 10")
    val milk = Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build()
    val calculatedPrice = PriceCalculator.calculateTotalPricePerProduct(milk,new SpecialDeal(3,10),0);
    Then("get right total price")
    assert(calculatedPrice === 0)
  }

  it should "should not let you calculate price when calling method with negative amount number parameter" in {
    When(" negative number of scanned items is passed to the method")
    val milk = Product.builder().productCode("milkCode").productName("Milk").unitPrice(5.5).build()

    Then("should throw IllegalArgumentException")
    intercept[IllegalArgumentException] {
      val calculatedPrice = PriceCalculator.calculateTotalPricePerProduct(milk,new SpecialDeal(3,10),-1);  }
  }

}
