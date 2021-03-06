package cs240.fms.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cs240.fms.Models.Event;

public class EventDao {

    private Connection connection;
    /**
     * Adds an event row to Event table in database
     * @param event
     */
    public boolean addEvent(Event event) {
        PreparedStatement statement = null;
        try {
            String insert = "insert into Event values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(insert);
            statement.setString(1, event.getEventID());
            statement.setString(2, event.getUsername());
            statement.setString(3, event.getPersonID());
            statement.setDouble(4, event.getLatitude());
            statement.setDouble(5, event.getLongitude());
            statement.setString(6, event.getCountry());
            statement.setString(7, event.getCity());
            statement.setString(8, event.getEventType());
            statement.setInt(9, event.getYear());
            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Adds multiple events to table in database
     * @param eventList
     */
    public boolean addAllEvents(Event[] eventList) {
        boolean allAdded = true;
        for(Event event: eventList) {
            if(!addEvent(event))
                allAdded = false;
        }
        return allAdded;
    }

    /**
     * Removes an Event from row in Event table throws exception if event doesn't exist
     * @param eventId
     * @throws Exception
     */
    public boolean removeEvent(String eventId) throws Exception {
        PreparedStatement statement = null;
        try {
            String delete = "delete from Event where eventId = ?";
            statement = connection.prepareStatement(delete);
            statement.setString(1, eventId);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //remove all events associated with the given username
    public boolean removeAllEvents(String username) {
        //will it be ok if there are none?
        PreparedStatement statement = null;
        try {
            String delete = "delete from Event where username = ?";
            statement = connection.prepareStatement(delete);
            statement.setString(1, username);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a row from Event table, throws exception if Event does not exist
     * @param queryEventId
     * @throws Exception
     * @return event
     *
     */
    public Event getEvent(String queryEventId) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        Event queriedEvent = null;
        try {
            statement = connection.prepareStatement("select * from Event where eventId = ?");
            statement.setString(1, queryEventId);
            rs = statement.executeQuery();
            while(rs.next()) {
                String eventId = rs.getString(1);
                String username = rs.getString(2);
                String personId = rs.getString(3);
                Double latitude = rs.getDouble(4);
                Double longitude = rs.getDouble(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                int year = rs.getInt(9);
                queriedEvent = new Event(eventId, username, personId, latitude, longitude, country, city, eventType, year);
                break;
            }
            statement.close();
            rs.close();
            return queriedEvent;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Event[] getAllEvents(String queryUsername) {
        ArrayList<Event> eventList = new ArrayList<Event>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement("select * from Event where username = ?");
            statement.setString(1, queryUsername);
            rs = statement.executeQuery();
            while(rs.next()) {
                String eventId = rs.getString(1);
                String username = rs.getString(2);
                String personId = rs.getString(3);
                Double latitude = rs.getDouble(4);
                Double longitude = rs.getDouble(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                int year = rs.getInt(9);
                Event event = new Event(eventId, username, personId, latitude, longitude, country, city, eventType, year);
                eventList.add(event);
            }
            Event[] eventArray = eventList.toArray(new Event[eventList.size()]);
            statement.close();
            rs.close();
            return eventArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public EventDao(Connection connection) {

        this.connection = connection;
    }
    public EventDao() {}

}
