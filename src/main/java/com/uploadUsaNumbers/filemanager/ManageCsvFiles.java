package com.uploadUsaNumbers.filemanager;

import com.google.i18n.phonenumbers.Phonenumber;
import com.opencsv.bean.CsvToBeanBuilder;
import com.uploadUsaNumbers.Validator.CliValidator;
import com.uploadUsaNumbers.Validator.ValidCliWithCountryCode;
import com.uploadUsaNumbers.model.Phone_Name_Email;
import com.uploadUsaNumbers.model.YellowPage;
import com.uploadUsaNumbers.model.YellowPagesCSV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ManageCsvFiles {

    private final static String COMMA = ",";
    private final static String COUNTRYCODE = "US";
    private final static String COUNTRYCODECANADA = "CA";
    private final static Phonenumber.PhoneNumber buffer = new Phonenumber.PhoneNumber();
    private final static Phonenumber.PhoneNumber bufferScraping = new Phonenumber.PhoneNumber();
    private final static Phonenumber.PhoneNumber bufferCanada = new Phonenumber.PhoneNumber();
    private final static Phonenumber.PhoneNumber bufferYellowPages = new Phonenumber.PhoneNumber();

    public static List<Phone_Name_Email> processInputFilePhoneEmail(String inputFilePath) {
        List<Phone_Name_Email> inputList = new ArrayList<Phone_Name_Email>();
        try {
            File inputF = new File(inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
                inputList = br.lines().map(mapToItemPhoneEmail).collect(Collectors.toList());
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return inputList;
    }

    private static Function<String, Phone_Name_Email> mapToItemPhoneEmail = (line) -> {
        String[] p = line.split(COMMA);// a CSV has comma separated lines
        Phone_Name_Email item = new Phone_Name_Email();
        String addressId = p[0];
        addressId = addressId.replaceAll("\"", "");

        if (!addressId.equals("AddressID")) {
            String mobilePhone = p[43];
            mobilePhone = mobilePhone.replaceAll("\"", "");
            if (!mobilePhone.isEmpty()) {
                ValidCliWithCountryCode validCli = CliValidator.getValidE164CellularPhoneNumberAndCC(mobilePhone,
                        COUNTRYCODE, buffer, false);
                if (validCli != null) {
                    item.setMobilePhone(validCli.getCli());
                    item.setName(p[2].replaceAll("\"", "") + " " + p[4].replaceAll("\"", ""));
                    item.setEmail(p[412].replaceAll("\"", ""));
                }
            }

            String phone = p[42];
            phone = phone.replaceAll("\"", "");
            if (!phone.isEmpty()) {
                ValidCliWithCountryCode validCli = CliValidator.getValidE164CellularPhoneNumberAndCC(phone, COUNTRYCODE,
                        buffer, false);
                if (validCli != null) {
                    item.setPhone(validCli.getCli());
                    item.setEmail(p[412].replaceAll("\"", ""));

                }
            }
        }
        return item;
    };

    public static List<Phone_Name_Email> processInputFileScraping(String inputFilePath) {
        List<Phone_Name_Email> inputList = new ArrayList<>();
        try {
            File inputF = new File(inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
                inputList = br.lines().map(mapToItem).collect(Collectors.toList());
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return inputList;
    }

    private static Function<String, Phone_Name_Email> mapToItem = (line) -> {
        String[] p = line.split(COMMA);// a CSV has comma separated lines
        Phone_Name_Email item = new Phone_Name_Email();
        String phone = p[0];
        phone = phone.replaceAll("\"", "");
        if (!phone.isEmpty()) {
            ValidCliWithCountryCode validCli = CliValidator.getValidE164CellularPhoneNumberAndCC(phone, COUNTRYCODE,
                    bufferScraping, false);
            if (validCli != null) {
                item.setPhone(validCli.getCli());
                item.setName(p[1].replaceAll("\"", ""));
            }
        }
        return item;
    };

    public static List<Phone_Name_Email> processInputFileCanada(String inputFilePath) {
        List<Phone_Name_Email> inputList = new ArrayList<Phone_Name_Email>();
        try {
            File inputF = new File(inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
                inputList = br.lines().map(mapToItemCanada).collect(Collectors.toList());
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return inputList;
    }

    private static Function<String, Phone_Name_Email> mapToItemCanada = (line) -> {
        String[] p = line.split(COMMA);// a CSV has comma separated lines
        Phone_Name_Email item = new Phone_Name_Email();
        String addressId = p[0];
        addressId = addressId.replaceAll("\"", "");

        if (!addressId.equals("Name")) {
            try {
                String phone = p[7];
                phone = phone.replaceAll("\"", "");
                if (!phone.isEmpty()) {
                    ValidCliWithCountryCode validCli = CliValidator.getValidE164CellularPhoneNumberAndCC(phone,
                            COUNTRYCODECANADA, bufferCanada, false);
                    if (validCli != null) {
                        item.setPhone(validCli.getCli());
                        item.setEmail(p[8].replaceAll("\"", ""));
                        item.setName(p[0].replaceAll("\"", "") + " " + p[1].replaceAll("\"", ""));
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println(line.toString());
            }
        }
        return item;
    };

    public static List<YellowPage> processYellowPage(String inputFilePath) {
        List<YellowPage> inputList = new ArrayList<YellowPage>();
        try {
            File inputF = new File(inputFilePath);
            System.out.println("fileName: " + inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
                inputList = br.lines().map(mapToItemYellowPage).collect(Collectors.toList());
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return inputList;
    }

    private static Function<String, YellowPage> mapToItemYellowPage = (line) -> {
        String[] p = line.split(COMMA);// a CSV has comma separated lines

        YellowPage item = new YellowPage();

        String phone = p[6];
        phone = phone.replaceAll("\"", "");
        if (!phone.isEmpty()) {
            try {
                ValidCliWithCountryCode validCli = CliValidator.getValidE164CellularPhoneNumberAndCC(phone, COUNTRYCODE,
                        bufferYellowPages, false);
                if (validCli != null) {
                    item.setPhone(validCli.getCli());
                    item.setName(p[1].replaceAll("\"", ""));
                    item.setEmail(p[8].replaceAll("\"", ""));
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println(line.toString());
            }
        }

        return item;
    };

    public static List<YellowPagesCSV> processYellowPageOutPut(String inputFilePath) {
        try {
            List<YellowPagesCSV> beans = new CsvToBeanBuilder(new FileReader(inputFilePath))
                    .withType(YellowPagesCSV.class).build().parse();
            for (int i = 0; i < beans.size(); i++) {
                YellowPagesCSV item = beans.get(i);
                ValidCliWithCountryCode validCli = CliValidator.getValidE164CellularPhoneNumberAndCC(item.getPhone(),
                        COUNTRYCODE, bufferYellowPages, false);
                if (validCli != null) {

                    item.setPhone(validCli.getCli());
                    continue;
                }
                item.setPhone(null);
            }
            return beans;
        } catch (Exception ex) {
            System.out.println(inputFilePath);
            System.out.println(ex.toString());
            return null;
        }
    }
}
