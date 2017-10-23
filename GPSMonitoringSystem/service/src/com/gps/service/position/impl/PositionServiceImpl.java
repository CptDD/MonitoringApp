package com.gps.service.position.impl;

import com.gps.persistence.dao.PositionDao;
import com.gps.persistence.dto.Position;
import com.gps.service.position.PositionService;
import com.gps.service.util.JsonToObjectConverters;
import com.gps.service.util.ObjectToJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * @author radu.miron
 * @since 10/8/13
 */
@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionDao positionDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void savePosition(String jsonPosition) throws Exception {
        positionDao.savePosition(JsonToObjectConverters.convertJsonToPosition(jsonPosition));
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
    public String getAllPositions(Integer userId) throws Exception
    {
         List<Position> allPositions=positionDao.getAllPositions(userId);
         return ObjectToJSON.convertPositionsToJson(allPositions);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly = false)
    public String getAllPositions(Integer userId,String start,String end) throws Exception
    {
        List<Position> allPositions=positionDao.getAllPositions(userId,start,end);
        return ObjectToJSON.convertPositionsToJson(allPositions);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=false)
    public void deletePositions(String userIdJson) throws Exception
    {
        positionDao.deletePositions(JsonToObjectConverters.convertJsonToUser(userIdJson));
    }




}
