/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.printingsystem;

/**
 *
 * @author bella
 */
public interface ServicePrinter {
    int MAX_PAPER = 250;
    int MAX_TONER = 500;
    int MIN_TONER = 10;
    int PAPER_IN_PACK = 50;
    int PAGES_PER_TONER_CARTRIDGE = 500;

    void printDocument(Document document);
    void refillPaper(int noOfSheets);
    void replaceTonerCartridge();
}
