package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchTasksLists() throws Exception {
        //given
        Task task = new Task(1L, "Test task1", "test1");
        Task task2 = new Task(2L, "Test task2", "test2");

        TaskDto taskDto = new TaskDto(1L, "Test task1", "test1");
        TaskDto taskDto2 = new TaskDto(2L, "Test task2", "test2");

        List<TaskDto> taskListDto = Arrays.asList(taskDto, taskDto2);

        List<Task> taskList = Arrays.asList(task, task2);

        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskListDto);

        //when & then

        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].title", is("Test task1")))
                .andExpect(jsonPath("$.[0].content", is("test1")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].title", is("Test task2")))
                .andExpect(jsonPath("$.[1].content", is("test2"))
                );
        verify(service, times(1)).getAllTasks();
        verify(taskMapper, times(1)).mapToTaskDtoList(taskList);
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(taskMapper);
    }

    @Test
    public void shouldFetchEmptyTasksLists() throws Exception {
        //given
        List<TaskDto> taskListDto = new ArrayList<>();

        when(service.getAllTasks()).thenReturn(new ArrayList<>());
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskListDto);

        //when & then

        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0))
                );
        verify(service, times(1)).getAllTasks();
        verify(taskMapper, times(1)).mapToTaskDtoList(anyList());
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(taskMapper);
    }

    @Test
    public void shouldGetTask() throws Exception {

        //given
        Task task = new Task(1L,"Test task", "test1");

        TaskDto taskDto = new TaskDto(1L,"Test task","test1");

        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(service.getTask(1L)).thenReturn(Optional.of(task));

        //when & then
        mockMvc.perform(get("/v1/task/getTask").contentType(MediaType.APPLICATION_JSON).param("taskId", "1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test task")))
                .andExpect(jsonPath("$.content", is("test1")));
        verify(service, times(1)).getTask(1L);
        verify(taskMapper, times(1)).mapToTaskDto(any(Task.class));
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(taskMapper);
    }

    @Test
    public void testGetTaskNonExistent() throws Exception, TaskNotFoundException {

        //given
        when(service.getTask(1L)).thenReturn(Optional.empty());

        //when & then
        try {
            mockMvc.perform(get("/v1/task/getTask").contentType(MediaType.APPLICATION_JSON).param("taskId", "1"))
                    .andExpect(status().isNotFound());
        } catch (TaskNotFoundException e) {
            System.out.println("TaskNotFoundException" + e);
        } finally {
            verify(service, times(1)).getTask(1L);
            verifyNoMoreInteractions(service);
        }
    }

    @Test
    public void testDeleteTask() throws Exception {

        //given
        Task task = new Task(1L,"Test task","test1");

        when(service.getTask(1L)).thenReturn(Optional.of(task));

        //when & then
        mockMvc.perform(delete("/v1/task/deleteTask").contentType(MediaType.APPLICATION_JSON).param("taskId", "1"))
                .andExpect(status().is(200)
                );
        verify(service, times(1)).deleteTask(task.getId());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testUpdateTask() throws Exception {

        //given
        Task task = new Task(1L,"Test task1","test1");
        TaskDto taskDto = new TaskDto(1L,"Test task1","test1");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //when & then
        mockMvc.perform(put("/v1/task/updateTask/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test task1")))
                .andExpect(jsonPath("$.content", is("test1"))
                );
        verify(taskMapper, times(1)).mapToTask(any(TaskDto.class));
        verify(taskMapper, times(1)).mapToTaskDto(any(Task.class));
        verify(service, times(1)).saveTask(task);
        verifyNoMoreInteractions(taskMapper);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void shouldCreateTask() throws Exception {

        //given
        Task task = new Task(1L,"Test task1","test1");
        TaskDto taskDto = new TaskDto(1L,"Test task1","test1");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        //when & then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk()
        );
        verify(taskMapper, times(1)).mapToTask(any(TaskDto.class));
    }
}