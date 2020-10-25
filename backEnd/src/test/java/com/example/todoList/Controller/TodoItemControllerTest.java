package com.example.todoList.Controller;


import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.dao.TodoListRepository;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoItem;
import com.example.todoList.domain.TodoList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TodoItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Test
    public void should_return_200_code_status_when_get_all_list() throws Exception {

        mockMvc.perform(get("/api/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_code_status_when_create_todoList() throws Exception {
        TodoItem item = new TodoItem();
        item.setDescription("todo");
        TodoList todoList = new TodoList("todo");
        todoListRepository.save(todoList);

        mockMvc.perform(post("/api/one")
                .param("description", item.getDescription())
                .param("todoListId", todoList.getId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_code_status_when_update_oneList() throws Exception {
        TodoItem item = new TodoItem();
        item.setStatus(Status.pending);

        todoItemRepository.save(item);
        mockMvc.perform(patch("/api/status/1")
                .param("status", item.getStatus().toString()))
                .andExpect(status().is(200));
    }

    @Test
    public void should_return_200_code_status_when_delete_oneList() throws Exception {
        TodoItem item = new TodoItem();
        TodoList todoList = new TodoList();
        todoListRepository.save(todoList);
        item.setTodoList(todoList);
        todoItemRepository.save(item);
        mockMvc.perform(delete("/api/one")
                .param("id", item.getId().toString()))
                .andExpect(status().isOk());
    }

//    @Test
//    public void should_return_200_code_status_when_change_index() throws Exception {
//        ArrayList<TodoItem> todoItemList = new ArrayList<>();
//        TodoItem list = new TodoItem();
//        TodoItem list1 = new TodoItem();
//        TodoItem list2 = new TodoItem();
//
//        list.setIndexOrder(1);
//        list1.setIndexOrder(2);
//        list2.setIndexOrder(3);
//
//        todoItemList.add(list);
//        todoItemList.add(list1);
//        todoItemList.add(list2);
//        todoItemRepository.saveAll(todoItemList);
//
//        mockMvc.perform(patch("/api/indexOrder")
//               .param("startIndex", "1")
//               .param("endIndex","3"))
//                .andExpect(status().isOk());
//    }
}
