package com.uploadUsaNumbers.Validator;


import java.util.Objects;

public class ValidCliWithCountryCode{

    private final String cli;
    private final String countryCode;
    private final CountryCode countryCodeEnum;

    public ValidCliWithCountryCode( String cli, String countryCode ){
        this.cli = cli;
        this.countryCode = countryCode.toLowerCase();
        CountryCode convertedCc = CountryCode.getByCode( countryCode, false );
        if( convertedCc == null ){
            this.countryCodeEnum = CountryCode.UNDEFINED;
        } else {
            this.countryCodeEnum = convertedCc;
        }
    }

    public String getCli(){
        return cli;
    }

    public String getCountryCode(){
        return countryCode;
    }
    
    public CountryCode getCountryCodeEnum(){
        return countryCodeEnum;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 67 * hash + Objects.hashCode( this.cli );
        hash = 67 * hash + Objects.hashCode( this.countryCode );
        return hash;
    }

    @Override
    public boolean equals( Object obj ){
        if( this == obj ){
            return true;
        }
        if( obj == null ){
            return false;
        }
        if( getClass() != obj.getClass() ){
            return false;
        }
        final ValidCliWithCountryCode other = (ValidCliWithCountryCode)obj;
        if( !Objects.equals( this.cli, other.cli ) ){
            return false;
        }
        if( !Objects.equals( this.countryCode, other.countryCode ) ){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "cli=" + cli + ", countryCode=" + countryCode;
    }
}
