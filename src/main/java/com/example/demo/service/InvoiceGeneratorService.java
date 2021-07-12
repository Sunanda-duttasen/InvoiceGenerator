package com.example.demo.service;

import com.example.demo.dto.BookingInvoice;
import com.example.demo.enums.BookingType;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import static com.example.demo.enums.BookingType.CONFERENCE_ROOM;

@Service
public class InvoiceGeneratorService {

    public String generateInvoice(Integer openSeat, Integer cabinSeat, Integer conferenceRoom, Integer meal) {
        BookingInvoice openSeatInvoice = new BookingInvoice(openSeat, BookingType.OPEN_SEAT.name());
        BookingInvoice cabinSeatInvoice = new BookingInvoice(cabinSeat, BookingType.CABIN_SEAT.name());
        BookingInvoice conferenceRoomInvoice = new BookingInvoice(conferenceRoom, CONFERENCE_ROOM.name());
        conferenceRoomInvoice.setFreeHoursForConferenceRoom(0);
        BookingInvoice mealInvoice = new BookingInvoice(meal, BookingType.MEAL.name());
        List<BookingInvoice> bookingInvoiceList = List.of(openSeatInvoice, cabinSeatInvoice, conferenceRoomInvoice, mealInvoice);
        prepareInvoice(bookingInvoiceList);

        Integer totalFreeHours = bookingInvoiceList.stream().mapToInt(BookingInvoice::getFreeHoursForConferenceRoom).sum();
        if(conferenceRoom > 0 && conferenceRoom > totalFreeHours) {
            conferenceRoomInvoice.setBookingNumber(conferenceRoom - totalFreeHours);
            CONFERENCE_ROOM.calculateAmount(conferenceRoomInvoice);
            conferenceRoomInvoice.setBookingNumber(conferenceRoom);
        } else {
            conferenceRoomInvoice.setAmount(0d);
            conferenceRoomInvoice.setGstAmt(0d);
        }

        List<String> response = new LinkedList<>();
        EnumSet.allOf(BookingType.class).forEach(type -> {
            BookingInvoice bookingInvoice = bookingInvoiceList.stream().filter(e -> type.name().equals(e.getBookingType())).findFirst().get();
            if(bookingInvoice.getBookingNumber() > 0) {
                response.add(bookingInvoice.getBookingNumber() + type.getInvoiceStatement(bookingInvoice.getBookingNumber()) + bookingInvoice.getAmount());
            }
        });
        response.add("Total: "+ bookingInvoiceList.stream().mapToDouble(BookingInvoice::getAmount).sum());
        response.add("Total GST: "+ bookingInvoiceList.stream().mapToDouble(BookingInvoice::getGstAmt).sum());
        return String.join("   ,  ", response);
    }

    private void prepareInvoice(List<BookingInvoice> bookingInvoiceList) {
        EnumSet.allOf(BookingType.class).forEach(type -> {
            if(!type.equals(CONFERENCE_ROOM)) {
                BookingInvoice bookingInvoice = bookingInvoiceList.stream().filter(e -> type.name().equals(e.getBookingType())).findFirst().get();
                type.calculateAmount(bookingInvoice);
            }
        });
    }
}
