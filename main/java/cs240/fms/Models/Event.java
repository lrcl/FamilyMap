package cs240.fms.Models;


public class Event {
    /**
     * unique Id for event
     */
    private String eventID;
    /**
     * username to which this event belongs
     */
    private String username;
    /**
     * personID to which this event belongs
     */
    private String personID;
    /**
     * latitude of event
     */
    private Double latitude;
    /**
     * longitude of event
     */
    private Double longitude;
    /**
     * country where event took place
     */
    private String country;
    /**
     * city where event took place
     */
    private String city;
    /**
     * type of event(birth baptism, christening, marriage, death etc)
     */
    private String eventType;
    /**
     * year in which event occurred
     */
    private int year;

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * returns unique Id for event
     *
     * @return eventID
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * returns username to which this event belongs
     *
     * @return username;
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns personID to which this event belongs
     *
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * returns latitude of event
     *
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * returns longitude of event
     *
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * returns country where event took place
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * returns city where event took place
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * returns type of event
     *
     * @return eventType
     */
    public String getEventType() {
        return eventType;
    }

    /** returns year in which event occurred
     *@return year
     */
    public int getYear() {
        return year;
    }

    /** constructor
     *
     * @param eventID
     * @param username
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String eventID, String username, String personID, Double latitude, Double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.username = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }
    public Event(Event event){
        this.eventID = event.eventID;
        this.username = event.username;
        this.personID = event.personID;
        this.latitude = event.latitude;
        this.longitude = event.longitude;
        this.country = event.country;
        this.city = event.city;
        this.eventType = event.eventType;
        this.year = event.year;
    }

}
