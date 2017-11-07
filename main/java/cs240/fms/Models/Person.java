package cs240.fms.Models;


public class Person {

    /**
     * Unique ID for person
     */
    private String personID;
    /**
     *  username of descendant to which this person is related
     */
    private String descendant;
    /**
     * Person's first name
     */
    private String firstName;
    /**
     * Person's last name
     */
    private String lastName;
    /**
     * Person's gender
     */
    private String gender;
    /**
     * ID of person's father, could be null
     */
    private String fatherID;
    /**
     * ID of person's mother, could be null
     */
    private String motherID;
    /**
     * ID of person's spouse, could be null
     */
    private String spouseID;

    /**
     * get personID
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * get user Name
     * @return userName
     */
    public String getDescendant() {
        return descendant;
    }

    /**
     * get first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * get last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * get gender
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * get the person's father's ID
     * @return fatherID
     */
    public String getFatherID() {
        return fatherID;
    }

    /**
     * get the person's mother's ID
     * @return motherID
     */
    public String getMotherID() {
        return motherID;
    }

    /**
     * get person's spouse ID
     * @return spouseID
     */
    public String getSpouseID() {
        return spouseID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    /**
     * Person constructor
     * @param personID
     * @param descendant
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     */
    public Person(String personID, String descendant, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }
    public Person(String personID){
        this.personID = personID;
    }
    public Person() {}

}
