package com.uploadUsaNumbers.filemanager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.uploadUsaNumbers.processing.ProcessCanada;
import com.uploadUsaNumbers.processing.ProcessScraping;
import com.uploadUsaNumbers.processing.ProcessUSANumber;
import com.uploadUsaNumbers.utils.Utils;

public class ZipFiles {

    public static void createZipfilesUSANumbers() {

        Thread thread_ZIP_Phone_Name = new Thread(() -> {
            try {
                Utils.preZipFile(ProcessUSANumber.UploadUSANumbersExportZip,
                        ProcessUSANumber.UploadUSANumbersExportToPhoneNamesZip, "PhoneNames.zip");
            } catch (IOException ex) {
                Logger.getLogger(ZipFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_Phone_Name.start();

        Thread thread_ZIP_Phone_Emails = new Thread(() -> {
            try {
                Utils.preZipFile(ProcessUSANumber.UploadUSANumbersExportZip,
                        ProcessUSANumber.UploadUSANumbersExportToPhoneEmailsZip, "PhoneEmails.zip");
            } catch (IOException ex) {
                Logger.getLogger(ZipFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_Phone_Emails.start();

        Thread thread_ZIP_mobile_Phone_Name = new Thread(() -> {
            try {
                Utils.preZipFile(ProcessUSANumber.UploadUSANumbersExportZip,
                        ProcessUSANumber.UploadUSANumbersExportToMobilePhoneNamesZip, "MobilePhoneNames.zip");
            } catch (IOException ex) {
                Logger.getLogger(ZipFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_mobile_Phone_Name.start();

        Thread thread_ZIP_mobile_Phone_Emails = new Thread(() -> {
            try {
                Utils.preZipFile(ProcessUSANumber.UploadUSANumbersExportZip,
                        ProcessUSANumber.UploadUSANumbersExportToMobilePhoneEmailsZip, "MobilePhoneEmails.zip");
            } catch (IOException ex) {
                Logger.getLogger(ZipFiles.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void createZipfilesScraping() {

        Thread thread_ZIP_Phone_Name = new Thread(() -> {
            try {
                Utils.preZipFile(ProcessScraping.ScrapingFolderToZip, ProcessScraping.ZipScrapingPhoneNames,
                        "PhoneNames.zip");
            } catch (IOException ex) {
                Logger.getLogger(ZipFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_Phone_Name.start();

        try {
            thread_ZIP_Phone_Name.join();
        } catch (InterruptedException ex) {
        }
    }

    public static void createZipfilesCanada() {

        Thread thread_ZIP_Phone_Name = new Thread(() -> {
            try {
                Utils.preZipFile(ProcessCanada.CanadaFolderToZip, ProcessCanada.ZipCanadaPhoneNames, "PhoneNames.zip");
            } catch (IOException ex) {
                Logger.getLogger(ZipFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_Phone_Name.start();

        Thread thread_ZIP_Phone_Emails = new Thread(() -> {
            try {
                Utils.preZipFile(ProcessCanada.CanadaFolderToZip, ProcessCanada.ZipCanadaPhoneEmails,
                        "PhoneEmails.zip");
            } catch (IOException ex) {
                Logger.getLogger(ZipFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread_ZIP_Phone_Emails.start();

        try {
            thread_ZIP_Phone_Name.join();
            thread_ZIP_Phone_Emails.join();
        } catch (InterruptedException ex) {

        }
    }

}