package com.san;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.san.config.AppConfig;
import com.san.config.JPAConfig;

/**
 * Hello world!
 *
 */
public class App {
	public static AnnotationConfigApplicationContext appContext;

	public static void main(String[] args) {
		System.out.println("Initializing Application");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JPAConfig.class, AppConfig.class);
		appContext = ctx;
		System.out.println("Application Initialized");
		Main.main(ctx);
	}

}
