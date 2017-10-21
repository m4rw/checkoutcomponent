package org.m4rw;

import lombok.NonNull;

import javax.annotation.Nonnegative;

@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder
public class SpecialDeal {
    @NonNull  @Nonnegative
    final int amountOfItems;
    @NonNull  @Nonnegative
    final double price;
}
