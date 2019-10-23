package com.example.todoList.Controller;


import com.example.todoList.Service.TodoItemService;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoController todoController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoItemService mockTodoItemService;

    @Test
    public void should_return_200_code_status_when_get_all_list() throws Exception {
        TodoItem list = new TodoItem("todo", Status.pending);
        ArrayList<TodoItem> todoLists = new ArrayList<>();
        todoLists.add(list);

        when(mockTodoItemService.getAll()).thenReturn(todoLists);

        mockMvc.perform(get("/api/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_code_status_when_create_todoList() throws Exception {
        TodoItem list = new TodoItem(1,"first", Status.pending,1);
        when(mockTodoItemService.create(list.getDescription())).thenReturn(list);
        mockMvc.perform(post("/api/one")
                .param("description", list.getDescription())
                .param("pending", list.getStatus().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_code_status_when_update_oneList() throws Exception {
        TodoItem list = new TodoItem(1,"second", Status.pending,0);

        when(mockTodoItemService.update(list.getId(),list.getStatus().toString())).thenReturn(true);

        mockMvc.perform(patch("/api/status/1")
                .param("status", list.getStatus().toString()))
                .andExpect(status().is(200));
    }

    @Test
    public void should_return_200_code_status_when_delete_oneList() throws Exception {
        TodoItem list = new TodoItem(1,"second", Status.pending,1);
        when(mockTodoItemService.delete(list.getId())).thenReturn(true);

        String s = objectMapper.writeValueAsString(list);
        mockMvc.perform(delete("/api/one")
                .param("id", list.getId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_code_status_when_change_index() throws Exception {
        ArrayList<TodoItem> todoLists = new ArrayList<>();
        TodoItem list = new TodoItem("todo1", Status.pending,1);
        TodoItem list1 = new TodoItem("todo2", Status.pending, 2);
        TodoItem list2 = new TodoItem("todo3", Status.pending, 3);
        todoLists.add(list);
        todoLists.add(list1);
        todoLists.add(list2);
        when(mockTodoItemService.updateIndex(any(), any())).thenReturn(todoLists);
        mockMvc.perform(patch("/api/indexOrder")
               .param("startIndex", "1")
               .param("endIndex","3"))
                .andExpect(status().isOk());
    }
}
