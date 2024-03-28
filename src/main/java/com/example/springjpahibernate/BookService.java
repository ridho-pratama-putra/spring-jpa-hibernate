package com.example.springjpahibernate;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    Logger logger = LoggerFactory.getLogger(getClass());
    
    private final BookRepository bookRepository;
    private final TransactionTemplate transactionTemplate;
    
    /*
     * this function only interact with the db
     * known leased connection when this function invoked is arround 20ms
     */
    public BookModel getBookById(@NonNull Long id) {
        Optional<BookModel> byId = bookRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    /*
     * 
     */
    public BookModel create(BookModel bookModel) {
        bookRepository.save(bookModel);
        return bookModel;
    }

    /*
     * this code to generate latency
     */
    public void callExternal() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * this function interact to db and generate latency before that
     * but leased connection result still around 20ms
     * thanks to spring.datasource.hikari.auto-commit=false
     */
    @Transactional
    public BookModel getBookByIdIncludeExternal(@NonNull Long id) {
        callExternal();
        Optional<BookModel> byId = bookRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    /*
     * this function interact to db and generate latency after that
     * but leased connection result IS MORE THAN 20ms like the others
     * even spring.datasource.hikari.auto-commit=false cant save it
     * this happen because doesnt know that connection maybe still required
     * SOLUTION :
     * 1. remove transactional ????? idk but it works
     * 2. using transactionalTemplate example in next funtion
     */
    @Transactional
    public BookModel getBookByIdIncludeExternalAfter(@NonNull Long id) {
        Optional<BookModel> byId = bookRepository.findById(id);
        callExternal();
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    /*
     * this function interact to db and generate latency after that
    */
    public BookModel getBookByIAfter(@NonNull Long id) {
        Optional<BookModel> result = transactionTemplate.execute(status -> {
            Optional<BookModel> res = bookRepository.findById(id);
            return res;
        });
        callExternal();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }
}
