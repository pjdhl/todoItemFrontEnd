package com.example.todoList.Service;

import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TodoItemServiceTest {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private TodoItemService todoItemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_return_all_when_get() {
        TodoItem list = new TodoItem("todo", Status.pending);
        todoItemRepository.save(list);
        todoItemRepository.findAll();
        List<TodoItem> getAll = todoItemService.getAll();

        assertEquals("todo", getAll.get(0).getDescription());
    }

    @Test
    public void should_return_list_create_list() {
        TodoItem list = new TodoItem(1,"homework", Status.pending,1);
        todoItemRepository.save(list);
        TodoItem createList = todoItemService.create(list.getDescription());
        assertEquals(list.getDescription(),createList.getDescription());
        assertEquals(list.getStatus(), createList.getStatus());
    }

    @Test
    public void should_return_update_index_order_list_when_update_order_index() {
        ArrayList<TodoItem> todoLists = new ArrayList<>();
        TodoItem list = new TodoItem("todo1", Status.pending,2);
        TodoItem list1 = new TodoItem("todo2", Status.pending, 3);
        TodoItem list2 = new TodoItem("todo3", Status.pending, 4);//0
        todoLists.add(list);
        todoLists.add(list1);
        todoLists.add(list2);

        todoItemRepository.save(list);
        todoItemRepository.save(list1);
        todoItemRepository.save(list2);

        todoItemService.updateIndex(4, 2);

        TodoItem expectList = todoItemRepository.findById(list.getId()).get();

        assertEquals(3,expectList.getIndexOrder());
    }

    @Test
    public void should_return_true_and_index_order_change_when_delete_list() {
        TodoItem list = new TodoItem("todo1", Status.pending,0);
        TodoItem list1 = new TodoItem("todo2", Status.pending, 1);
        TodoItem list2 = new TodoItem("todo3", Status.pending, 2);
        TodoItem list3 = new TodoItem("todo4", Status.pending, 3);

        todoItemRepository.save(list);
        todoItemRepository.save(list1);
        todoItemRepository.save(list2);
        todoItemRepository.save(list3);


        todoItemService.delete(list.getId());

        Optional<TodoItem> byIdList = todoItemRepository.findById(list.getId());
        List<TodoItem> all = todoItemRepository.findAll();
        assertEquals(Optional.empty(), byIdList);
        assertEquals(3, all.size());

        TodoItem expectList = todoItemRepository.findById(list1.getId()).get();
        assertEquals(0, expectList.getIndexOrder());
    }
}
