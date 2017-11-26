package com.example.java.gettingstarted;

import java.math.BigDecimal;

/**
 * Created For RIDMIS Web service
 */
public class Sample {
   String item;
   Integer quantity;
   BigDecimal unitprice;

    public Sample(String item, Integer quantity, BigDecimal unitprice) {
        this.item = item;
        this.quantity = quantity;
        this.unitprice = unitprice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
