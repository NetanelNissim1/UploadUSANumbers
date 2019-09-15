package com.uploadUsaNumbers.processing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.uploadUsaNumbers.filemanager.ManageCsvFiles;
import com.uploadUsaNumbers.filemanager.ManageProperties;
import com.uploadUsaNumbers.model.YellowPage;
import com.uploadUsaNumbers.utils.Utils;

public class ProcessYellowPage {

    private static String YellowPageExportToPhoneEmail;
    private static String YellowPageExportToPhoneName;

    public static void processYellowPage() {
        String UploadYellowPages = ManageProperties.getProperties("UploadYellowPages");
        YellowPageExportToPhoneName = ManageProperties.getProperties("YellowPageExportToPhoneName");
        YellowPageExportToPhoneEmail = ManageProperties.getProperties("YellowPageExportToPhoneEmail");
        // processYellowPages(UploadYellowPages);
    }

    /*
     * private static void processYellowPages(String path) { File folder = new
     * File(path); File[] listOfFiles = folder.listFiles(); int count = 0;
     * 
     * List<YellowPage> yellowPageList = new ArrayList<>(); long countAllNumbers =
     * 0; for (File file : listOfFiles) { if (file.isFile()) { yellowPageList =
     * ManageCsvFiles.processYellowPage(file.getAbsolutePath()); count++; String
     * phone_names = "phone_names_" + count + ".csv";
     * System.out.println("phone_names: " + phone_names); final List<YellowPage>
     * PhoneNamesList = new ArrayList<YellowPage>(yellowPageList); Thread
     * thread_phone_name = new Thread(() -> {
     * Utils.writeToCSVYellowPagesPhoneNames(YellowPageExportToPhoneName,
     * PhoneNamesList, phone_names); }); thread_phone_name.start(); String
     * phone_emails = "phone_emails_" + count + ".csv";
     * System.out.println("phone_emails: " + phone_emails); final List<YellowPage>
     * PhoneEmailList = new ArrayList<YellowPage>(yellowPageList); Thread
     * thread_phone_email = new Thread(() -> {
     * Utils.writeToCSVYellowPagesPhoneEmails(YellowPageExportToPhoneEmail,
     * PhoneEmailList, phone_emails); }); thread_phone_email.start(); try {
     * thread_phone_name.join(); thread_phone_email.join(); } catch
     * (InterruptedException ex) { } countAllNumbers += yellowPageList.size();
     * yellowPageList = new ArrayList<>();
     * 
     * } } System.out.println("countAllNumbers: " + countAllNumbers);
     * 
     * }
     */
}