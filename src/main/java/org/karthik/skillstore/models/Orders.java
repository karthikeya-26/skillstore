package org.karthik.skillstore.models;

import java.math.BigDecimal;

public class Orders {
    private int orderId;
    private long customerUserId;
    private long sellerUserId;
    private int sellerSkillId;
    private BigDecimal pricePerHour;
    private BigDecimal totalPrice;
    private long paymentId;
    private long createdAt;
    private Long updatedAt; // Nullable field

    // Constructors
    public Orders() {}

    public Orders(int orderId, long customerUserId, long sellerUserId, int sellerSkillId,
                 BigDecimal pricePerHour, BigDecimal totalPrice, long paymentId,
                 long createdAt, Long updatedAt) {
        this.orderId = orderId;
        this.customerUserId = customerUserId;
        this.sellerUserId = sellerUserId;
        this.sellerSkillId = sellerSkillId;
        this.pricePerHour = pricePerHour;
        this.totalPrice = totalPrice;
        this.paymentId = paymentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(long customerUserId) {
        this.customerUserId = customerUserId;
    }

    public long getSellerUserId() {
        return sellerUserId;
    }

    public void setSellerUserId(long sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    public int getSellerSkillId() {
        return sellerSkillId;
    }

    public void setSellerSkillId(int sellerSkillId) {
        this.sellerSkillId = sellerSkillId;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerUserId=" + customerUserId +
                ", sellerUserId=" + sellerUserId +
                ", sellerSkillId=" + sellerSkillId +
                ", pricePerHour=" + pricePerHour +
                ", totalPrice=" + totalPrice +
                ", paymentId=" + paymentId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
