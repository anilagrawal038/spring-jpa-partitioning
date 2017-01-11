package com.san;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;

import com.san.domain.Book;
import com.san.repo.BookRepository;
import com.san.service.BookService;

public class Main {

	static BookService bookService;
	static BookRepository productRepository;
	static ApplicationContext ctx;
	static DataSource dataSource;

	public static void main(ApplicationContext appContext) {
		ctx = appContext;
		bookService = appContext.getBean(BookService.class);
		productRepository = appContext.getBean(BookRepository.class);

		executeScriptFile("sql/1_drop_trig_book.sql");
		executeScriptFile("sql/2_func_book_trigger.sql");
		executeScriptFile("sql/3_trig_book.sql");
		System.out.println("Partition trigger created on book table");

		System.out.println("Goint to insert 1000 books");
		bookService.insertThousandsBooks();

		System.out.println("Searching first book published in 2011 ");
		List<Book> books = productRepository.findByPublishYear(2011);
		if (books != null && books.size() > 0) {
			System.out.println("First book published in 2011 is : " + books.get(0));
		}
	}

	private static void executeScriptFile(String fileName) {
		dataSource = ctx.getBean(DataSource.class);
		Statement stmt = null;
		String script = "";
		try {
			byte[] byteArr;
			System.out.println("Executing script file :" + fileName);
			// byteArr =
			// Files.readAllBytes(Paths.get(Main.class.getClassLoader().getResource("sql/func_book_trigger.sql").getPath()));
			byteArr = Files.readAllBytes(Paths.get(fileName));
			script = new String(byteArr);
			if(script.length()>100){
//				System.out.println("char at : >>>"+script.charAt(45)+"<<<");
//				script = script.replaceAll("\r\n", "");
			}
//			script = script.replaceAll("\n", "");
			System.out.println("\n\n" + script + "\n\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			stmt = dataSource.getConnection().createStatement();
			stmt.executeUpdate(script);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
	}
}
