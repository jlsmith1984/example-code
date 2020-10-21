package com.myexample.code.domain;

import java.util.Comparator;

public class Currency {

    private final int denomination;
    private final int count;

    public Currency(Integer denomination, Integer count) {
        this.denomination = denomination;
        this.count = count;
    }

    final static Comparator<Currency> byDenomination = Comparator.comparingInt(Currency::getDenomination);

    public static Comparator<Currency> getDenominationComparator() {
        return byDenomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public int getCount() {
        return count;
    }
}
