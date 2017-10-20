package org.m4rw;

import org.springframework.dao.DuplicateKeyException;
import java.util.List;
import java.util.NoSuchElementException;

public interface ProductRepository {
    /**
     * Associates the specified productCode with the specified Product in as key-Value pairs (Map). If the map previously contained
     * a mapping for the product, the class will throw DuplicateKeyException.
     *
     * @param productCode an unique code to represent a product
     * @param product
     * @throws DuplicateKeyException when the collection already contains the specified productCode
     */
    void addNewProduct(String productCode, Product product) throws DuplicateKeyException;

    /**
     *
     * @param productCode
     * @param product
     * @throws NoSuchElementException when then map does not contain the specified productCode
     */
     void updateProduct(String productCode, Product product) throws NoSuchElementException;

    /**
     *
     * @return list of all products from the collection
     */
    List<Product> getListOfAllProducts();

    /**
     *
     * @param productCode an unique code to represent a product
     * @return product
     * @throws NoSuchElementException when the map does not contain the specified productCode
     */
    Product getProductByItsCode(String productCode) throws NoSuchElementException;
}
