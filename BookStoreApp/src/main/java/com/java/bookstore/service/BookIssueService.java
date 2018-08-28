package com.java.bookstore.service;

import com.java.bookstore.entities.BookIssued;

public interface BookIssueService extends BaseService<BookIssued> {

	BookIssued issueBookById(final BookIssued bookIssued);

	BookIssued returnIssuedBook(Long isbnId);

}
