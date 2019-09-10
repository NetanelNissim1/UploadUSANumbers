package com.uploadUsaNumbers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.uploadUsaNumbers.filemanager.ManageCsvFiles;
import com.uploadUsaNumbers.filemanager.ManageProperties;
import com.uploadUsaNumbers.filemanager.ZipFiles;
import com.uploadUsaNumbers.model.Phone_Name_Email;
import com.uploadUsaNumbers.model.YellowPage;
import com.uploadUsaNumbers.utils.Utils;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    public static String UploadUSANumbersExportZip;
    private static String UploadUSANumbersExportToPhoneEmails;
    private static String UploadUSANumbersExportToPhoneNames;
    private static String UploadUSANumbersExportToMobilePhoneEmails;
    private static String UploadUSANumbersExportToMobilePhoneNames;
    public static String UploadUSANumbersExportToMobilePhoneNamesZip;
    public static String UploadUSANumbersExportToMobilePhoneEmailsZip;
    public static String UploadUSANumbersExportToPhoneNamesZip;
    public static String UploadUSANumbersExportToPhoneEmailsZip;
    private static String UploadScraping;
    private static String ExportScrapingToPhoneNames;
    public static String ZipScrapingPhoneNames;
    public static String ScrapingFolderToZip;
    private static boolean enableScraping;
    private static boolean enableUSANumbers;
    private static boolean enableCanada;
    private static String UploadCanada;
    private static String ExportCanadaToPhoneEmails;
    private static String ExportCanadaToPhoneNames;
    public static String ZipCanadaPhoneNames;
    public static String ZipCanadaPhoneEmails;
    public static String CanadaFolderToZip;
    private static List<Phone_Name_Email> numbersList;
    private static boolean enableYellowPages;
    private static String UploadYellowPages;
    private static String YellowPageExportToPhoneName;
    private static String YellowPageExportToPhoneEmail;

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws IOException {

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

        UploadScraping = ManageProperties.getProperties("UploadScraping");
        ExportScrapingToPhoneNames = ManageProperties.getProperties("ExportScrapingToPhoneNames");
        ZipScrapingPhoneNames = ManageProperties.getProperties("ZipScrapingPhoneNames");
        ScrapingFolderToZip = ManageProperties.getProperties("ScrapingFolderToZip");
        enableUSANumbers = Boolean.valueOf(ManageProperties.getProperties("enableUSANumbers"));
        enableScraping = Boolean.valueOf(ManageProperties.getProperties("enableScraping"));

        enableCanada = Boolean.valueOf(ManageProperties.getProperties("enableCanada"));
        UploadCanada = ManageProperties.getProperties("UploadCanada");
        ExportCanadaToPhoneEmails = ManageProperties.getProperties("ExportCanadaToPhoneEmails");
        ExportCanadaToPhoneNames = ManageProperties.getProperties("ExportCanadaToPhoneNames");
        ZipCanadaPhoneNames = ManageProperties.getProperties("ZipCanadaPhoneNames");
        ZipCanadaPhoneEmails = ManageProperties.getProperties("ZipCanadaPhoneEmails");
        CanadaFolderToZip = ManageProperties.getProperties("CanadaFolderToZip");

        enableYellowPages = Boolean.valueOf(ManageProperties.getProperties("enableYellowPages"));
        UploadYellowPages = ManageProperties.getProperties("UploadYellowPages");
        YellowPageExportToPhoneName = ManageProperties.getProperties("YellowPageExportToPhoneName");
        YellowPageExportToPhoneEmail = ManageProperties.getProperties("YellowPageExportToPhoneEmail");
        Thread thread_USA_Numbers = null;
        if (enableUSANumbers) {
            thread_USA_Numbers = new Thread(() -> {
                try {
                    processUSANumbers(ManageProperties.getProperties("UploadUSANumbers"));
                    ZipFiles.createZipfilesUSANumbers();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread_USA_Numbers.start();
        }
        Thread thread_Scraping = null;
        if (enableScraping) {
            thread_Scraping = new Thread(() -> {
                processFiles(UploadScraping);
                ZipFiles.createZipfilesScraping();
            });
            thread_Scraping.start();
        }

        Thread thread_Canada = null;
        if (enableCanada) {
            thread_Canada = new Thread(() -> {
                processCanadaFiles(UploadCanada);
                ZipFiles.createZipfilesCanada();
            });
            thread_Canada.start();
        }

        Thread thread_YellowPages = null;
        if (enableYellowPages) {
            thread_YellowPages = new Thread(() -> {
                processYellowPages(UploadYellowPages);
                // ZipFiles.createZipfilesYellowPages();
            });
            thread_YellowPages.start();
        }

        try {
            if (thread_Canada != null) {
                thread_Canada.join();
            }
            if (thread_USA_Numbers != null) {
                thread_USA_Numbers.join();
            }
            if (thread_Scraping != null) {
                thread_Scraping.join();
            }
            if (thread_Canada != null) {
                thread_Canada.join();
            }
        } catch (InterruptedException ex) {
        }
    }

    private static void processYellowPages(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        int count = 0;

        List<YellowPage> yellowPageList = new ArrayList<>();
        long countAllNumbers = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                yellowPageList = ManageCsvFiles.processYellowPage(file.getAbsolutePath());
                count++;
                String phone_names = "phone_names_" + count + ".csv";
                System.out.println("phone_names: " + phone_names);
                final List<YellowPage> PhoneNamesList = new ArrayList<YellowPage>(yellowPageList);
                Thread thread_phone_name = new Thread(() -> {
                    Utils.writeToCSVYellowPagesPhoneNames(YellowPageExportToPhoneName, PhoneNamesList, phone_names);
                });
                thread_phone_name.start();
                String phone_emails = "phone_emails_" + count + ".csv";
                System.out.println("phone_emails: " + phone_emails);
                final List<YellowPage> PhoneEmailList = new ArrayList<YellowPage>(yellowPageList);
                Thread thread_phone_email = new Thread(() -> {
                    Utils.writeToCSVYellowPagesPhoneEmails(YellowPageExportToPhoneEmail, PhoneEmailList, phone_emails);
                });
                thread_phone_email.start();
                try {
                    thread_phone_name.join();
                    thread_phone_email.join();
                } catch (InterruptedException ex) {
                }
                countAllNumbers += yellowPageList.size();
                yellowPageList = new ArrayList<>();

            }
        }
        System.out.println("countAllNumbers: " + countAllNumbers);

    }

    private static void processCanadaFiles(String path) {
        // Method sumInstanceMethod = ManageCsvFiles.class.getMethod("mapToItemCanada");
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        int count = 0;
        // List<Phone_Name_Email> numbersList = new ArrayList<>();
        long countAllNumbers = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {

                numbersList = ManageCsvFiles.processInputFileCanada(file.getAbsolutePath());
                count++;
                String phone_emails = "phone_emails_" + count + ".csv";
                System.out.println("phone_emails: " + phone_emails);
                final List<Phone_Name_Email> PhoneEmailsList = new ArrayList<Phone_Name_Email>(numbersList);
                Thread thread_P_E = new Thread(() -> {
                    Utils.writeToCSVPhoneEmails(ExportCanadaToPhoneEmails, PhoneEmailsList, phone_emails);
                });
                thread_P_E.start();

                String phone_names = "phone_names_" + count + ".csv";
                System.out.println("phone_names: " + phone_names);
                final List<Phone_Name_Email> PhoneNamesList = new ArrayList<Phone_Name_Email>(numbersList);
                Thread thread_P_N = new Thread(() -> {
                    Utils.writeToCSVPhoneNames(ExportCanadaToPhoneNames, PhoneNamesList, phone_names);
                });
                thread_P_N.start();

                try {
                    thread_P_E.join();
                    thread_P_N.join();
                } catch (InterruptedException ex) {
                }
                countAllNumbers += numbersList.size();
            }
            System.out.println("countAllNumbers: " + countAllNumbers);
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
                String phone_names = "phoneNamesScraping_" + count + ".csv";
                System.out.println("phoneNamesScraping: " + phone_names);
                Thread thread_P_N = new Thread(() -> {
                    Utils.writeToCSVPhoneNames(ExportScrapingToPhoneNames, numbersList, phone_names);
                });
                thread_P_N.start();
                try {
                    thread_P_N.join();
                } catch (InterruptedException ex) {
                }
                count++;
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
                        Utils.writeToCSVPhoneEmails(UploadUSANumbersExportToMobilePhoneEmails, numberEmails,
                                phone_emails);
                    });
                    thread_P_E.start();

                    String phone_names = "phone_names_" + count + ".csv";
                    System.out.println("phone_names: " + phone_names);
                    Thread thread_P_N = new Thread(() -> {
                        Utils.writeToCSVPhoneNames(UploadUSANumbersExportToPhoneNames, numberEmails, phone_names);
                    });
                    thread_P_N.start();

                    String mobile_phone_names = "mobile_phone_names_" + count + ".csv";
                    System.out.println("mobile_phone_names: " + mobile_phone_names);
                    Thread thread_M_P_N = new Thread(() -> {
                        Utils.writeToCSVMobilePhoneNames(UploadUSANumbersExportToMobilePhoneNames, numberEmails,
                                mobile_phone_names);
                    });
                    thread_M_P_N.start();

                    String mobile_phone_emails = "mobile_phone_emails_" + count + ".csv";
                    System.out.println("phone_emails: " + mobile_phone_emails);
                    Thread thread_M_P_E = new Thread(() -> {
                        Utils.writeToCSVMobilePhoneEmails(UploadUSANumbersExportToPhoneEmails, numberEmails,
                                mobile_phone_emails);
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
