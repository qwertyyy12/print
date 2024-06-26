/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.printingsystem;

/**
 *
 * @author bella
 */
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Student implements Runnable {
    private final LaserPrinter printer;
    private final List<Document> documentsToPrint;
    private final Random random = new Random();
    private static final Logger logger = Logger.getLogger(Student.class.getName());

    public Student(LaserPrinter printer, List<Document> documentsToPrint) {
        this.printer = printer;
        this.documentsToPrint = documentsToPrint;
        logger.info("Initialized student thread for printing documents.");
    }

    @Override
    public void run() {
        int totalPagesPrinted = 0;
        for (Document document : documentsToPrint) {
            synchronized (printer) {
                while (printer.getPaperLevel() < document.getPages() || printer.getTonerLevel() < document.getPages()) {
                    try {
                        logger.info(Thread.currentThread().getName() + " waiting for resources to be refilled.");
                        printer.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        logger.warning(Thread.currentThread().getName() + " interrupted while waiting for resources.");
                        return;
                    }
                }
                printer.printDocument(document);
                totalPagesPrinted += document.getPages();
            }
            try {
                Thread.sleep(random.nextInt(3000)); // Simulate random delay between print requests
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warning(Thread.currentThread().getName() + " interrupted during sleep.");
            }
        }
        logger.info(Thread.currentThread().getName() + " finished printing all documents. Total pages printed: " + totalPagesPrinted);
    }
}
