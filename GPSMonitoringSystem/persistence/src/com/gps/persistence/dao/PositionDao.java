package com.gps.persistence.dao;

import com.gps.persistence.dto.Position;
import java.util.List;

/**
 * @author radu.miron
 * @since 10/8/13
 */
public interface PositionDao {

    void savePosition(Position position) throws Exception;
    List<Position> getAllPositions(Integer userId)  throws Exception;
    List<Position> getAllPositions(Integer userId,String start,String end)  throws Exception;
    void deletePositions(Integer userId) throws Exception;


}
