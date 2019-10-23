package com.example.todoList.Controller;


import com.example.todoList.Service.TodoListService;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoList;
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
    private  TodoListService mockTodoListService;

    @Test
    public void should_return_200_code_status_when_get_all_list() throws Exception {
        TodoList list = new TodoList("todo", Status.pending);
        ArrayList<TodoList> todoLists = new ArrayList<>();
        todoLists.add(list);

        when(mockTodoListService.getAll()).thenReturn(todoLists);

        mockMvc.perform(get("/api/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_code_status_when_create_todoList() throws Exception {
        TodoList list = new TodoList(1,"first", Status.pending,1);
        when(mockTodoListService.create(list.getDescription())).thenReturn(list);
        mockMvc.perform(post("/api/one")
                .param("description", list.getDescription())
                .param("pending", list.getStatus().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_code_status_when_update_oneList() throws Exception {
        TodoList list = new TodoList(1,"second", Status.pending,0);

        when(mockTodoListService.update(list.getId(),list.getStatus().toString())).thenReturn(true);

        mockMvc.perform(patch("/api/status/1")
                .param("status", list.getStatus().toString()))
                .andExpect(status().is(200));
    }

    @Test
    public void should_return_200_code_status_when_delete_oneList() throws Exception {
        TodoList list = new TodoList(1,"second", Status.pending,1);
        when(mockTodoListService.deleteList(list.getId())).thenReturn(true);

        String s = objectMapper.writeValueAsString(list);
        mockMvc.perform(delete("/api/one")
                .param("id", list.getId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_code_status_when_change_index() throws Exception {
        ArrayList<TodoList> todoLists = new ArrayList<>();
        TodoList list = new TodoList("todo1", Status.pending,1);
        TodoList list1 = new TodoList("todo2", Status.pending, 2);
        TodoList list2 = new TodoList("todo3", Status.pending, 3);
        todoLists.add(list);
        todoLists.add(list1);
        todoLists.add(list2);
        when(mockTodoListService.updateIndex(any(), any())).thenReturn(todoLists);
        mockMvc.perform(patch("/api/indexOrder")
               .param("startIndex", "1")
               .param("endIndex","3"))
                .andExpect(status().isOk());
    }
}
