package cs240.fms.ServerFacade.DataHandling;


import cs240.fms.Models.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Generator {
    private FemaleName femaleNames;
    private MaleName maleNames;
    private Location locations;
    private LastName lastNames;

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
    public ArrayList createCouple(String username) {
        Person female = createPersonFromFile(username, "f");
        Person male = createPersonFromFile(username, "m");
        female.setSpouseID(male.getPersonID());
        male.setSpouseID(female.getPersonID());
        ArrayList<Person> couple = new ArrayList<Person>();
        couple.add(female);
        couple.add(male);

        return couple;
    }
    public ArrayList connectToParents(ArrayList<Person> recentGen, ArrayList<Person> oldGen) {
        //Each array passed in should be of size 2, with female


        return null;
    }
    public Event createBirth() {

        return null;
    }
    public Event createMarriage() {

        return null;
    }
    public Event createDeathDate() {

        return null;
    }
}
