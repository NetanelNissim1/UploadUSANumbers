package com.uploadUsaNumbers.processing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.uploadUsaNumbers.filemanager.ManageCsvFiles;
import com.uploadUsaNumbers.filemanager.ManageProperties;
import com.uploadUsaNumbers.model.YellowPagesCSV;
import com.uploadUsaNumbers.utils.Utils;

public class YellowPagesOutPut {

    private static String YellowPageExportToPhoneNameOutPut;
    private static String YellowPageExportToPhoneEmailOutPut;
    private static String YellowPageExportToPhoneWebsiteOutPut;
    private static String YellowPageExportToPhoneAddressOutPut;
    private static String YellowPageExportToPhoneImagesOutPut;

    public static void outPutYellowPage() {

        String UploadYellowPagesOutPut = ManageProperties.getProperties("UploadYellowPagesOutPut");
        YellowPageExportToPhoneNameOutPut = ManageProperties.getProperties("YellowPageExportToPhoneNameOutPut");
        YellowPageExportToPhoneEmailOutPut = ManageProperties.getProperties("YellowPageExportToPhoneEmailOutPut");
        YellowPageExportToPhoneWebsiteOutPut = ManageProperties.getProperties("YellowPageExportToPhoneWebsiteOutPut");
        YellowPageExportToPhoneAddressOutPut = ManageProperties.getProperties("YellowPageExportToPhoneAddressOutPut");
        YellowPageExportToPhoneImagesOutPut = ManageProperties.getProperties("YellowPageExportToPhoneImagesOutPut");

        processYellowPages(UploadYellowPagesOutPut);

    }

    private static void processYellowPages(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        int count = 0;
        int num = 0;
        List<YellowPagesCSV> yellowPageList = new ArrayList<>();
        long countAllNumbers = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                yellowPageList.addAll(ManageCsvFiles.processYellowPageOutPut(file.getAbsolutePath()));
                num++;
                if (num == 790) {
                    count++;
                    num = 0;
                    String phone_names = "phone_names_" + count + ".csv";
                    System.out.println("phone_names: " + phone_names);
                    final List<YellowPagesCSV> PhoneNamesList = new ArrayList<YellowPagesCSV>(yellowPageList);
                    Thread thread_phone_name = new Thread(() -> {
                        Utils.writeToCSVYellowPagesPhoneNames(YellowPageExportToPhoneNameOutPut, PhoneNamesList,
                                phone_names);
                    });
                    thread_phone_name.start();
                    String phone_emails = "phone_emails_" + count + ".csv";
                    System.out.println("phone_emails: " + phone_emails);
                    final List<YellowPagesCSV> PhoneEmailList = new ArrayList<YellowPagesCSV>(yellowPageList);
                    Thread thread_phone_email = new Thread(() -> {
                        Utils.writeToCSVYellowPagesPhoneEmails(YellowPageExportToPhoneEmailOutPut, PhoneEmailList,
                                phone_emails);
                    });
                    thread_phone_email.start();

                    String phone_website = "phone_website_" + count + ".csv";
                    System.out.println("phone_website: " + phone_website);
                    final List<YellowPagesCSV> PhoneWebsite = new ArrayList<YellowPagesCSV>(yellowPageList);
                    Thread thread_phone_website = new Thread(() -> {
                        Utils.writeToCSVYellowPagesPhoneWebsite(YellowPageExportToPhoneWebsiteOutPut, PhoneWebsite,
                                phone_website);
                    });
                    thread_phone_website.start();

                    String phone_address = "phone_address_" + count + ".csv";
                    System.out.println("phone_address: " + phone_address);
                    final List<YellowPagesCSV> PhoneAddress = new ArrayList<YellowPagesCSV>(yellowPageList);
                    Thread thread_phone_address = new Thread(() -> {
                        Utils.writeToCSVYellowPagesPhoneAddress(YellowPageExportToPhoneAddressOutPut, PhoneAddress,
                                phone_address);
                    });
                    thread_phone_address.start();

                    String phone_images = "phone_images_" + count + ".csv";
                    System.out.println("phone_images: " + phone_images);
                    final List<YellowPagesCSV> PhoneImages = new ArrayList<YellowPagesCSV>(yellowPageList);
                    Thread thread_phone_images = new Thread(() -> {
                        Utils.writeToCSVYellowPagesPhoneImages(YellowPageExportToPhoneImagesOutPut, PhoneImages,
                                phone_images);
                    });
                    thread_phone_images.start();
                    try {
                        thread_phone_name.join();
                        thread_phone_email.join();
                        thread_phone_website.join();
                        thread_phone_address.join();
                        thread_phone_images.join();
                    } catch (InterruptedException ex) {
                    }
                    countAllNumbers += yellowPageList.size();
                    yellowPageList = new ArrayList<>();
                }

            }
        }
        System.out.println("countAllNumbers: " + countAllNumbers);

    }

}