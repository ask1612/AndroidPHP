/**
 *
 * Wer sucht, der findet. Bald kommt der Winter mit Schnee und Frost.
 *
 * Person.java Copyright (C) 2014 The Android Open Source Project
 *
 * @author ASK https://github.com/ask1612/AndroidPHP.git
 */
package ask.droid.php;

/**
 * Person is class to work with data any person. It has fields <name>,<surname>
 * and  <address>. It allows to set and get values of this fields.
 */
public class Person {

    private String name;
    private String surname;
    private Address address;

    /**
     * constructor
     */
    public Person() {
        this.name = new String();
        this.surname = new String();
        this.address = new Address();
    }

    ; 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Class Address works with an address of person. It allows to set and get
     * the adress of person
     */
    public class Address {

        private String city;
        private String street;
        private int build;
        private int flat;

        /**
         * constructor
         */
        Address() {
            this.city = new String();
            this.street = new String();
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public int getBuild() {
            return build;
        }

        public void setBuild(int build) {
            this.build = build;
        }

        public int getFlat() {
            return flat;
        }

        public void setFlat(int flat) {
            this.flat = flat;
        }
    }
}
