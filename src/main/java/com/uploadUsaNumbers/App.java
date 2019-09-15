package com.uploadUsaNumbers;

import java.io.IOException;
import com.uploadUsaNumbers.filemanager.ManageProperties;
import com.uploadUsaNumbers.processing.ProcessCanada;
import com.uploadUsaNumbers.processing.ProcessScraping;
import com.uploadUsaNumbers.processing.ProcessUSANumber;
import com.uploadUsaNumbers.processing.ProcessYellowPage;
import com.uploadUsaNumbers.processing.YellowPagesOutPut;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    private static boolean enableScraping;
    private static boolean enableUSANumbers;
    private static boolean enableCanada;
    private static boolean enableYellowPages;
    private static Boolean enableYellowPagesOutPut;

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws IOException {

        enableUSANumbers = Boolean.valueOf(ManageProperties.getProperties("enableUSANumbers"));
        enableScraping = Boolean.valueOf(ManageProperties.getProperties("enableScraping"));
        enableCanada = Boolean.valueOf(ManageProperties.getProperties("enableCanada"));
        enableYellowPages = Boolean.valueOf(ManageProperties.getProperties("enableYellowPages"));
        enableYellowPagesOutPut = Boolean.valueOf(ManageProperties.getProperties("enableYellowPagesOutPut"));
        Thread thread_USA_Numbers = null;
        if (enableUSANumbers) {
            thread_USA_Numbers = new Thread(() -> {
                try {
                    ProcessUSANumber.processUSA();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread_USA_Numbers.start();
        }
        Thread thread_Scraping = null;
        if (enableScraping) {
            thread_Scraping = new Thread(() -> {
                ProcessScraping.processScraping();

            });
            thread_Scraping.start();
        }

        Thread thread_Canada = null;
        if (enableCanada) {
            thread_Canada = new Thread(() -> {
                ProcessCanada.processCanada();

            });
            thread_Canada.start();
        }

        Thread thread_YellowPages = null;
        if (enableYellowPages) {
            thread_YellowPages = new Thread(() -> {
                ProcessYellowPage.processYellowPage();
            });
            thread_YellowPages.start();
        }
        Thread thread_YellowPages_output = null;
        if (enableYellowPagesOutPut) {
            thread_YellowPages_output = new Thread(() -> {
                YellowPagesOutPut.outPutYellowPage();
            });
            thread_YellowPages_output.start();
        }
        try {
            if (thread_USA_Numbers != null) {
                thread_USA_Numbers.join();
            }
            if (thread_Scraping != null) {
                thread_Scraping.join();
            }
            if (thread_Canada != null) {
                thread_Canada.join();
            }
            if (thread_YellowPages != null) {
                thread_YellowPages.join();
            }

            if (thread_YellowPages_output != null) {
                thread_YellowPages_output.join();
            }

        } catch (InterruptedException ex) {
        }
    }

}
