package com.uploadUsaNumbers.processing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.uploadUsaNumbers.filemanager.ManageCsvFiles;
import com.uploadUsaNumbers.filemanager.ManageProperties;
import com.uploadUsaNumbers.filemanager.ZipFiles;
import com.uploadUsaNumbers.model.Phone_Name_Email;
import com.uploadUsaNumbers.utils.Utils;

public class ProcessUSANumber {

    public static String UploadUSANumbersExportZip;
    private static String UploadUSANumbersExportToPhoneEmails;
    private static String UploadUSANumbersExportToPhoneNames;
    private static String UploadUSANumbersExportToMobilePhoneEmails;
    private static String UploadUSANumbersExportToMobilePhoneNames;
    public static String UploadUSANumbersExportToMobilePhoneNamesZip;
    public static String UploadUSANumbersExportToMobilePhoneEmailsZip;
    public static String UploadUSANumbersExportToPhoneNamesZip;
    public static String UploadUSANumbersExportToPhoneEmailsZip;

    public static void processUSA() throws IOException {

        String UploadUSANumbers = ManageProperties.getProperties("UploadUSANumbers");
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

        processUSANumbers(UploadUSANumbers);
        ZipFiles.createZipfilesUSANumbers();
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