package com.san.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Book {

	public Book() {
	}

	public Book(String name, String author, int year) {
		this.name = name;
		this.author = author;
		this.publishYear = year;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
	@SequenceGenerator(name = "book_seq_gen", sequenceName = "book_id_seq")
	public Integer id;
	public String name;
	public String author;
	public int publishYear;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", publishYear=" + publishYear + "]";
	}

}
