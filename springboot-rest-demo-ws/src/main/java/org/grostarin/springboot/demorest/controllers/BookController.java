package org.grostarin.springboot.demorest.controllers;

import org.grostarin.springboot.demorest.annotations.LogExecutionTime;
import org.grostarin.springboot.demorest.domain.Book;
import org.grostarin.springboot.demorest.dto.BookSearch;
import org.grostarin.springboot.demorest.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookServices bookServices;

    @GetMapping
    @LogExecutionTime
    public Iterable<Book> findAll(@Valid BookSearch bookSearchDTO) {
        return bookServices.findAll(bookSearchDTO);
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable long id) {
        return bookServices.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookServices.create(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bookServices.delete(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable long id) {
        return bookServices.updateBook(book, id);
    }
}
