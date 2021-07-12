package com.example.demo;

import com.example.demo.service.InvoiceGeneratorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
    private final InvoiceGeneratorService invoiceGeneratorService;

    public DemoApplication(InvoiceGeneratorService invoiceGeneratorService) {
        this.invoiceGeneratorService = invoiceGeneratorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/")
    public String sayHello(@RequestParam(value = "openSeat", defaultValue = "0") String openSeat,
                           @RequestParam(value = "cabinSeat", defaultValue = "0") String cabinSeat,
                           @RequestParam(value = "conferenceRoom", defaultValue = "0") String conferenceRoom,
                           @RequestParam(value = "meal", defaultValue = "0") String meal) throws Exception{
        try {
            return invoiceGeneratorService.generateInvoice(Integer.valueOf(openSeat), Integer.valueOf(cabinSeat),
                    Integer.valueOf(conferenceRoom), Integer.valueOf(meal));
        } catch (Exception e) {
            throw new Exception("Invalid data sent. ", e);
        }
    }
}
