package com.pds.biblioteca.controller;

import com.pds.biblioteca.entity.Book;
import com.pds.biblioteca.service.firestore.BookFirestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookFirestoreService bookFirestoreService;

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookFirestoreService.save(book));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(this.bookFirestoreService.getAll());
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        this.bookFirestoreService.delete(bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(@PathVariable String bookId, @RequestBody Book book) {
        this.bookFirestoreService.update(bookId, book);
        return ResponseEntity.status(HttpStatus.OK ).build(); // TODO ver o que retornar
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> searchBook(@PathVariable String bookId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.bookFirestoreService.search(bookId));
    }


}
