package com.pds.biblioteca.service.firestore;

import com.google.cloud.firestore.DocumentReference;
import com.pds.biblioteca.entity.Book;
import com.pds.biblioteca.entity.Booking;
import fireconnect.service.AbstractFirestoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingFirestoreService extends AbstractFirestoreService<Booking> {

    private BookFirestoreService bookFirestoreService;

    @Override
    protected String getCollectionName() {
        return "bookings";
    }

    public Booking doBooking(Booking booking) {
        final Book book = this.bookFirestoreService.search(booking.getBookId());
        if (book.getStockQuantity() == 0)
            throw new RuntimeException("There's no stock");

        book.setStockQuantity(book.getStockQuantity() - 1);
        this.bookFirestoreService.update(book.getId(), book);

        final DocumentReference document = super.addDocument(booking);
        booking.setId(document.getId());
        return booking;
    }

    public List<Booking> getAll() {
        return super.getAllDocuments(Booking.class);
    }

    public void delete(String id) {
        super.deleteDocument(id);
    }

    public void update(String bookingId, Booking booking) {
        super.updateDocument(bookingId, booking);
    }

    public Booking search(String bookingId) {
        return super.findDocumentById(bookingId, Booking.class);
    }

}
