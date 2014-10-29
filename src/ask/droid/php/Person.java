/**
 * Person.java
 * @author ASK
 * https://github.com/ask1612/AndroidPHP.git
 * 
*/
package ask.droid.php;




/**
 * Class Person
*/
public class Person
        {
        private String name;
        private String surname;
        private Address address;
        
        public Person(){}; //constructor
        /**
         *begin of getter/setter functions for the Person class
        */
            public String getName(){
                    return name;
                }
            public void setName(String name){
                    this.name = name;
                }
            public String getSurname(){
                    return surname;
                }
            public void setSurname(String surname){
                    this.surname = surname;
                }
            public Address getAddress(){
                    return address;
                }
            public void setAddress(Address address){
                    this.address=address;
                }
        /**
         *end  of getter/setter functions for the Person class
        */
            
        /**
         * Class Address
        */
        public class Address
            {
            private String city;
            private String street;
            private int build;
            private int flat;
            public Address(){};//constructor
        /**
         *begin of getter/setter functions for the Address class
        */
            
            public String getCity(){
                    return city;
                }
            
            public void  setCity(String city){
                    this.city= city;
                }
            public String getStreet(){
                    return street;
                }
            
            public void  setStreet(String street){
                    this.street= street;
                }
            public int getBuild(){   
                    return build;   
                }
            public void setBuild(int build){
                    this.build=build;   
                }
                public int getFlat(){
                    return flat;    
                }
                public void setFlat(int flat){
                    this.flat=flat;
                }
        /**
         *end of getter/setter functions  for the Address class
        */
            }
        }
