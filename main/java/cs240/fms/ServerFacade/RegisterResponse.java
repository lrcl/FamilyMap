package cs240.fms.ServerFacade;


public class RegisterResponse {
   /** auth token */
   private String authToken;
   /** newly created username */
   private String username;
   /** newly assigned personID */
   private String personId;

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonId() {
        return personId;
    }

    /** Constructor
    *
    * @param authToken
    * @param username
    * @param personId
    */
   public RegisterResponse(String authToken, String username, String personId) {
       this.authToken = authToken;
       this.username = username;
       this.personId = personId;
   }
}

