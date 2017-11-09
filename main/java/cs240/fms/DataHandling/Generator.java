package cs240.fms.DataHandling;


import cs240.fms.Models.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.UUID;

public class Generator {
    protected FemaleName femaleNames;
    protected MaleName maleNames;
    protected Location locations;
    protected LastName lastNames;

    public boolean loadData() {
        final String JsonPath1 = "/home/grant/AndroidStudioProjects/FMS/app/libs/json/fnames.json";
        final String JsonPath2 = "/home/grant/AndroidStudioProjects/FMS/app/libs/json/mnames.json";
        final String JsonPath3 = "/home/grant/AndroidStudioProjects/FMS/app/libs/json/locations.json";
        final String JsonPath4 = "/home/grant/AndroidStudioProjects/FMS/app/libs/json/snames.json";
        try{
            JsonReader jsonReader1 = new JsonReader(new FileReader(JsonPath1));
            JsonReader jsonReader2 = new JsonReader(new FileReader(JsonPath2));
            JsonReader jsonReader3 = new JsonReader(new FileReader(JsonPath3));
            JsonReader jsonReader4 = new JsonReader(new FileReader(JsonPath4));

            Gson gson = new Gson();
            femaleNames = gson.fromJson(jsonReader1,FemaleName.class);
            Gson gson2 = new Gson();
            maleNames = gson2.fromJson(jsonReader2, MaleName.class);
            Gson gson3 = new Gson();
            locations = gson3.fromJson(jsonReader3, Location.class);
            Gson gson4 = new Gson();
            lastNames = gson4.fromJson(jsonReader4, LastName.class);
            if(gson == null)
                return false;
            return true;
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Person createPersonFromUser(User user){
        Person p = new Person();
        p.setPersonID(user.getPersonId());
        p.setDescendant(user.getUsername());
        p.setFirstName(user.getFirstName());
        p.setLastName(user.getLastName());
        p.setGender(user.getGender());

        return p;
    }
    public Person createPersonFromFile(String username, String gender) {
        loadData();
        Person p = new Person();
        Random r = new Random();

        //random first name from file
        if(gender.equals("f")) {
            int index = r.nextInt(femaleNames.data.length-1);
            p.setFirstName(femaleNames.data[index]);
            //System.out.println(femaleNames.data[index]);
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
        loadData();
        //birth year
        Random r = new Random();
        int offset = r.nextInt(5);
        int year = 1990 - (gen*20) + offset;

       //location
        r = new Random();
        int index = r.nextInt(locations.data.length-1);
        String country = locations.data[index].get("country").toString();
        String city = locations.data[index].get("city").toString();
        Double lat = Double.parseDouble(locations.data[index].get("latitude").toString());
        Double longit = Double.parseDouble(locations.data[index].get("longitude").toString());

        //type
        String type = "birth";
        //eventId
        String eventId = createId();

        Event birth = new Event(eventId,username,personId,lat,longit,country,city,type,year);
        return birth;
    }
    public Double generateLocationInfo() {
        //location
        loadData();
        Random r = new Random();
        int index = r.nextInt(locations.data.length-1);
        String country = locations.data[index].get("country").toString();
        String city = locations.data[index].get("city").toString();
        Double lat = Double.parseDouble(locations.data[index].get("latitude").toString());
        Double longit = Double.parseDouble(locations.data[index].get("longitude").toString());
        return longit;
    }
    public Event createMarriage(int gen, String username, String personId) {
        loadData();
       //year
        Random r = new Random();
        int offset = r.nextInt(5);
        int year = 2010 - (gen * 20) + offset;

        //location
        r = new Random();
        int index = r.nextInt(locations.data.length-1);
        String country = locations.data[index].get("country").toString();
        String city = locations.data[index].get("city").toString();
        Double lat = Double.parseDouble(locations.data[index].get("latitude").toString());
        Double longit = Double.parseDouble(locations.data[index].get("longitude").toString());

        //type
        String type = "marriage";
        //eventId
        String eventId = createId();

        Event marriage = new Event(eventId,username,personId,lat,longit,country,city,type,year);
        return marriage;
    }
    public Event createDeathDate(int gen, String username, String personId) {
        loadData();
        //year
        Random r = new Random();
        int offset = r.nextInt(5);
        int year = 2030 - (gen * 20) + offset;

        //location
        r = new Random();
        int index = r.nextInt(locations.data.length-1);
        String country = locations.data[index].get("country").toString();
        String city = locations.data[index].get("city").toString();
        Double lat = Double.parseDouble(locations.data[index].get("latitude").toString());
        Double longit = Double.parseDouble(locations.data[index].get("longitude").toString());

        //type
        String type = "death";
        //eventId
        String eventId = createId();

        Event death = new Event(eventId,username,personId,lat,longit,country,city,type,year);
        return death;
    }
    public String getCountry(String jsonStr) {
        try {
            JSONObject json = new JSONObject(jsonStr.trim());
            String country = json.getString("country");
            return country;
        } catch (JSONException je) {
            je.printStackTrace();
            return "";
        }
    }
    public String getCity(String jsonStr) {
        try{
            JSONObject json = new JSONObject(jsonStr.trim());
            String city = json.getString("city");
            return city;
        } catch (JSONException je) {
            je.printStackTrace();
            return "";
        }
    }
    public Double getLat(String js) {
        try{
            JSONObject json = new JSONObject(js.trim());
            Double lat = json.getDouble("latitude");
            return lat;
        } catch(JSONException je) {
            je.printStackTrace();
            return -3.3;
        }
    }
    public Double getLong(String js) {
        try{
            JSONObject json = new JSONObject(js.trim());
            Double longit = json.getDouble("longitude");
            return longit;
        } catch (JSONException je) {
            je.printStackTrace();
            return -3.3;
        }
    }
}
