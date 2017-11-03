package cs240.fms.Models;


public class User {
    /** The unique username */
    private String username;
    /**User's password*/
    private String password;
    /** User's email address*/
    private String email;
    /** User's first name*/
    private String firstName;
    /** User's last name*/
    private String lastName;
    /** User's gender*/
    private String gender;
    /** Unique Person ID assigned by Person object */
    private String personId;

    /**
     * Return the username
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Return the password
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Return the email
     * @return email address
     */
    public String getEmail() {
        return email;
    }
    /**
     * Return the user's first name
     * @return first name of user
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Return user's last name
     * @return last name of user
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Return the gender
     * @return user's gender, f or m
     */
    public String getGender() {
        return gender;
    }
    /** Return the personID of the user
     * @return personID
     */
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String newPersonId) {
        this.personId = newPersonId;
    }
    /** Constructor
     * @param username
     * @param  password
     * @param  email
     * @param firstName
     * @param  lastName
     * @param gender
     * @param personID
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personId = personID;

    }
    public User(String username) {
        this.username = username;
    }

}
