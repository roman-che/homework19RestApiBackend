package ru.inventorium.qa.restbackend.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.inventorium.qa.restbackend.domain.BookInfo;
import ru.inventorium.qa.restbackend.service.BookService;

import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    public BookService bookService;

    @GetMapping("/books/findByName/{bookName}")
    @ApiOperation("Найти книгу по названию")
    public BookInfo findBookByName(@PathVariable String bookName) {
        return bookService.findBookByName(bookName);
    }

    @GetMapping("/books/findByAuthor/{authorName}")
    @ApiOperation("Найти книгу по названию")
    public List<BookInfo> findBookByAuthor(@PathVariable String authorName) {
        return bookService.findBookByAuthor(authorName);
    }


    @PostMapping("/books/newBook")
    @ApiOperation("Создать новую книгу")
    public BookInfo addBook(@RequestBody BookInfo newBook) {
        return bookService.addBook(newBook.getBookName(), newBook.getBookAuthor(),
                newBook.getBookPrice(), newBook.getBookIsbn());
    }


    @GetMapping("books/getAll")
    @ApiOperation("Получение списка всех книг")
    public List<BookInfo> getAllBooksInfo() {
        return bookService.getAll();
    }

}
