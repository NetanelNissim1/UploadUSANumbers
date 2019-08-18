package com.uploadUsaNumbers.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.uploadUsaNumbers.App;
import com.uploadUsaNumbers.model.Phone_Name_Email;

public class Utils {

    private static final String CSV_SEPARATOR = ",";

    public static void preZipFile( String fileNameToZip, String zipFileName ) throws IOException{

        FileOutputStream fos = new FileOutputStream( App.UploadUSANumbersExportZip + zipFileName );
        ZipOutputStream zipOut = new ZipOutputStream( fos );
        File fileToZip = new File( fileNameToZip );
        zipFile( fileToZip, fileToZip.getName(), zipOut );
        zipOut.close();
        fos.close();
    }

    private static void zipFile( File fileToZip, String fileName, ZipOutputStream zipOut ) throws IOException{
        if( fileToZip.isHidden() ){
            return;
        }
        if( fileToZip.isDirectory() ){
            if( fileName.endsWith( "/" ) ){
                zipOut.putNextEntry( new ZipEntry( fileName ) );
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry( new ZipEntry( fileName + "/" ) );
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for( File childFile : children ){
                zipFile( childFile, fileName + "/" + childFile.getName(), zipOut );
            }
            return;
        }
        FileInputStream fis = new FileInputStream( fileToZip );
        ZipEntry zipEntry = new ZipEntry( fileName );
        zipOut.putNextEntry( zipEntry );
        byte[] bytes = new byte[ 1024 ];
        int length;
        while( ( length = fis.read( bytes ) ) >= 0 ){
            zipOut.write( bytes, 0, length );
        }
        fis.close();
    }


    public static void writeToCSVPhoneEmails( List<Phone_Name_Email> numberEmails, String fileName ){
        try{
            try ( BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( App.UploadUSANumbersExportToPhoneEmails + fileName ), "UTF-8" ) ) ){
                for( Phone_Name_Email numberEmail : numberEmails ){
                    if( numberEmail.getPhone() != null && numberEmail.getEmail() != null ){
                        if( !numberEmail.getPhone().isEmpty() && !numberEmail.getEmail().isEmpty() ){
                            StringBuffer oneLine = new StringBuffer();
                            oneLine.append( numberEmail.getPhone() );
                            oneLine.append( CSV_SEPARATOR );
                            oneLine.append( numberEmail.getEmail() );
                            bw.write( oneLine.toString() );
                            bw.newLine();
                        }
                    }
                }   
                bw.flush();
            }
        } catch ( UnsupportedTemporalTypeException e ) {
        } catch ( FileNotFoundException e ) {
        } catch ( IOException e ) {
        }
    }

    public static void writeToCSVPhoneNames( List<Phone_Name_Email> numberEmails, String fileName ){
        try{
            try ( BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( App.UploadUSANumbersExportToPhoneNames + fileName ), "UTF-8" ) ) ){
                for( Phone_Name_Email numberEmail : numberEmails ){
                    if( numberEmail.getPhone() != null ){
                        if( !numberEmail.getPhone().isEmpty() ){
                            StringBuffer oneLine = new StringBuffer();
                            oneLine.append( numberEmail.getPhone() );
                            oneLine.append( CSV_SEPARATOR );
                            oneLine.append( numberEmail.getName() );
                            bw.write( oneLine.toString() );
                            bw.newLine();
                        }
                    }
                }
                bw.flush();
            }
        } catch ( UnsupportedEncodingException e ) {
        } catch ( FileNotFoundException e ) {
        } catch ( IOException e ) {
        }
    }

    public static void writeToCSVMobilePhoneEmails( List<Phone_Name_Email> numberEmails, String fileName ){
        try{
            try ( BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( App.UploadUSANumbersExportToMobilePhoneEmails + fileName ), "UTF-8" ) ) ){
                for( Phone_Name_Email numberEmail : numberEmails ){
                    if( numberEmail.getMobilePhone() != null && numberEmail.getEmail() != null ){
                        if( !numberEmail.getMobilePhone().isEmpty() && !numberEmail.getEmail().isEmpty() ){
                            StringBuffer oneLine = new StringBuffer();
                            oneLine.append( numberEmail.getMobilePhone() );
                            oneLine.append( CSV_SEPARATOR );
                            oneLine.append( numberEmail.getEmail() );
                            bw.write( oneLine.toString() );
                            bw.newLine();
                        }
                    }
                }
                bw.flush();
            }
        } catch ( UnsupportedEncodingException e ) {
        } catch ( FileNotFoundException e ) {
        } catch ( IOException e ) {
        }
    }

    public static void writeToCSVMobilePhoneNames( List<Phone_Name_Email> numberEmails, String fileName ){
        try{
            try ( BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( App.UploadUSANumbersExportToMobilePhoneNames + fileName ), "UTF-8" ) ) ){
                for( Phone_Name_Email numberEmail : numberEmails ){

                    if( numberEmail.getMobilePhone() != null  && numberEmail.getName()!= null ){
                        if( !numberEmail.getMobilePhone().isEmpty() && !numberEmail.getName().isEmpty()){
                            StringBuffer oneLine = new StringBuffer();
                            oneLine.append( numberEmail.getMobilePhone() );
                            oneLine.append( CSV_SEPARATOR );
                            oneLine.append( numberEmail.getName() );
                            bw.write( oneLine.toString() );
                            bw.newLine();
                        }
                    }
                }
                bw.flush();
            }
        } catch ( UnsupportedEncodingException e ) {
        } catch ( FileNotFoundException e ) {
        } catch ( IOException e ) {
        }
    }




}