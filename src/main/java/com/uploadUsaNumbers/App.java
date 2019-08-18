package com.uploadUsaNumbers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uploadUsaNumbers.filemanager.ManageCsvFiles;
import com.uploadUsaNumbers.filemanager.ManageProperties;
import com.uploadUsaNumbers.filemanager.ZipFiles;
import com.uploadUsaNumbers.model.Phone_Name_Email;
import com.uploadUsaNumbers.utils.Utils;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    public static String UploadUSANumbersExportZip;
    public static String UploadUSANumbersExportToPhoneEmails;
    public static String UploadUSANumbersExportToPhoneNames;
    public static String UploadUSANumbersExportToMobilePhoneEmails;
    public static String UploadUSANumbersExportToMobilePhoneNames;
    public static String UploadUSANumbersExportToMobilePhoneNamesZip;
    public static String UploadUSANumbersExportToMobilePhoneEmailsZip;
    public static String UploadUSANumbersExportToPhoneNamesZip;
    public static String UploadUSANumbersExportToPhoneEmailsZip;
    private static String UploadScraping;
    public static String ExportScrapingToPhoneNames;
    public static String ZipScrapingPhoneNames;
    public static String ScrapingFolderToZip;

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws IOException {

        System.out.println("test");
        UploadUSANumbersExportToPhoneEmails = ManageProperties.getProperties("UploadUSANumbersExportToPhoneEmails");
        UploadUSANumbersExportToPhoneNames = ManageProperties.getProperties("UploadUSANumbersExportToPhoneNames");

        UploadUSANumbersExportZip = ManageProperties.getProperties("UploadUSANumbersExportZip");
        UploadUSANumbersExportToMobilePhoneEmails = ManageProperties
                .getProperties("UploadUSANumbersExportToMobilePhoneEmails");
        UploadUSANumbersExportToMobilePhoneNames = ManageProperties
                .getProperties("UploadUSANumbersExportToMobilePhoneNames");

        UploadUSANumbersExportToPhoneNamesZip = ManageProperties.getProperties("UploadUSANumbersExportToPhoneNamesZip");
        UploadUSANumbersExportToPhoneEmailsZip = ManageProperties
                .getProperties("UploadUSANumbersExportToPhoneEmailsZip");
        UploadUSANumbersExportToMobilePhoneEmailsZip = ManageProperties
                .getProperties("UploadUSANumbersExportToMobilePhoneEmailsZip");
        UploadUSANumbersExportToMobilePhoneNamesZip = ManageProperties
                .getProperties("UploadUSANumbersExportToMobilePhoneNamesZip");

        Thread thread_USA_Numbers = new Thread(() -> {
            try {
                processUSANumbers(ManageProperties.getProperties("UploadUSANumbers"));
                ZipFiles.createZipfilesUSANumbers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread_USA_Numbers.start();
        try {
            thread_USA_Numbers.join();
        } catch (InterruptedException ex) {
        }

        UploadScraping = ManageProperties.getProperties("UploadScraping");
        ExportScrapingToPhoneNames = ManageProperties.getProperties("ExportScrapingToPhoneNames");
        ZipScrapingPhoneNames = ManageProperties.getProperties("ZipScrapingPhoneNames");
        ScrapingFolderToZip = ManageProperties.getProperties("ScrapingFolderToZip");

        Thread thread_Scraping = new Thread(() -> {
            processFiles(UploadScraping);
            ZipFiles.createZipfilesScraping();
        });
        thread_Scraping.start();
        try {
            thread_Scraping.join();
        } catch (InterruptedException ex) {
        }
    }

    private static void processFiles(String path) {

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        int count = 1;
        List<Phone_Name_Email> numbersList = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                numbersList.addAll(ManageCsvFiles.processInputFileScraping(file.getAbsolutePath()));
                String phone_names = "phone_names_" + count + ".csv";
                System.out.println("phone_names: " + phone_names);
                Thread thread_P_N = new Thread(() -> {
                    Utils.writeToCSVPhoneNames(numbersList, phone_names);
                });
                thread_P_N.start();
                try {
                    thread_P_N.join();
                } catch (InterruptedException ex) {
                }
                numbersList.clear();
            }
        }

    }

    private static void processUSANumbers(String path) throws IOException {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        int count = 0;
        int num = 0;
        int countListfiles = listOfFiles.length;
        List<Phone_Name_Email> numberEmails = new ArrayList<>();
        long countAllNumbers = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                numberEmails.addAll(ManageCsvFiles.processInputFilePhoneEmail(file.getAbsolutePath()));
                num++;

                if (num == 5 || (countListfiles == 2 && num == 2)) {
                    count++;
                    countListfiles -= num;
                    num = 0;
                    System.out.println("Countfiles: " + countListfiles);

                    String phone_emails = "phone_emails_" + count + ".csv";
                    System.out.println("phone_emails: " + phone_emails);
                    Thread thread_P_E = new Thread(() -> {
                        Utils.writeToCSVPhoneEmails(numberEmails, phone_emails);
                    });
                    thread_P_E.start();

                    String phone_names = "phone_names_" + count + ".csv";
                    System.out.println("phone_names: " + phone_names);
                    Thread thread_P_N = new Thread(() -> {
                        Utils.writeToCSVPhoneNames(numberEmails, phone_names);
                    });
                    thread_P_N.start();

                    String mobile_phone_names = "mobile_phone_names_" + count + ".csv";
                    System.out.println("mobile_phone_names: " + mobile_phone_names);
                    Thread thread_M_P_N = new Thread(() -> {
                        Utils.writeToCSVMobilePhoneNames(numberEmails, mobile_phone_names);
                    });
                    thread_M_P_N.start();

                    String mobile_phone_emails = "mobile_phone_emails_" + count + ".csv";
                    System.out.println("phone_emails: " + mobile_phone_emails);
                    Thread thread_M_P_E = new Thread(() -> {
                        Utils.writeToCSVMobilePhoneEmails(numberEmails, mobile_phone_emails);
                    });
                    thread_M_P_E.start();

                    try {
                        thread_P_E.join();
                        thread_P_N.join();
                        thread_M_P_N.join();
                        thread_M_P_E.join();
                    } catch (InterruptedException ex) {
                    }
                    countAllNumbers += numberEmails.size();
                    numberEmails.clear();
                }
            }
        }
        System.out.println("countAllNumbers: " + countAllNumbers);

    }
}
