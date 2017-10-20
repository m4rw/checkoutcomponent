package org.m4rw.products;

import lombok.NonNull;

@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder

public class Product{
    @NonNull String productCode;
    @NonNull String productName;
    @NonNull double unitPrice;
}