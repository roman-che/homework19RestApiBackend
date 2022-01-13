package ru.inventorium.qa.restbackend.service;

import org.springframework.stereotype.Service;
import ru.inventorium.qa.restbackend.domain.BookInfo;
import ru.inventorium.qa.restbackend.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private List<BookInfo> books = new ArrayList<>();

    public BookInfo addBook(String bookName, String bookAuthor, long bookPrice, String bookIsbn) {
        BookInfo newBook = BookInfo.builder().bookName(bookName)
                .bookAuthor(bookAuthor)
                .bookPrice(bookPrice)
                .bookIsbn(bookIsbn)
                .build();
        books.add(newBook);
        return newBook;
    }

    public List<BookInfo> getAll() {
        return books;
    }

    public BookInfo findBookByName(String name) {
        for (BookInfo book : books) {
            if (book.getBookName().equals(name)) {
                return book;
            }
        }
        throw new NotFoundException("Book not found");
    }

    public List<BookInfo> findBookByAuthor(String author) {
        List<BookInfo> foundBooks = new ArrayList<>();
        for (BookInfo book : books) {
            if (book.getBookAuthor().equals(author)) {
                foundBooks.add(book);
            }
        }
        if (foundBooks.size() > 0)
            return foundBooks;
        else
            throw new NotFoundException("Author not found");
    }
}
