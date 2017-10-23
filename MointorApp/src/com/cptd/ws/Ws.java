package com.cptd.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.cptd.persistence.User;
import com.cptd.persistence.UserDAO;
import com.cptd.persistence.UserDAOImpl;

@Path("/user")
public class Ws {

	@Path("/insert")
	@POST
	@Consumes({ MediaType.TEXT_PLAIN })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response insertUser(String result) {
		UserDAO dao = new UserDAOImpl();

		try {

			User tempUser = JSONConverter.JSONtoUser(result);
			dao.insertUser(tempUser);
			return Response.status(200).entity(Utility.SUCESS).build();
		} catch (Exception e) {
			return Response.status(422)
					.entity(Utility.ERROR + " " + e.getMessage()).build();
		}

	}

	@POST
	@Path("/test")
	@Consumes({ MediaType.TEXT_PLAIN })
	public Response go(String result) {
		return Response.status(200).entity(result).build();

	}

	@GET
	public String here() {
		return "We are here to make some noise!";
	}

	@GET
	@Path("/allUsers")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllUsers() {
		UserDAO dao = new UserDAOImpl();
		try {
			List<User> users = dao.getAllUsers();
			String result = JSONConverter.UsersToJSON(users);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			return Response.status(422)
					.entity(Utility.ERROR + " " + e.getMessage()).build();
		}

	}

	@Path("/makeAdmin")
	@POST
	@Consumes({ MediaType.TEXT_PLAIN })
	public Response makeAdmin(String result) {
		UserDAO dao = new UserDAOImpl();
		try {
			User temp = JSONConverter.JSONtoUser(result);
			dao.makeAdmin(temp);
			return Response.status(200).entity(Utility.SUCESS).build();
		} catch (Exception e) {
			return Response.status(422)
					.entity(Utility.ERROR + " " + e.getMessage()).build();
		}
	}

	@Path("/isAdmin")
	@GET
	public Response isAdmin(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		UserDAO dao = new UserDAOImpl();
		try {

			boolean response = dao.isAdmin(username, password);
			return Response.status(200).entity(response + "").build();
		} catch (Exception e) {
			return Response.status(422)
					.entity(Utility.ERROR + " " + e.getMessage()).build();
		}
	}

	@Path("/deleteUser")
	@DELETE
	public Response deleteUser(String result) {
		UserDAO dao = new UserDAOImpl();
		try {
			User user = JSONConverter.JSONtoUser(result);
			dao.deleteUser(user);
			return Response.status(200).entity(Utility.SUCESS).build();
		} catch (Exception e) {
			return Response.status(422)
					.entity(Utility.ERROR + " " + e.getMessage()).build();
		}
	}

	@Path("/getUser")
	@GET
	public Response getUser(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		UserDAO dao = new UserDAOImpl();
		try {
			User user = dao.getUser(username, password);
			String result = JSONConverter.userToJSON(user);
			return Response.status(200).entity(result).build();

		} catch (Exception e) {
			return Response.status(422)
					.entity(Utility.ERROR + " " + e.getMessage()).build();
		}

	}

	@Path("/login")
	@POST
	public Response login(String result) {
		UserDAO dao = new UserDAOImpl();
		try {
			JSONObject obj = new JSONObject(result);
			String username = obj.getString("username");
			String password = obj.getString("password");
			User user = dao.getUser(username, password);
			String res = JSONConverter.userToJSON(user);
			return Response.status(200).entity(res).build();
		} catch (Exception e) {
			return Response.status(422).entity(Utility.ERROR).build();
		}

	}

}
