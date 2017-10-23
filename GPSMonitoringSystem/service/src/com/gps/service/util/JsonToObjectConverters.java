package com.gps.service.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.gps.persistence.dto.Position;
import com.gps.persistence.util.PersistenceConstants;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.util.Date;


/**
 * @author radu.miron
 * @since 10/18/13
 */
public final class JsonToObjectConverters {
    /**
     * Hide the default constructor of this utility class.
     */
    private JsonToObjectConverters() {
    }

    /**
     * Converts a String in JSON format to a Position instance.
     *
     * @param jsonPosition
     * @return
     * @throws Exception
     */
    public static Position convertJsonToPosition(String jsonPosition) throws Exception {
        JsonFactory jasonFactory = new JsonFactory();
        JsonParser jsonParser;
        String latitude = null, longitude = null;
        Integer userId = null;
        Position position;
        try {
            jsonParser = jasonFactory.createParser(jsonPosition);
            jsonParser.nextToken(); // Returns "{" (Which is JsonToken.START_OBJECT)
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String field = jsonParser.getCurrentName();
                jsonParser.nextToken();
                if ("latitude".equals(field)) {
                    latitude = jsonParser.getText();
                } else if ("longitude".equals(field)) {
                    longitude = jsonParser.getText();
                } else if ("userId".equals(field)) {
                    userId = jsonParser.getIntValue();
                }
            }
        } catch (IOException e) {
            throw new Exception("Error while parsing input json!", e);
        }
        if (latitude != null && longitude != null && userId != null) {
            position = new Position(userId, latitude, longitude);
            position.setDate(new Date(System.currentTimeMillis()));
        } else {
            throw new Exception("Invalid input json!");
        }
        return position;
    }

    public static Integer convertJsonToUser(String userJson) throws Exception
    {
        JSONObject obj=new JSONObject(userJson);
        Integer userId=obj.getInt(PersistenceConstants.USER_ID);
        return userId;
    }

}