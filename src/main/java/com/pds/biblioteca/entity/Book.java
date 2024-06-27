package com.pds.biblioteca.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Book extends AbstractFirestoreEntity {
    private String title;
    private String author;
    private String publisher;
    private Integer year;
    private String imageBook;
    private Integer stockQuantity;
}
