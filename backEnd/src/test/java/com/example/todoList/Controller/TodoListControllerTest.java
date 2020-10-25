package com.example.todoList.Controller;

import com.example.todoList.Service.TodoListService;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoItem;
import com.example.todoList.domain.TodoList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TodoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoListService mockTodoListService;

    private TodoList todoList;

    @Before
    public void setUp() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setDescription("todo");
        todoItem.setStatus(Status.pending);
        todoItem.setIndexOrder(0);
        todoItem.setCreateTime(new Date());
        ArrayList<TodoItem> todoItems = new ArrayList<>();
        todoItems.add(todoItem);
        todoList = new TodoList("todoList", todoItems);
    }

    @Test
    public void should_return_200_when_create_todoList() throws Exception {

        mockMvc.perform(post("/api/todo/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("name", todoList.getName()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_when_delete_todoList() throws Exception {

        todoList.setId(1);
        when(mockTodoListService.delete(todoList.getId())).thenReturn(true);

        mockMvc.perform(delete("/api/todo/list/"+todoList.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_404_when_delete_todoList_with_error_id() throws Exception {
        todoList.setId(1);
        when(mockTodoListService.delete(todoList.getId())).thenReturn(false);

        mockMvc.perform(delete("/api/todo/list/"+todoList.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_200_when_get_all_todoList() throws Exception {

        mockMvc.perform(get("/api/todo/list"))
                .andExpect(status().isOk());
    }
}
