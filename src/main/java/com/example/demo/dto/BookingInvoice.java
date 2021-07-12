package com.example.demo.dto;

public class BookingInvoice {
    private Integer bookingNumber;
    private Integer freeHoursForConferenceRoom;
    private String bookingType;
    private Double amount;
    private Double gstAmt;

    public BookingInvoice(Integer bookingNumber, String bookingType) {
        this.bookingNumber = bookingNumber;
        this.bookingType = bookingType;
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(Integer bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Integer getFreeHoursForConferenceRoom() {
        return freeHoursForConferenceRoom;
    }

    public void setFreeHoursForConferenceRoom(Integer freeHoursForConferenceRoom) {
        this.freeHoursForConferenceRoom = freeHoursForConferenceRoom;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getGstAmt() {
        return gstAmt;
    }

    public void setGstAmt(Double gstAmt) {
        this.gstAmt = gstAmt;
    }
}
