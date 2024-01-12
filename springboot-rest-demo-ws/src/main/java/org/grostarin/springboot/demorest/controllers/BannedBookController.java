package org.grostarin.springboot.demorest.controllers;


import org.grostarin.springboot.demorest.annotations.LogExecutionTime;
import org.grostarin.springboot.demorest.domain.BannedBook;
import org.grostarin.springboot.demorest.dto.BookSearch;
import org.grostarin.springboot.demorest.services.BannedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/banned-books")
public class BannedBookController {

    @Autowired
    private BannedBookService bannedBookService;

    @GetMapping
    @LogExecutionTime
    public Iterable<BannedBook> findAll(@Valid BookSearch bookSearchDTO) {
        return bannedBookService.findAll(bookSearchDTO);
    }

    @GetMapping("/{id}")
    public BannedBook findOne(@PathVariable long id) {
        return bannedBookService.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BannedBook create(@RequestBody BannedBook bannedBook) {
        return bannedBookService.create(bannedBook);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bannedBookService.delete(id);
    }

    @PutMapping("/{id}")
    public BannedBook updateBannedBook(@RequestBody BannedBook bannedBook, @PathVariable long id) {
        return bannedBookService.updateBannedBook(bannedBook, id);
    }
}
