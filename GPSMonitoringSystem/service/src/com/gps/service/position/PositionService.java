package com.gps.service.position;

import java.util.Date;

/**
 * @author radu.miron
 * @since 10/8/13
 */
public interface PositionService {
    void savePosition(String jsonPosition) throws Exception;
    String getAllPositions(Integer userId) throws Exception;
    String getAllPositions(Integer userId,String start,String end) throws Exception;
    void deletePositions(String userIdJson) throws Exception;


}
