package model;

import java.util.Date;

public class Payment {
    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentStatus;
    private Date paymentTime;

    public Payment() {}

    public Payment(int paymentId, int bookingId, double amount, String paymentStatus, Date paymentTime) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
    }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public Date getPaymentTime() { return paymentTime; }
    public void setPaymentTime(Date paymentTime) { this.paymentTime = paymentTime; }

    @Override
    public String toString() { return "Payment#"+paymentId+" booking="+bookingId+" "+paymentStatus; }
}
