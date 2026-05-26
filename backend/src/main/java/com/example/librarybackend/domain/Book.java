package com.example.librarybackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book {
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "publisher")
  private String publisher;

  @Column(name = "price")
  private String price;

  @Column(name = "year")
  private String year;

  @Column(name = "status")
  private String status;

  @Column(name = "issue")
  private String issue;

  @Column(name = "due")
  private String due;

  @Column(name = "studentid")
  private String studentid;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getIssue() {
    return issue;
  }

  public void setIssue(String issue) {
    this.issue = issue;
  }

  public String getDue() {
    return due;
  }

  public void setDue(String due) {
    this.due = due;
  }

  public String getStudentid() {
    return studentid;
  }

  public void setStudentid(String studentid) {
    this.studentid = studentid;
  }
}

