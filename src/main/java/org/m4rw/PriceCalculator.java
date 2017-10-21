package org.m4rw;

import org.m4rw.products.Product;

import javax.annotation.Nonnegative;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility class to encapsulate logic for price calculation
 */

public  class PriceCalculator {

    /**
     * Utility method to calculate total price of list of products. It sums up all the unit prices of products from the list
     * @param products
     * @return total price of products
     */
    @Nonnegative
    public static double calculateStandardPriceOfAllItems(Collection<Product> products) {
        double price = 0;
        for (Product value : products) {
            price += value.getUnitPrice();
        }
        return price;
    }
    /**
     * Utility method to calculate total price of multi-priced products.
     * It sums up all the unit prices of products. Special deals are factored in
     * (i.e. when you calculate a price of 6 products that have 3 for 2 special price, total price would be 4 x unit price
     * @param product {@link org.m4rw.products.Product}
     * @param deal {@link org.m4rw.SpecialDeal}
     * @param numberOfScannedItems number of items that you want to calculate
     * @return total price of products
     */
    @Nonnegative
    public static double calculateTotalPricePerProduct(Product product, SpecialDeal deal, int numberOfScannedItems) {
        checkArgument(numberOfScannedItems >=0, "numberOfScannedItems was %s but expected non negative", numberOfScannedItems);
        checkNotNull(numberOfScannedItems);

        double price = 0;
        //calculate price per product group
        int amountOfItems = deal.getAmountOfItems();
        int quotient = numberOfScannedItems / amountOfItems;
        int remainder = numberOfScannedItems % amountOfItems;

        double specialPrice = deal.getPrice();
        price = quotient * specialPrice + remainder * product.getUnitPrice();
        return price;
    }
}
