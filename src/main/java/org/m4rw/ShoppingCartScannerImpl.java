package org.m4rw;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.m4rw.products.DuplicateProductException;
import org.m4rw.products.Product;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Implementation of {@code ShoppingCartScanner} that uses an {@code ArrayListMultimap} to store
 * the product items for a given key (product). A {@link HashMap} associates each key with an
 * {@link ArrayList} of values.
 *
 * <p>This class is stateful and is not threadsafe when any concurrent operations update the
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
    DuplicateProductException.ProductRepository productRepository;


    ShoppingCartScannerImpl(HashMap<String, SpecialDeal> specialDeals, DuplicateProductException.ProductRepository productRepository){
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
            price += value.getUnitPrice();
        }
        return price;
    }

    /**
     * {@inheritDoc}
     *
     */
    public double getTotalPrice() {
        double totalPrice = 0;

        //iterate through scanned products
        for (String product : scannedProducts.keySet()) {

            //check if special deal for the product exists
            if (specialDealExistsForTheProduct(product)) {

                //how many items of this product we've got scanned
                int numberOfItems = getNumberOfItems(product);
                totalPrice += PriceCalculator.calculateTotalPricePerProduct(
                        productRepository.getProductByItsCode(product),
                        specialDeals.get(product),
                        numberOfItems);
            }
            else{//no special deal exists for the product
                totalPrice += PriceCalculator.calculateStandardPriceOfAllItems(scannedProducts.get(product));
            }
        }
        return totalPrice;
    }


    private boolean specialDealExistsForTheProduct(String product) {
        return specialDeals.get(product) != null;
    }


    /**
     * @param product
     * @return a number of items scanned so far
     */
    private int getNumberOfItems(String product) {
        return scannedProducts.get(product).size();
    }


    public int getNumberOfScannedItems() {
        return scannedProducts.size();
    }
}
