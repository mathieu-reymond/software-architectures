package softarch.portal.db.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axis.message.MessageElement;

import librarysearch.soft.BookList;
import librarysearch.soft.LibrarySearchRequest;
import librarysearch.soft.LibrarySearchResponse;
import librarysearch.soft.LibrarySearchSOAPBindingStub;
import librarysearch.soft.LibrarySearchServiceLocator;
import softarch.portal.data.Book;
import softarch.portal.db.DatabaseException;
import softarch.portal.db.RecordFinderInterface;

public class WebService implements RecordFinderInterface {
	
	private LibrarySearchSOAPBindingStub service;
	
	private softarch.portal.data.Book createBook(be.ac.vub.soft.Book book) {
		String	author = book.getAuthor();
		long	isbn = book.getIsbn();
		int	pages = 0;
		Date	publicationDate = new GregorianCalendar(book.getYear(), 0, 1).getTime();
		String	publisher = book.getPublisher();
		String	review = "";
		String	summary = "";
		String	title = book.getTitle();
		Book b = new softarch.portal.data.Book(new Date(),author,isbn, pages, publicationDate, publisher, review, summary, title);
		return b;
	}
	
	private softarch.portal.data.Book createBook(be.library.Book book) {
		String	author = book.getAuthor();
		long	isbn = Long.parseLong(book.getIsbn(), 10);
		int	pages = 0;
		Date	publicationDate = book.getDate().getTime();
		String	publisher = book.getPublisher();
		String	review = "";
		String	summary = "";
		String	title = book.getTitle();
		Book b = new softarch.portal.data.Book(new Date(),author,isbn, pages, publicationDate, publisher, review, summary, title);
		return b;
		
	}
	 
	private List<Book> parse(BookList booklist, Date date) throws Exception {
		List<Book> list = new ArrayList<Book>();
		for(MessageElement element : booklist.get_any()) {
			if(element.getName().equals("searchBooksResponse")) {
				for (Iterator<MessageElement> iterator = element.getChildren().iterator(); iterator.hasNext();) {
					MessageElement el = iterator.next();
					be.ac.vub.soft.Book book = (be.ac.vub.soft.Book) el.getObjectValue(be.ac.vub.soft.Book.class);
					if(date != null) {
						boolean after = new GregorianCalendar(book.getYear(), 0, 1).getTime().compareTo(date) > 0;
						list.add(createBook(book));
					} else {
						list.add(createBook(book));
					}
				}
			} else {
				be.library.Book book = (be.library.Book) element.getObjectValue(be.library.Book.class);
				if(date == null || book.getDate().getTime().compareTo(date) > 0) {
					list.add(createBook(book));
				}
			}
		}
		return list;
	}

	private List<Book> parse(BookList booklist) throws Exception {
		return parse(booklist, null);
	}

	public WebService() throws ServiceException, MalformedURLException{
		LibrarySearchServiceLocator locator = new LibrarySearchServiceLocator();
		service = (LibrarySearchSOAPBindingStub) locator.getLibrarySearchServicePort(new URL("http://localhost:8080/ode/processes/LibrarySearchService"));
	}

	public List findRecords(String informationType, String queryString) throws DatabaseException {
		List results = new ArrayList();
		try {
			if(informationType.charAt(0)=='B') {
				LibrarySearchResponse response = service.process(new LibrarySearchRequest(queryString));
				results = parse(response.getBooks());
			}
		} catch (Exception e) {
			throw new DatabaseException("problem getting records " + e.getMessage());
		}
		return results;
	}

	public List findRecordsFrom(String informationType, Date date) throws DatabaseException {
		List results = new ArrayList();
		try {
			if(informationType.charAt(0)=='B') {
				LibrarySearchResponse response = service.process(new LibrarySearchRequest(""));
				results = parse(response.getBooks(), date);
			}
		} catch (Exception e) {
			throw new DatabaseException("problem getting records " + e.getMessage());
		}
		return results;
	}

}
