package com.example.sushant_bansde;

public class GetLatLongResponse
{
    private String companyName;

    private String latitude;

    private String location;

    private String drive;

    private String longitude;

    public String getCompanyName ()
    {
        return companyName;
    }

    public void setCompanyName (String companyName)
    {
        this.companyName = companyName;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getDrive ()
    {
        return drive;
    }

    public void setDrive (String drive)
    {
        this.drive = drive;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [companyName = "+companyName+", latitude = "+latitude+", location = "+location+", drive = "+drive+", longitude = "+longitude+"]";
    }
}