package cs240.fms.ServerFacade.DataHandling;


import cs240.fms.Models.*;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Generator {
    protected FemaleName femaleNames;
    protected MaleName maleNames;
    protected Location locations;
    protected LastName lastNames;

    public void loadData() {

       Gson gson = new Gson();
       gson.fromJson("fnames.json",FemaleName.class);
       Gson gson2 = new Gson();
       gson2.fromJson("mnames.json", MaleName.class);
       Gson gson3 = new Gson();
       gson3.fromJson("locations.json", Location.class);
       Gson gson4 = new Gson();
       gson4.fromJson("snames.json", LastName.class);
    }
    public Person createPersonFromUser(User user){
        Person p = new Person();
        p.setPersonID(createId());
        p.setDescendant(user.getUsername());
        p.setFirstName(user.getFirstName());
        p.setLastName(user.getLastName());
        p.setGender(user.getGender());

        return p;
    }
    public Person createPersonFromFile(String username, String gender) {
        Person p = new Person();
        Random r = new Random();

        //random first name from file
        if(gender.equals("f")) {
            int index = r.nextInt(femaleNames.data.length-1);
            p.setFirstName(femaleNames.data[index]);
        }
        else {
            r = new Random();
            int index2 = r.nextInt(maleNames.data.length-1);
            p.setFirstName(maleNames.data[index2]);

        }
        //random last name from file
        r = new Random();
        int index3 = r.nextInt(lastNames.data.length-1);
        p.setLastName(lastNames.data[index3]);

        //set everything except spouse and parentIds
        p.setDescendant(username);
        p.setPersonID(createId());
        p.setGender(gender);

        return p;
    }
    public String createId() {
        //generate Id
        String longId = UUID.randomUUID().toString();
        String Id = longId.substring(0,7);
        return Id;
    }
    public Event createBirth(int gen, String username, String personId) {
        //birth year
        Random r = new Random();
        int offset = r.nextInt(6);
        int year = 1990 - (gen*20) + offset;

        //location
        r = new Random();
        int index = r.nextInt(locations.data.length-1);
        String country = getCountry(locations.data[index]);
        String city = getCity(locations.data[index]);
        Double lat = getLat(locations.data[index]);
        Double longit = getLong(locations.data[index]);

        //type
        String type = "birth";
        //eventId
        String eventId = createId();

        Event birth = new Event(eventId,username,personId,lat,longit,country,city,type,year);
        return birth;
    }
    public Event createMarriage(int gen, String username, String personId) {
        //year
        Random r = new Random();
        int offset = r.nextInt(6);
        int year = 2010 - (gen * 20) + offset;

        //location
        r = new Random();
        int index = r.nextInt(locations.data.length-1);
        String country = getCountry(locations.data[index]);
        String city = getCity(locations.data[index]);
        Double lat = getLat(locations.data[index]);
        Double longit = getLong(locations.data[index]);

        //type
        String type = "marriage";
        //eventId
        String eventId = createId();

        Event marriage = new Event(eventId,username,personId,lat,longit,country,city,type,year);
        return marriage;
    }
    public Event createDeathDate(int gen, String username, String personId) {
        //year
        Random r = new Random();
        int offset = r.nextInt(6);
        int year = 2030 - (gen * 20) + offset;

        //location
        r = new Random();
        int index = r.nextInt(locations.data.length-1);
        String country = getCountry(locations.data[index]);
        String city = getCity(locations.data[index]);
        Double lat = getLat(locations.data[index]);
        Double longit = getLong(locations.data[index]);

        //type
        String type = "death";
        //eventId
        String eventId = createId();

        Event death = new Event(eventId,username,personId,lat,longit,country,city,type,year);
        return death;
    }
    public String getCountry(String jsonStr) {
        try {
            JSONObject json = new JSONObject(jsonStr);
            String country = json.getString("country");
            return country;
        } catch (JSONException je) {
            je.printStackTrace();
            return "";
        }
    }
    public String getCity(String jsonStr) {
        try{
            JSONObject json = new JSONObject(jsonStr);
            String city = json.getString("city");
            return city;
        } catch (JSONException je) {
            je.printStackTrace();
            return "";
        }
    }
    public Double getLat(String js) {
        try{
            JSONObject json = new JSONObject(js);
            Double lat = json.getDouble("latitude");
            return lat;
        } catch(JSONException je) {
            je.printStackTrace();
            return -3.3;
        }
    }
    public Double getLong(String js) {
        try{
            JSONObject json = new JSONObject(js);
            Double longit = json.getDouble("longitude");
            return longit;
        } catch (JSONException je) {
            je.printStackTrace();
            return -3.3;
        }
    }
}
