package com.uploadUsaNumbers.Validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


/**
 * CliValidator
 */
public class CliValidator{

    private CliValidator(){
        
    }
    
    private static final PhoneNumberUtil PHONE_UTIL = PhoneNumberUtil.getInstance();

    public static boolean isValidCellularNumber( final String cli, final Phonenumber.PhoneNumber buffer ){
        if( cli == null ){
            return false;
        }
        return getValidE164CellularPhoneNumberAndCC( cli, buffer, true ) != null;
    }

    public static boolean isValidPhoneNumber( String cli, final Phonenumber.PhoneNumber buffer ){
        if( cli == null ){
            return false;
        }
        return cli.equals( getValidE164PhoneNumber( cli, buffer ) );
    }


    public static ValidCliWithCountryCode getValidE164CellularPhoneNumberAndCC( String cli, final Phonenumber.PhoneNumber buffer, boolean exactMatch ){
        return getValidE164CellularPhoneNumberAndCC( cli, null, buffer, exactMatch );
    }


    public static ValidCliWithCountryCode getValidE164CellularPhoneNumberAndCC( String cli, String defaultRegion, final Phonenumber.PhoneNumber buffer, boolean exactMatch ){
        ValidCliWithCountryCode res = null;
        if( cli == null ){
            return null;
        }
        Phonenumber.PhoneNumber workingBuffer;
        if( buffer == null ){
            workingBuffer = new Phonenumber.PhoneNumber();
        } else {
            workingBuffer = buffer;
        }
        try{
            String cliToParse;
            if( defaultRegion == null && cli.isEmpty() == false && cli.charAt( 0 ) != '+' ){
                cliToParse = "+" + cli;
            } else {
                cliToParse = cli;
            }

            PHONE_UTIL.parse( cliToParse, defaultRegion, workingBuffer );
            
            if( PHONE_UTIL.isValidNumber( workingBuffer ) ){
                String cc = PHONE_UTIL.getRegionCodeForNumber( workingBuffer );
                PhoneNumberUtil.PhoneNumberType type = PHONE_UTIL.getNumberType( workingBuffer );
                if( type == PhoneNumberUtil.PhoneNumberType.MOBILE || type == PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE
                        || ( type == PhoneNumberUtil.PhoneNumberType.FIXED_LINE && "MX".equals( cc ) ) ){
                    String phone = subtractLeadingPlus( PHONE_UTIL.format( workingBuffer, PhoneNumberUtil.PhoneNumberFormat.E164 ) );
                    if( exactMatch && false == subtractLeadingPlus( cli ).equals( phone ) ){
                        return null;
                    }

                    res = new ValidCliWithCountryCode( phone, cc );
                }
            }
        } catch ( NumberParseException e ) {

        } finally {
            if( buffer != null ){
                buffer.clear();
            }
        }

        return res;
    }

    public static String getValidE164PhoneNumber( String possiblyCli, final Phonenumber.PhoneNumber buffer ){
        if( possiblyCli == null || possiblyCli.isEmpty() ){
            return null;
        }

        Phonenumber.PhoneNumber workingBuffer;
        String resultCli = null;
        if( buffer == null ){
            workingBuffer = new Phonenumber.PhoneNumber();
        } else {
            workingBuffer = buffer;
        }
        try{
            PHONE_UTIL.parse( "+" + possiblyCli, null, workingBuffer );
            if( PHONE_UTIL.isValidNumber( workingBuffer ) ){
                resultCli = PHONE_UTIL.format( workingBuffer, PhoneNumberUtil.PhoneNumberFormat.E164 );
            }
        } catch ( NumberParseException e ) {
        } finally {
            if( buffer != null ){
                buffer.clear();
            }
        }

        return subtractLeadingPlus( resultCli );
    }

    public static String subtractLeadingPlus( String possiblyCliWithLeadingPlus ){
        if( possiblyCliWithLeadingPlus != null
                && possiblyCliWithLeadingPlus.isEmpty() == false
                && possiblyCliWithLeadingPlus.charAt( 0 ) == '+' ){
            return possiblyCliWithLeadingPlus.substring( 1 );
        } else {
            return possiblyCliWithLeadingPlus;
        }
    }

    public static ValidCliWithCountryCode getValidE164PhoneNumberAndCC( String possiblyCli, final Phonenumber.PhoneNumber buffer ){
        Phonenumber.PhoneNumber workingBuffer;
        String resultCli = null;
        String cc = null;
        if( buffer == null ){
            workingBuffer = new Phonenumber.PhoneNumber();
        } else {
            workingBuffer = buffer;
        }
        try{

            PHONE_UTIL.parse( "+" + possiblyCli, null, workingBuffer );
            if( PHONE_UTIL.isValidNumber( workingBuffer ) ){
                resultCli = subtractLeadingPlus( PHONE_UTIL.format( workingBuffer, PhoneNumberUtil.PhoneNumberFormat.E164 ) );
                cc = PHONE_UTIL.getRegionCodeForNumber( workingBuffer );
            }
        } catch ( NumberParseException e ) {
        } finally {
            if( buffer != null ){
                buffer.clear();
            }
        }

        ValidCliWithCountryCode res = null;
        if( resultCli != null && resultCli.isEmpty() == false && cc != null && cc.isEmpty() == false ){
            res = new ValidCliWithCountryCode( resultCli, cc );
        }
        return res;
    }

    public static boolean containsNumbersOnly( String probablyNumbers ){
        if( probablyNumbers == null || probablyNumbers.length() < 1 ){
            return false;
        }
        for( int i = 0; i < probablyNumbers.length(); i++ ){
            if( Character.isDigit( probablyNumbers.charAt( i ) ) == false ){
                return false;
            }
        }
        return true;
    }
}
