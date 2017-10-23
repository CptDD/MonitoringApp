package com.gps.rest;

import com.gps.rest.util.WsConstants;
import com.gps.service.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

/**
 * @author radu.miron
 * @since 10/8/13
 */
@Component
@Path("/position")
public class PositionGateway {
    @Autowired
    private PositionService positionService;

    @GET
    @Path("/ping")
    public Response hello() {
        String result = "I'm alive!";
        return Response.status(200).entity(result).build();
    }

    @POST
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response savePosition(String jsonPosition) {
        try {
            positionService.savePosition(jsonPosition);
            return Response.status(200).entity(WsConstants.SUCCESS_STATUS).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(422).entity(WsConstants.FAIL_STATUS).build();
        }
    }

    @GET
    @Path("/allPositions")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPositions(@QueryParam("userId")Integer userId)
    {
        try
        {
            String allPositions=positionService.getAllPositions(userId);
            return Response.status(200).entity(allPositions).build();
        }catch(Exception e)
        {
            e.printStackTrace();
            return Response.status(422).entity(WsConstants.FAIL_STATUS).build();
        }
    }
    @GET
    @Path("/getPositions")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPositions(@QueryParam("userId")Integer userId,@QueryParam("start")String start,
    @QueryParam("end")String end)
    {
        try {
            String allPositions = positionService.getAllPositions(userId, start, end);
            return Response.status(200).entity(allPositions).build();
        }catch(Exception e)
        {
            return Response.status(422).entity(WsConstants.FAIL_STATUS).build();
        }
    }

    @DELETE
    @Path("/deletePositions")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePositions(String jsonUserId)
    {
        try
        {
            positionService.deletePositions(jsonUserId);
            return Response.status(200).entity(WsConstants.SUCCESS_STATUS).build();
        }catch(Exception e)
        {
            return Response.status(422).entity(WsConstants.FAIL_STATUS+" "+e.getMessage()).build();
        }
    }







    @GET
    @Path("/hello")
    @Produces({MediaType.APPLICATION_JSON})
    public Response go(@QueryParam("value") String value)
    {
        return Response.status(200).entity(value).build();
    }


}