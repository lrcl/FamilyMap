package cs240.fms.Models;


import java.util.Random;


public class UserAuth {
    /** Auth Token created for this login */
    private String authToken;
    /** Username for which the token was created */
    private String username;

    public String generateAuthToken() {
        Random r = new Random();
        int token = 10000000 + (int)(r.nextFloat() * 90000000);
        String authToken = Integer.toString(token);
        return authToken;  //set the member variable authoken to this?
    }
    /** Get the Auth token
     * @return authToken
     */
    public String getAuthToken() {return authToken;}
    /** Get the username connected to auth token
     * @return username
     */
    public String getUsername() {return username;}
    /** User Auth Constructor
     * @param authToken
     * @param username
     */
    public UserAuth(String authToken, String username) {
        //this.authToken = generateAuthToken(); //is this a good idea?
        this.authToken = authToken;
        this.username = username;
    }

}
