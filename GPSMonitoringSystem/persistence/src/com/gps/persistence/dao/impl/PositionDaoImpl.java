package com.gps.persistence.dao.impl;

import com.gps.persistence.dao.PositionDao;
import com.gps.persistence.dto.Position;
import com.gps.persistence.util.PersistenceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * @author radu.miron
 * @since 10/8/13
 */
@Repository
public class PositionDaoImpl implements PositionDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public PositionDaoImpl(){
        System.out.println("PDI created!");
    }

    @Override
    public void savePosition(Position position) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + PersistenceConstants.POSITION_TABLE);
        sql.append(" ( " + PersistenceConstants.USER_ID + ", " + PersistenceConstants.LATITUDE + ", ");
        sql.append(PersistenceConstants.LONGITUDE + ", " + PersistenceConstants.DATE + ") ");
        sql.append("VALUES (:userId, :latitude, :longitude, :date);");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", position.getUserId());
        params.put("latitude", position.getLatitude());
        params.put("longitude", position.getLongitude());
        params.put("date", position.getDate());

        try{
            jdbcTemplate.update(sql.toString(), params);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Position> getAllPositions(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + PersistenceConstants.POSITION_TABLE);
        sql.append(" WHERE userId = :userId");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);

        List<Position> positions=null;

        try {
            positions=jdbcTemplate.query(sql.toString(), params, new RowMapper<Position>() {
                public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Integer userId = rs.getInt(PersistenceConstants.USER_ID);
                    String latitude = rs.getString(PersistenceConstants.LATITUDE);
                    String longitude = rs.getString(PersistenceConstants.LONGITUDE);
                    Date date=rs.getDate(PersistenceConstants.DATE);
                     Position pos= new Position(userId, latitude, longitude);
                    pos.setDate(date);
                    return pos;

                }
            });
        } catch (Exception e) {
            throw e;
        }
        return positions;
    }

    @Override
    public List<Position> getAllPositions(Integer userId,String start,String end) throws Exception {

        StringBuilder sql = new StringBuilder("SELECT * FROM " + PersistenceConstants.POSITION_TABLE);
        sql.append(" WHERE userId = :userId AND DATE(date) ");
        sql.append(" BETWEEN DATE(:start) AND DATE(:end)");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("start", start);
        params.put("end", end);

        List<Position> positions = null;

        try {
            positions = jdbcTemplate.query(sql.toString(), params, new RowMapper<Position>() {
                public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Integer userId = rs.getInt(PersistenceConstants.USER_ID);
                    String latitude = rs.getString(PersistenceConstants.LATITUDE);
                    String longitude = rs.getString(PersistenceConstants.LONGITUDE);
                    Date date=rs.getDate(PersistenceConstants.DATE);
                    Position pos=new Position(userId, latitude, longitude);
                    pos.setDate(date);

                    return pos;
                }

                ;

            });
            return positions;
        } catch (Exception e) {
            throw e;


        }
    }

    @Override
    public void deletePositions(Integer userId) throws Exception
    {
       StringBuilder sql=new StringBuilder();
        sql.append("DELETE FROM "+PersistenceConstants.POSITION_TABLE);
        sql.append(" WHERE userId=:userId");

        Map<String,Object> params=new HashMap<String,Object>();
        params.put("userId",userId);

        try
        {
            jdbcTemplate.update(sql.toString(),params);
        }catch(Exception e)
        {
            throw e;
        }
    }





}
