package com.example.springjpahibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BookService bookService;
    
    @GetMapping("book")
    public ResponseEntity<BookModel> getBookById(@NonNull @RequestParam Long id) {
        BookModel bookModel = bookService.getBookById(id);
        return new ResponseEntity<>(bookModel, HttpStatus.OK);
    }

    @PostMapping("book")
    public ResponseEntity<BookModel> createBook(@RequestBody BookModel bookModel) {
        bookService.create(bookModel);
        return new ResponseEntity<>(bookModel, HttpStatus.OK);
    }

    @GetMapping("book/outside/external")
    public ResponseEntity<BookModel> getBookByIdExternal(@NonNull @RequestParam Long id) {
        BookModel bookModel = bookService.getBookById(id);
        bookService.callExternal();
        return new ResponseEntity<>(bookModel, HttpStatus.OK);
    }

    @GetMapping("book/inside/external")
    public ResponseEntity<BookModel> getBookByIdIncludeExternal(@NonNull @RequestParam Long id) {
        BookModel bookModel = bookService.getBookByIdIncludeExternal(id);
        return new ResponseEntity<>(bookModel, HttpStatus.OK);
    }

    @GetMapping("book/inside/external/after")
    public ResponseEntity<BookModel> getBookByIdIncludeExternalAfter(@NonNull @RequestParam Long id) {
        BookModel bookModel = bookService.getBookByIdIncludeExternalAfter(id);
        return new ResponseEntity<>(bookModel, HttpStatus.OK);
    }

    @GetMapping("book/inside/external/after/manual")
    public ResponseEntity<BookModel> getBookByIdIAfter(@NonNull @RequestParam Long id) {
        BookModel bookModel = bookService.getBookByIAfter(id);
        return new ResponseEntity<>(bookModel, HttpStatus.OK);
    }
}
