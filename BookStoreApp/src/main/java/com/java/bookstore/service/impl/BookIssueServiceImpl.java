package com.java.bookstore.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.bookstore.entities.Book;
import com.java.bookstore.entities.BookIssued;
import com.java.bookstore.repositories.BookIssueRepository;
import com.java.bookstore.service.BookIssueService;
import com.java.bookstore.service.BookService;

@Service
public class BookIssueServiceImpl implements BookIssueService {

	Logger logger = LoggerFactory.getLogger(BookIssueServiceImpl.class);

	@Autowired
	private BookService bookService;

	@Autowired
	private BookIssueRepository bookIssueRepository;

	@Override
	public List<BookIssued> getAll() {
		logger.info("Fetching all issued books from repository");
		return bookIssueRepository.getAll();
	}

	@Override
	public BookIssued get(final Long searchParam) {
		logger.info("Fetching an issued book: {} from repository", searchParam);
		return bookIssueRepository.search(searchParam);
	}

	@Override
	public BookIssued save(final BookIssued incommingItem) {
		logger.info("Saving a book issue in repository");
		return bookIssueRepository.create(incommingItem);
	}

	@Override
	public BookIssued update(final Long updateEntityId, final BookIssued incommingItem) {
		return null;
	}

	@Override
	public void delete(final long deleteEntityId) {
		logger.info("Deleting a book: {} issued from repository", deleteEntityId);
		bookIssueRepository.delete(deleteEntityId);
	}

	@Override
	public BookIssued issueBookById(final BookIssued bookIssued) {
		logger.info("Issuing a book: {} present in book store", bookIssued.getIsbnId());
		final Optional<Book> bookToIssue = bookService.getAll().stream()
				.filter(book -> book.getIsbn().equals(bookIssued.getIsbnId())).findFirst();
		if (bookToIssue.isPresent() && (bookToIssue.get().getNumberOfBooks() != ServiceConstants.INDEX_ZERO)) {
			bookIssued.setIssuedOn(new Date());
			save(bookIssued);
			bookToIssue.get().setNumberOfBooks(bookToIssue.get().getNumberOfBooks() - ServiceConstants.INDEX_ONE);
			// Reduce the number of books in inventory
			bookService.update(bookToIssue.get().getIsbn(), bookToIssue.get());
			logger.info("Book issue succesfull. {}", bookIssued.getIsbnId());
			return bookIssued;
		}
		return null;
	}

	@Override
	public BookIssued returnIssuedBook(final Long isbnId) {
		logger.info("Returning a book: {} availed earlier from bookStore.", isbnId);
		final BookIssued bookIssued = get(isbnId);
		if (bookIssued != null) {
			delete(isbnId);
			final Book issuedBook = bookService.get(isbnId);
			issuedBook.setNumberOfBooks(issuedBook.getNumberOfBooks() + ServiceConstants.INDEX_ONE);
			bookService.update(isbnId, issuedBook);
			bookIssued.setIsReturned(Boolean.TRUE);
			return bookIssued;
		}
		return null;
	}

}
