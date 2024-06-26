/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.printingsystem;

/**
 *
 * @author bella
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class LaserPrinter implements ServicePrinter {
    private final String printerId;
    private int paperLevel;
    private int tonerLevel;
    private int documentsPrinted;
    private final Lock lock;
    private static final Logger logger = Logger.getLogger(LaserPrinter.class.getName());

    public LaserPrinter(String printerId, int initialPaper, int initialToner) {
        this.printerId = printerId;
        this.paperLevel = initialPaper;
        this.tonerLevel = initialToner;
        this.documentsPrinted = 0;
        this.lock = new ReentrantLock();
        logger.info("Printer [" + printerId + "] initialized with " + initialPaper + " sheets of paper and " + initialToner + " units of toner.");
    }

    @Override
    public void printDocument(Document document) {
        lock.lock();
        try {
            while (paperLevel < document.getPages() || tonerLevel < document.getPages()) {
                logger.info(Thread.currentThread().getName() + " waiting for resources to be refilled.");
                synchronized (this) {
                    wait();
                }
            }
            logger.info("Attempting to print document: " + document);
            paperLevel -= document.getPages();
            tonerLevel -= document.getPages();
            documentsPrinted++;
            logger.info("Document printed: " + document + ". New Paper Level: " + paperLevel + ", New Toner Level: " + tonerLevel);
            synchronized (this) {
                notifyAll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void refillPaper(int noOfSheets) {
        lock.lock();
        try {
            int packsUsed = 0;
            while (paperLevel + noOfSheets <= MAX_PAPER) {
                paperLevel += noOfSheets;
                packsUsed++;
            }
            logger.info("Paper refilled using " + packsUsed + " packs. New Paper Level: " + paperLevel);
            synchronized (this) {
                notifyAll();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void replaceTonerCartridge() {
        lock.lock();
        try {
            if (tonerLevel < MIN_TONER) {
                tonerLevel = MAX_TONER;
                logger.info("Toner replaced. New Toner Level: " + tonerLevel);
                synchronized (this) {
                    notifyAll();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public int getPaperLevel() {
        lock.lock();
        try {
            return this.paperLevel;
        } finally {
            lock.unlock();
        }
    }

    public int getTonerLevel() {
        lock.lock();
        try {
            return this.tonerLevel;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "LaserPrinter [ID=" + printerId + ", paper level=" + paperLevel + ", toner level=" + tonerLevel + ", documents printed=" + documentsPrinted + "]";
    }
}
