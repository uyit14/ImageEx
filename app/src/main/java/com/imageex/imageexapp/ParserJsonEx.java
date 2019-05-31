package com.imageex.imageexapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ParserJsonEx {
    public void parseJson(){
        //we have a json object
        String jsonStr = "{\n" +
                "    \"contacts\": [\n" +
                "        {\n" +
                "                \"id\": \"c200\",\n" +
                "                \"name\": \"Ravi Tamada\",\n" +
                "                \"email\": \"ravi@gmail.com\",\n" +
                "                \"address\": \"xx-xx-xxxx,x - street, x - country\",\n" +
                "                \"gender\" : \"male\",\n" +
                "                \"phone\": {\n" +
                "                    \"mobile\": \"+91 0000000000\",\n" +
                "                    \"home\": \"00 000000\",\n" +
                "                    \"office\": \"00 000000\"\n" +
                "                }\n" +
                "        },\n" +
                "        {\n" +
                "                \"id\": \"c201\",\n" +
                "                \"name\": \"Johnny Depp\",\n" +
                "                \"email\": \"johnny_depp@gmail.com\",\n" +
                "                \"address\": \"xx-xx-xxxx,x - street, x - country\",\n" +
                "                \"gender\" : \"male\",\n" +
                "                \"phone\": {\n" +
                "                    \"mobile\": \"+91 0000000000\",\n" +
                "                    \"home\": \"00 000000\",\n" +
                "                    \"office\": \"00 000000\"\n" +
                "                }\n" +
                "        },\n" +
                "        .\n" +
                "        .\n" +
                "        .\n" +
                "        .\n" +
                "  ]\n" +
                "}";

        //parser
        if(jsonStr!=null){
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray contacts = jsonObject.getJSONArray("contacts");
                for(int i=0; i<contacts.length();i++){
                    JSONObject c = contacts.getJSONObject(i);
                    //
                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");
                    //
                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("id", id);
                    contact.put("name", name);
                    contact.put("email", email);
                    contact.put("mobile", mobile);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
