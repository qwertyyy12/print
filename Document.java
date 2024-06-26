/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.printingsystem;

/**
 *
 * @author bella
 */
public class Document {
    private final String studentName;
    private final String title;
    private final int pages;

    public Document(String studentName, String title, int pages) {
        this.studentName = studentName;
        this.title = title;
        this.pages = pages;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "Document{" +
                "studentName='" + studentName + '\'' +
                ", title='" + title + '\'' +
                ", pages=" + pages +
                '}';
    }
}
