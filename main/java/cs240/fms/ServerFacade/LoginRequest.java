package cs240.fms.ServerFacade;


public class LoginRequest {
   /** username of existing user */
   private String userName;
   /** password previously created */
   private String password;

   public String getUsername() {
       return userName;
   }
   public String getPassword() {
       return password;
   }
   /**Constructor
    *
    * @param username
    * @param password
    */
   public LoginRequest(String username, String password) {
       this.userName = username;
       this.password = password;
   }
}

