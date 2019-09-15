package com.uploadUsaNumbers.processing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.uploadUsaNumbers.filemanager.ManageCsvFiles;
import com.uploadUsaNumbers.filemanager.ManageProperties;
import com.uploadUsaNumbers.filemanager.ZipFiles;
import com.uploadUsaNumbers.model.Phone_Name_Email;
import com.uploadUsaNumbers.utils.Utils;

public class ProcessCanada {

    private static String ExportCanadaToPhoneEmails;
    private static String ExportCanadaToPhoneNames;
    public static String ZipCanadaPhoneNames;
    public static String ZipCanadaPhoneEmails;
    public static String CanadaFolderToZip;

    public static void processCanada() {
        String UploadCanada = ManageProperties.getProperties("UploadCanada");
        ExportCanadaToPhoneEmails = ManageProperties.getProperties("ExportCanadaToPhoneEmails");
        ExportCanadaToPhoneNames = ManageProperties.getProperties("ExportCanadaToPhoneNames");

        ZipCanadaPhoneNames = ManageProperties.getProperties("ZipCanadaPhoneNames");
        ZipCanadaPhoneEmails = ManageProperties.getProperties("ZipCanadaPhoneEmails");
        CanadaFolderToZip = ManageProperties.getProperties("CanadaFolderToZip");

        processCanadaFiles(UploadCanada);
        ZipFiles.createZipfilesCanada();
    }

    private static void processCanadaFiles(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        int count = 0;
        List<Phone_Name_Email> canadaList = new ArrayList<>();
        long countAllNumbers = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {

                canadaList = ManageCsvFiles.processInputFileCanada(file.getAbsolutePath());
                count++;
                String phone_emails = "phone_emails_" + count + ".csv";
                System.out.println("phone_emails: " + phone_emails);
                final List<Phone_Name_Email> PhoneEmailsList = new ArrayList<Phone_Name_Email>(canadaList);
                Thread thread_P_E = new Thread(() -> {
                    Utils.writeToCSVPhoneEmails(ExportCanadaToPhoneEmails, PhoneEmailsList, phone_emails);
                });
                thread_P_E.start();

                String phone_names = "phone_names_" + count + ".csv";
                System.out.println("phone_names: " + phone_names);
                final List<Phone_Name_Email> PhoneNamesList = new ArrayList<Phone_Name_Email>(canadaList);
                Thread thread_P_N = new Thread(() -> {
                    Utils.writeToCSVPhoneNames(ExportCanadaToPhoneNames, PhoneNamesList, phone_names);
                });
                thread_P_N.start();

                try {
                    thread_P_E.join();
                    thread_P_N.join();
                } catch (InterruptedException ex) {
                }
                countAllNumbers += canadaList.size();
            }
            System.out.println("countAllNumbers: " + countAllNumbers);
        }
    }
}