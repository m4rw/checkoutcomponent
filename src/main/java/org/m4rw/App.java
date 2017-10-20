package org.m4rw;

/**
 * CheckoutComponent application example
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        boostrapApplication();


    }

    private static void boostrapApplication() {

        ProductRepository productDao = new ProductInMemoryRepositoryImpl();
        productDao.addNewProduct("someProductCode", new Product.ProductBuilder()
                        .productCode("someProductCode")
                        .productName("someProductName")
                        .unitPrice(20.2)
                .build());
    }
}
