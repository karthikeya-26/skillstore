package org.karthik.skillstore.models;

public class PaymentMethods {


    private Long paymentMethodId;
    private String paymentMethodName;
    private Long userId;
    private String paymentUpiId;

    public PaymentMethods() {
        super();
    }

    public PaymentMethods(Long paymentMethodId, String paymentMethodName, Long userId, String paymentUpiId) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.userId = userId;
        this.paymentUpiId = paymentUpiId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPaymentUpiId() {
        return paymentUpiId;
    }

    public void setPaymentUpiId(String paymentUpiId) {
        this.paymentUpiId = paymentUpiId;
    }

}
