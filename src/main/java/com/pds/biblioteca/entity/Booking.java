package com.pds.biblioteca.entity;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Booking extends AbstractFirestoreEntity {
    private String bookId;
    private String clientId;
    private Date bookingDate;
    private Date returnDate;
}
