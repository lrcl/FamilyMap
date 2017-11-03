package cs240.fms.ServerFacade;


public class LoginRequest {
   /** username of existing user */
   private String username;
   /** password previously created */
   private String password;

   public String getUsername() {
       return username;
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
       this.username = username;
       this.password = password;
   }
}

