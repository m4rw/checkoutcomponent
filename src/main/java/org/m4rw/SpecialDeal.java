package org.m4rw;

import lombok.NonNull;

import javax.annotation.Nonnegative;

/**
 * Some products are multi priced: "buy n of them, and they’ll cost you x cents for the whole batch".
 * This class represents such special deals
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder
public class SpecialDeal {
    /**
     * Number of items in batch
     */
    @NonNull  @Nonnegative
    final int numberOfItems;
    /**
     * Price of batch
     */
    @NonNull  @Nonnegative
    final double price;
}
