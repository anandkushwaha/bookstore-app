package com.java.bookstore.rest.api.bookstore;

public final class RestConstants {

	public static final String BOOK_ENDPOINT = "/book";
	public static final String ENDPOINT_SEPARATOR = "/";
	public static final String APPLICATION_PRODUCER_TYPE = "application/json";
	public static final String BY_ISBNID = ENDPOINT_SEPARATOR + "{isbnId}";
	public static final String BOOK_ISSUE = BOOK_ENDPOINT + ENDPOINT_SEPARATOR + "issue";
}
