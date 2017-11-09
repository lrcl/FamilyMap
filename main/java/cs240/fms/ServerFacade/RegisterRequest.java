package cs240.fms.ServerFacade;


public class RegisterRequest {

      /** username */

   private String userName; //should be camelcase
   /** password */
   private String password;
   /** User's email address */
   private String email;
   /** user's first name */
   private String firstName;
   /** user's last name */
   private String lastName;
   /** user's gender */
   private String gender;

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

   /**Constructor
    *
    * @param username
    * @param password
    * @param email
    * @param firstName
    * @param lastName
    * @param gender
    */
   public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
       this.userName = username;
       this.password = password;
       this.email = email;
       this.firstName = firstName;
       this.lastName = lastName;
       this.gender = gender;
   }
}

