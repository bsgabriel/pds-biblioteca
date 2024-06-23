package com.pds.biblioteca.service.firestore;

import com.google.cloud.firestore.DocumentReference;
import com.pds.biblioteca.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookFirestoreService extends AbstractFirestoreService<Book> {

    public Book save(Book book) {
        final DocumentReference document = super.addDocument(book);
        book.setId(document.getId());
        return book;
    }

    public List<Book> getAll() {
        return super.getAllDocuments(Book.class);
    }

    public void delete(String id) {
        super.deleteDocument(id);
    }

    public void update(String bookId, Book book) {
        super.updateDocument(bookId, book);
    }

    public Book search(String bookId) {
        return super.findDocumentById(bookId, Book.class);
    }

    @Override
    protected String getCollectionName() {
        return "books";
    }
}
