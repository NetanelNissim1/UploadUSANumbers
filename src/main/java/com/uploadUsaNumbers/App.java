package com.uploadUsaNumbers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uploadUsaNumbers.filemanager.ManageCsvFiles;
import com.uploadUsaNumbers.filemanager.ManageProperties;
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
    private static String UploadUSANumbersExportToMobilePhoneNamesZip;
    private static String UploadUSANumbersExportToMobilePhoneEmailsZip;
    private static String UploadUSANumbersExportToPhoneNamesZip;
    private static String UploadUSANumbersExportToPhoneEmailsZip;

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
        getFiles(ManageProperties.getProperties("UploadUSANumbers"));

        Thread thread_ZIP_Phone_Name = new Thread(() -> {
            try {
                Utils.preZipFile(UploadUSANumbersExportToPhoneNamesZip, "PhoneNames.zip");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_Phone_Name.start();

        Thread thread_ZIP_Phone_Emails = new Thread(() -> {
            try {
                Utils.preZipFile(UploadUSANumbersExportToPhoneEmailsZip, "PhoneEmails.zip");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_Phone_Emails.start();

        Thread thread_ZIP_mobile_Phone_Name = new Thread(() -> {
            try {
                Utils.preZipFile(UploadUSANumbersExportToMobilePhoneNamesZip, "MobilePhoneNames.zip");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_mobile_Phone_Name.start();

        Thread thread_ZIP_mobile_Phone_Emails = new Thread(() -> {
            try {
                Utils.preZipFile(UploadUSANumbersExportToMobilePhoneEmailsZip, "MobilePhoneEmails.zip");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_mobile_Phone_Emails.start();

        try {
            thread_ZIP_Phone_Name.join();
            thread_ZIP_Phone_Emails.join();
            thread_ZIP_mobile_Phone_Emails.join();
            thread_ZIP_mobile_Phone_Name.join();
        } catch (InterruptedException ex) {

        }

    }

    private static void getFiles(String path) throws IOException {
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
