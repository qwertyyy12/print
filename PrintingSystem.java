/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.printingsystem;

/**
 *
 * @author bella
 */
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class PrintingSystem {
    private static final Logger logger = Logger.getLogger(PrintingSystem.class.getName());

    public static void main(String[] args) {
        logger.info("Starting Printing System...");
        LaserPrinter printer = new LaserPrinter("Printer1", 50, 50);

        ThreadGroup studentsGroup = new ThreadGroup("Students");
        ThreadGroup techniciansGroup = new ThreadGroup("Technicians");

        List<Document> student1Docs = Arrays.asList(
                new Document("Student 1", "Harry Potter", 20),
                new Document("Student 1", "Percy Jackson", 10)
        );

        List<Document> student2Docs = Arrays.asList(
                new Document("Student 2", "Divergent", 15),
                new Document("Student 2", "Malory Towers", 25)
        );

        List<Document> student3Docs = Arrays.asList(
                new Document("Student 3", "Charlie and the Chocolate Factory", 30)
        );

        List<Document> student4Docs = Arrays.asList(
                new Document("Student 4", "Moby Dick", 10),
                new Document("Student 4", "The Great Gatsby", 20)
        );

        Thread student1 = new Thread(studentsGroup, new Student(printer, student1Docs), "Student 1");
        Thread student2 = new Thread(studentsGroup, new Student(printer, student2Docs), "Student 2");
        Thread student3 = new Thread(studentsGroup, new Student(printer, student3Docs), "Student 3");
        Thread student4 = new Thread(studentsGroup, new Student(printer, student4Docs), "Student 4");

        PaperTechnician paperTech = new PaperTechnician(printer);
        TonerTechnician tonerTech = new TonerTechnician(printer);

        Thread paperTechnician = new Thread(techniciansGroup, paperTech, "Paper Technician");
        Thread tonerTechnician = new Thread(techniciansGroup, tonerTech, "Toner Technician");

        logger.info(".");
        student1.start();
        logger.info("..");
        student2.start();
        logger.info("...");
        student3.start();
        logger.info("....");
        student4.start();
        logger.info(".....");

        logger.info("Creating and starting technician threads...");
        paperTechnician.start();
        logger.info("Paper Technician has clocked in...");
        tonerTechnician.start();
        logger.info("Toner Technician has clocked in ...");

        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            paperTech.requestShutdown();
            tonerTech.requestShutdown();
            synchronized (printer) {
                printer.notifyAll();
            }
            paperTechnician.join();
            tonerTechnician.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logger.info("Printing system finished.");
        logger.info("Final printer status: " + printer);
    }
}
