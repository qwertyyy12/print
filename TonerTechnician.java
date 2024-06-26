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

public class TonerTechnician implements Runnable {
    private volatile boolean shutdownRequested = false;
    private final LaserPrinter printer;
    private static final Logger logger = Logger.getLogger(TonerTechnician.class.getName());

    public TonerTechnician(LaserPrinter printer) {
        this.printer = printer;
        logger.info("Toner Technician initialized.");
    }

    @Override
    public void run() {
        logger.info("Toner Technician started.");
        while (!shutdownRequested) {
            synchronized (printer) {
                if (printer.getTonerLevel() < ServicePrinter.MIN_TONER) {
                    printer.replaceTonerCartridge();
                    logger.info("Toner Technician replaced toner.");
                } else {
                    try {
                        printer.wait(5000); // Wait for 5 seconds before checking again
                    } catch (InterruptedException e) {
                        if (shutdownRequested) {
                            logger.info("Toner Technician shutdown requested.");
                            Thread.currentThread().interrupt();
                        } else {
                            logger.warning("Toner Technician interrupted.");
                        }
                    }
                }
            }
        }
        logger.info("Toner Technician finished.");
    }

    public void requestShutdown() {
        shutdownRequested = true;
    }
}
