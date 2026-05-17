package com.exemplo.auditor.domain.model;

import java.util.List;

public class DeadLetterMessage {
    private String zipCode;
    private Long customerId;
    private List<Item> orderItems;
    private String origin;
    private String occurredAt;


    public static class Item {
        private Long sku;
        private int amount;

        public int getAmount() { return amount; }
        public void setAmount(int amount) { this.amount = amount; }
    }

    public int getTotalAmount() {
        if (orderItems == null) return 0;
        return orderItems.stream().mapToInt(Item::getAmount).sum();
    }
}