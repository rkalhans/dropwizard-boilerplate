package com.franzwong.app.dropwizard.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.franzwong.app.dropwizard.data.TaskRepository;
import com.franzwong.app.dropwizard.model.Task;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
	
	@Inject
	private TaskRepository taskRepository;
	
	@GET
	@Valid
	public Response getTasks(@NotNull @QueryParam("userName") String userName) {
		List<com.franzwong.app.dropwizard.data.Task> entities = taskRepository.findByUserName(userName);
		
		List<Task> tasks = entities.stream().map((entity) -> {
			Task task = new Task();
			task.setContent(entity.getContent());
			task.setUserName(entity.getUserName());
			return task;
		}).collect(Collectors.toList());
		
		return Response.ok().entity(tasks).build();
	}
	
	@POST
	@Valid
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTask(@NotNull Task task) {
		com.franzwong.app.dropwizard.data.Task entity = new com.franzwong.app.dropwizard.data.Task();
		entity.setContent(task.getContent());
		entity.setUserName(task.getUserName());
		
		com.franzwong.app.dropwizard.data.Task persistedEntity = taskRepository.addTask(entity);
		
		Task persistedTask = new Task();
		persistedTask.setId(persistedEntity.getId());
		persistedTask.setContent(persistedEntity.getContent());
		persistedTask.setUserName(persistedEntity.getUserName());
		
		return Response.ok().entity(persistedTask).build();
	}
}
