package org.m4rw;

import org.m4rw.products.Product;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility class to encapsulate logic for price calculation
 */
public  class PriceCalculator {

    public static double calculateStandardPriceOfAllItems(Collection<Product> products) {
        double price = 0;
        for (Product value : products) {
            price += value.getUnitPrice();
        }
        return price;
    }

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
