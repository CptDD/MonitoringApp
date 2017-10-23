package com.gps.service.util;

import com.gps.persistence.dto.Position;
import java.util.List;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import com.gps.persistence.util.PersistenceConstants;

/**
 * Created by admin on 29/11/2014.
 */
public class ObjectToJSON {

    public static String convertPositionsToJson(List<Position> positions) throws Exception
    {
        JSONArray result=new JSONArray();
        for(Position position:positions)
        {
            JSONObject obj=new JSONObject();
            obj.put(PersistenceConstants.USER_ID,position.getUserId());
            obj.put(PersistenceConstants.LATITUDE,position.getLatitude());
            obj.put(PersistenceConstants.LONGITUDE,position.getLongitude());
            obj.put(PersistenceConstants.DATE,position.getDate().toString());
            result.put(obj);
        }
        return result.toString();

    }

}
