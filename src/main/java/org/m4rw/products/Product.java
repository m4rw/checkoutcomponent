package org.m4rw.products;

import lombok.NonNull;

import javax.annotation.Nonnegative;

@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder

/**
 * Represents a product
 */
public class Product{
    @NonNull String productCode;
    @NonNull String productName;
    @NonNull @Nonnegative double unitPrice;
}