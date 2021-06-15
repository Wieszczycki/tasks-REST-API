package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {

    @InjectMocks
    private TaskMapper taskMapper;


    @Test
    public void mapToTaskTest() {
        //Given
        Task task = new Task(1L, "Test title1", "test1");
        TaskDto taskDto = new TaskDto(1L, "Test title1", "test1");

        //When
        Task mappedTask = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(task.getId(), mappedTask.getId());
        assertEquals(task.getTitle(), mappedTask.getTitle());
        assertEquals(task.getContent(), mappedTask.getContent());
    }

    @Test
    public void mapToTaskDtoTest() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test title1", "test1");
        Task task = new Task(1L, "Test title1", "test1");

        //When
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(taskDto.getId(), mappedTaskDto.getId());
        assertEquals(taskDto.getTitle(), mappedTaskDto.getTitle());
        assertEquals(taskDto.getContent(), mappedTaskDto.getContent());
    }

    @Test
    public void mapTaskDtoListTest() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test title1", "test1");
        List<TaskDto> taskDtoList = Arrays.asList(taskDto);

        Task task = new Task(1L, "Test title1", "test1");
        List<Task> taskList = Arrays.asList(task);

        //When
        List<TaskDto> mappedList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertNotNull(mappedList);
        assertEquals(1, mappedList.size());

        mappedList.forEach(testTask -> {
            assertEquals(taskDto.getId(), testTask.getId());
            assertEquals(taskDto.getTitle(), testTask.getTitle());
            assertEquals(taskDto.getContent(),testTask.getContent());
        });
    }
}