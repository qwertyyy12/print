/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.printingsystem;

/**
 *
 * @author bella
 */
import java.util.logging.Logger;

public class PaperTechnician implements Runnable {
    private volatile boolean shutdownRequested = false;
    private final LaserPrinter printer;
    private static final Logger logger = Logger.getLogger(PaperTechnician.class.getName());

    public PaperTechnician(LaserPrinter printer) {
        this.printer = printer;
        logger.info("Paper Technician initialized.");
    }

    @Override
    public void run() {
        logger.info("Paper Technician started.");
        while (!shutdownRequested) {
            synchronized (printer) {
                if (printer.getPaperLevel() < ServicePrinter.PAPER_IN_PACK) {
                    printer.refillPaper(ServicePrinter.PAPER_IN_PACK);
                    logger.info("Paper Technician refilled paper.");
                } else {
                    try {
                        printer.wait(5000); // Wait for 5 seconds before checking again
                    } catch (InterruptedException e) {
                        if (shutdownRequested) {
                            logger.info("Paper Technician shutdown requested.");
                            Thread.currentThread().interrupt();
                        } else {
                            logger.warning("Paper Technician interrupted.");
                        }
                    }
                }
            }
        }
        logger.info("Paper Technician finished.");
    }

    public void requestShutdown() {
        shutdownRequested = true;
    }
}
