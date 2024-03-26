package com.example.springjpahibernate;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    
    @GetMapping("book")
    public ResponseEntity<BookModel> getBookById(@NonNull @RequestParam Long id) {
        BookModel bookModel = bookService.getBookById(id);
        return new ResponseEntity<>(bookModel, HttpStatus.OK);
    }
    
}
