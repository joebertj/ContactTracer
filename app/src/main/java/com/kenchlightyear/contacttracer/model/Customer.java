package com.kenchlightyear.contacttracer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable {
    String first;
    String last;
    String address;
    String barangay;
    String city;
    String province;
    Long number;
    String email;
    Float temperature;
    String timestamp;
    String name;
    String establishmentId;
    Double latitude;
    String longitude;

    public Customer() {
    }

    @Override
    public String toString() {
        return first + " " + last + " 0" + number.toString() + " " + email;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(String establishmentId) {
        this.establishmentId = establishmentId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.first);
        dest.writeString(this.last);
        dest.writeString(this.address);
        dest.writeString(this.barangay);
        dest.writeString(this.city);
        dest.writeString(this.province);
        dest.writeValue(this.number);
        dest.writeString(this.email);
        dest.writeValue(this.temperature);
        dest.writeString(this.timestamp);
        dest.writeString(this.name);
        dest.writeString(this.establishmentId);
        dest.writeValue(this.latitude);
        dest.writeString(this.longitude);
    }

    protected Customer(Parcel in) {
        this.first = in.readString();
        this.last = in.readString();
        this.address = in.readString();
        this.barangay = in.readString();
        this.city = in.readString();
        this.province = in.readString();
        this.number = (Long) in.readValue(Long.class.getClassLoader());
        this.email = in.readString();
        this.temperature = (Float) in.readValue(Float.class.getClassLoader());
        this.timestamp = in.readString();
        this.name = in.readString();
        this.establishmentId = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = in.readString();
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
