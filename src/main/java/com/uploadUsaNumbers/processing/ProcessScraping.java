package com.uploadUsaNumbers.processing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.uploadUsaNumbers.filemanager.ManageCsvFiles;
import com.uploadUsaNumbers.filemanager.ManageProperties;
import com.uploadUsaNumbers.filemanager.ZipFiles;
import com.uploadUsaNumbers.model.Phone_Name_Email;
import com.uploadUsaNumbers.utils.Utils;

public class ProcessScraping {

    private static String ExportScrapingToPhoneNames;
    public static String ZipScrapingPhoneNames;
    public static String ScrapingFolderToZip;

    public static void processScraping() {

        String UploadScraping = ManageProperties.getProperties("UploadScraping");

        ExportScrapingToPhoneNames = ManageProperties.getProperties("ExportScrapingToPhoneNames");
        ZipScrapingPhoneNames = ManageProperties.getProperties("ZipScrapingPhoneNames");
        ScrapingFolderToZip = ManageProperties.getProperties("ScrapingFolderToZip");

        processScrapingFiles(UploadScraping);
        ZipFiles.createZipfilesScraping();
    }

    private static void processScrapingFiles(String path) {

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

}