package com.example.demo.enums;

import com.example.demo.dto.BookingInvoice;
import static com.example.demo.dto.BookingCharges.df;

public enum BookingType {
    OPEN_SEAT {
        @Override
        public void calculateAmount(BookingInvoice bookingInvoice) {
            bookingInvoice.setFreeHoursForConferenceRoom(getTotalFreeHoursForConferenceRoom(5, bookingInvoice.getBookingNumber()));
            calculateInvoice(bookingInvoice, 5000d, 18d);
        }
        @Override
        public String getInvoiceStatement(Integer bookingNumber) {
            return (bookingNumber == 1) ? " Open Seat: " : " Open Seats: ";
        }
    },
    CABIN_SEAT {
        @Override
        public void calculateAmount(BookingInvoice bookingInvoice) {
            bookingInvoice.setFreeHoursForConferenceRoom(getTotalFreeHoursForConferenceRoom(10, bookingInvoice.getBookingNumber()));
            calculateInvoice(bookingInvoice, 10000d, 18d);
        }
        @Override
        public String getInvoiceStatement(Integer bookingNumber) {
            return (bookingNumber == 1) ? " Cabin Seat: " : " Cabin Seats: ";
        }
    },
    CONFERENCE_ROOM {
        @Override
        public void calculateAmount(BookingInvoice bookingInvoice) {
            calculateInvoice(bookingInvoice, 200d, 18d);
        }
        @Override
        public String getInvoiceStatement(Integer bookingNumber) {
            return " hours of Conference Room Usage: ";
        }
    },
    MEAL {
        @Override
        public void calculateAmount(BookingInvoice bookingInvoice) {
            bookingInvoice.setFreeHoursForConferenceRoom(0);
            calculateInvoice(bookingInvoice, 100d, 12d);
        }
        @Override
        public String getInvoiceStatement(Integer bookingNumber) {
            return (bookingNumber == 1) ? " meal: " : " meals: ";
        }
    };

    private static Integer getTotalFreeHoursForConferenceRoom(Integer freeHrPerSeat, Integer bookedSeats) {
        return freeHrPerSeat * bookedSeats;
    }

    private static void calculateInvoice(BookingInvoice bookingInvoice, Double amountPerSeat, Double gst) {
        Double amount = Double.parseDouble(df.format(amountPerSeat * bookingInvoice.getBookingNumber()));
        Double gstAmt = Double.parseDouble(df.format((gst / 100) * amount));
        bookingInvoice.setAmount(Double.parseDouble(df.format(gstAmt + amount)));
        bookingInvoice.setGstAmt(gstAmt);
    }

    public abstract void calculateAmount(BookingInvoice bookingInvoice);

    public abstract String getInvoiceStatement(Integer bookingNumber);
}
