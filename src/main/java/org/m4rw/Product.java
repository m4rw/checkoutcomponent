package org.m4rw;

import lombok.NonNull;

@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder

class Product{
    @NonNull String productCode;
    @NonNull String productName;
    @NonNull double unitPrice;
}