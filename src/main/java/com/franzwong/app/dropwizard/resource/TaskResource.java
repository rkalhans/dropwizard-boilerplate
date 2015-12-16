package com.franzwong.app.dropwizard.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.franzwong.app.dropwizard.model.Task;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
	
	@GET
	public Response getTasks(@QueryParam("userName") String userName) {
		List<Task> tasks = new ArrayList<>();
		
		Task task1 = new Task();
		task1.setContent("task 1");
		tasks.add(task1);
		
		Task task2 = new Task();
		task2.setContent("task 2");
		tasks.add(task2);
		
		return Response.ok().entity(tasks).build();
	}
	
}
