package org.m4rw;

import java.util.NoSuchElementException;

public interface ShoppingCartScanner {

    /**
     * Adds a product by storing a key-value pair in a multimap and increases size of the map by 1.
     *
     * It's intended to be a multimap type of collection and duplicates of the same type of product are possible
     *
     * @param productCode  unique identifier of product,
     * @param item product object
     * @return number of items scanned so far. Duplicates of the same type of product are allowed
     * @throws NoSuchElementException when relevant ProductRepository does not contain product with productCode
     */
    int scanProduct(String productCode, Product item);

    /**
     * Adds a product by storing a key-value pair in a multimap and increases size of the map by 1.
     *
     * It's intended to be a multimap type of collection and duplicates of the same type of product are possible
     *
     * @param productCode  unique identifier of product,
     * @return number of items scanned so far. Duplicates of the same type of product are allowed
     * @throws NoSuchElementException when relevant ProductRepository does not contain product with productCode
     */
    int scanProduct(String productCode);

    /**
     * Removes a single product. If multiple product fit this contract, which one is removed is unspecified as it's
     * irrelevant for the scanner.
     *
     * @param productCode  unique identifier of product,
     * @param item product object
     * */
    boolean removeProduct(String productCode, Product item);

    /**
     * @return Standard total price of all scanned products. Some products are multi-priced;
     * Special deals not taken into account
     */
    double getStandardTotalPrice();


    /**
     * @return Total price of all scanned products. Special deals (multipriced products) taken into account
     */
    double getTotalPrice();

//    /**
//     * @return List of special deals that were applied to the basket during checkout
//     */
//    List<SpecialDeal> getAppliedlDeals();

    /**
     * @returns number of items that were scanned so far.
     */
    int getNumberOfScannedItems();



}