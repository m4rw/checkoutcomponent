package org.m4rw.products;

import java.util.*;

/**
 * In-memory implementation of {@code ProductRepository} that uses an {@code HashMap} to store
 * the product items for a given key (unique product code).
 *
 *  * <p>This class is stateful and is not threadsafe when any concurrent operations update the respective
 * map. Concurrent read operations will work correctly.
 *
 */
public class ProductInMemoryRepositoryImpl implements ProductRepository {

    private HashMap<String,Product> products = new HashMap<String, Product>();


    public void addNewProduct(String productCode, Product product) throws DuplicateProductException {
        if (products.containsKey(productCode))
            throw new DuplicateProductException("Product with this product code already exists");
        products.put(productCode, product);
    }

    public void updateProduct(String productCode, Product product) {
        if (! products.containsKey(productCode))
            throw new NoSuchElementException("Product with this product code does not found");
         products.put(productCode, product);

    }

    public List<Product> getListOfAllProducts() {
        List<Product> productList = new ArrayList<Product>(products.values());
        return productList;
    }

    public Product getProductByItsCode(String productCode) {
        if (! products.containsKey(productCode))
            throw new NoSuchElementException("Product with this product code is not found");
        return products.get(productCode);
    }
}
