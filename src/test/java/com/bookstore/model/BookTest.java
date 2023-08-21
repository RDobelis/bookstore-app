package com.bookstore.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void testGettersAndSetters() {
        Book book = new Book();
        book.setId(1L);
        book.setName("Test Book");
        LocalDateTime dateAdded = LocalDateTime.now();
        book.setDateAdded(dateAdded);

        assertEquals(1L, book.getId());
        assertEquals("Test Book", book.getName());
        assertEquals(dateAdded, book.getDateAdded());
    }

    @Test
    public void testEqualsAndHashCode() {
        Book book1 = new Book("Test Book", LocalDateTime.now());
        Book book2 = new Book("Test Book", LocalDateTime.now());
        Book book3 = new Book("Different Book", LocalDateTime.now());

        assertEquals(book1, book1); // Reflexivity
        assertEquals(book1, book2); // Symmetry
        assertEquals(book2, book1); // Symmetry
        assertEquals(book1.hashCode(), book2.hashCode());

        assertNotEquals(book1, book3);
        assertNotEquals(book1.hashCode(), book3.hashCode());

        assertNotEquals(null, book1);
        assertNotEquals(book1, new Object());
    }

    @Test
    public void testToString() {
        Book book = new Book("Test Book", LocalDateTime.now());
        assertNotNull(book.toString());
        assertTrue(book.toString().contains("Test Book"));
    }

    @Test
    public void testEqualsWithNull() {
        Book book = new Book("Test Book", LocalDateTime.now());
        assertNotEquals(book, null);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        Book book = new Book("Test Book", LocalDateTime.now());
        assertNotEquals(book, new Object());
    }

    @Test
    public void testEqualsWithDifferentId() {
        Book book1 = new Book("Test Book", LocalDateTime.now());
        book1.setId(1L);
        Book book2 = new Book("Test Book", LocalDateTime.now());
        book2.setId(2L);
        assertNotEquals(book1, book2);
    }

    @Test
    public void testEqualsWithDifferentDateAdded() {
        Book book1 = new Book("Test Book", LocalDateTime.now());
        Book book2 = new Book("Test Book", LocalDateTime.now().plusDays(1));
        assertNotEquals(book1, book2);
    }
}
