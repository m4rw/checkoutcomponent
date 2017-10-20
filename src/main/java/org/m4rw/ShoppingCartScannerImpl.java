package org.m4rw;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@code ShoppingCartScanner} that uses an {@code ArrayListMultimap} to store
 * the product items for a given key (product). A {@link HashMap} associates each key with an
 * {@link ArrayList} of values.
 *
 *  * <p>This class is stateful and is not threadsafe when any concurrent operations update the
 * scanner. Concurrent read operations will work correctly.
 *
 * <p>See the Guava User Guide article on <a href=
 * "https://github.com/google/guava/wiki/NewCollectionTypesExplained#multimap">
 * {@code Multimap}</a>.
 *
 */

public class ShoppingCartScannerImpl implements ShoppingCartScanner {

    Multimap<String,Product> scannedProducts = ArrayListMultimap.create();
    //map of product -> special deal
    HashMap<String, SpecialDeal> specialDeals;
    ProductRepository productRepository;


    ShoppingCartScannerImpl(HashMap<String, SpecialDeal> specialDeals, ProductRepository productRepository){
        this.specialDeals = specialDeals;
        this.productRepository = productRepository;
    }

    /**
     * {@inheritDoc}
     **
     */
    public int scanProduct(String productCode, Product item) {
         scannedProducts.put(productCode, item);
         return scannedProducts.size();
    }

    /**
     * {@inheritDoc}
     **
     */
    public int scanProduct(String productCode) {
        Product product = productRepository.getProductByItsCode(productCode);
        scannedProducts.put(productCode, product);
        return scannedProducts.size();
    }

    /**
     * {@inheritDoc}
     *
     */
    public boolean removeProduct(String productCode, Product item) {
        return scannedProducts.remove(productCode, item);
    }

    /**
     * {@inheritDoc}
     *
     */
    public double getStandardTotalPrice() {
        double price = 0;
        for (Product value : scannedProducts.values()) {
            price += value.unitPrice;
        }
        return price;
    }

    /**
     * {@inheritDoc}
     *
     */
    public double getTotalPrice() {
        double price = 0;

        //iterate through scanned products
        for (String product : scannedProducts.keys()) {

            //check if special deal for the product exists
            if (specialDealExistsForTheProduct(product)) {

                //how many items of this product we've got scanned
                int numberOfItems = getNumberOfItems(product);
                price += calculateTotalPricePerProduct(product, numberOfItems);
            }
            else{//no special deal for the product
                price += calculateStandardPriceOfAllItems(product);
            }
        }
        return price;
    }

    private double calculateTotalPricePerProduct(String productCode, int numberOfScannedItems) {
        checkArgument(numberOfScannedItems >=0, "numberOfScannedItems was %s but expected non negative", numberOfScannedItems);
        checkNotNull(numberOfScannedItems);

        double price = 0;
        //calculate price per product group
        SpecialDeal deal = specialDeals.get(productCode);
        int amountOfItems = deal.getAmountOfItems();
        int quotient = numberOfScannedItems / amountOfItems;
        int remainder = numberOfScannedItems % amountOfItems;

        double specialPrice = deal.getPrice();
        Product product = productRepository.getProductByItsCode(productCode);
        price = quotient * specialPrice + remainder * product.getUnitPrice();

        return price;
    }

    private boolean specialDealExistsForTheProduct(String product) {
        return specialDeals.get(product) != null;
    }

    private double calculateStandardPriceOfAllItems(String product) {
        double price = 0;
        for (Product value : scannedProducts.get(product)) {
            price = value.unitPrice;
        }
        return price;
    }

    /**
     * @param product
     * @return a number of items of a given product in the shopping cart
     */
    private int getNumberOfItems(String product) {
        return scannedProducts.get(product).size();
    }

//    public List<SpecialDeal> getAppliedlDeals() {
//        return null;
//    }

    public int getNumberOfScannedItems() {
        return scannedProducts.size();
    }
}
