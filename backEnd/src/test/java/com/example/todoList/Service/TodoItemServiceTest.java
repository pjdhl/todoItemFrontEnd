package com.example.todoList.Service;

import com.example.todoList.dao.TodoListRepository;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoList;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import org.junit.Assert;
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
public class TodoListServiceTest{

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_return_all_when_get() {
        TodoList list = new TodoList("todo", Status.pending);
        todoListRepository.save(list);
        todoListRepository.findAll();
        List<TodoList> getAll = todoListService.getAll();

        assertEquals("todo", getAll.get(0).getDescription());
    }

    @Test
    public void should_return_list_create_list() {
        TodoList list = new TodoList(1,"homework", Status.pending,1);
        todoListRepository.save(list);
        TodoList createList = todoListService.create(list.getDescription());
        assertEquals(list.getDescription(),createList.getDescription());
        assertEquals(list.getStatus(), createList.getStatus());
    }

    @Test
    public void should_return_update_index_order_list_when_update_order_index() {
        ArrayList<TodoList> todoLists = new ArrayList<>();
        TodoList list = new TodoList("todo1", Status.pending,2);
        TodoList list1 = new TodoList("todo2", Status.pending, 3);
        TodoList list2 = new TodoList("todo3", Status.pending, 4);//0
        todoLists.add(list);
        todoLists.add(list1);
        todoLists.add(list2);

        todoListRepository.save(list);
        todoListRepository.save(list1);
        todoListRepository.save(list2);

        todoListService.updateIndex(4, 2);

        TodoList expectList = todoListRepository.findById(list.getId()).get();

        assertEquals(3,expectList.getIndexOrder());
    }

    @Test
    public void should_return_true_and_index_order_change_when_delete_list() {
        TodoList list = new TodoList("todo1", Status.pending,0);
        TodoList list1 = new TodoList("todo2", Status.pending, 1);
        TodoList list2 = new TodoList("todo3", Status.pending, 2);
        TodoList list3 = new TodoList("todo4", Status.pending, 3);

        todoListRepository.save(list);
        todoListRepository.save(list1);
        todoListRepository.save(list2);
        todoListRepository.save(list3);


        todoListService.deleteList(list.getId());

        Optional<TodoList> byIdList = todoListRepository.findById(list.getId());
        List<TodoList> all = todoListRepository.findAll();
        assertEquals(Optional.empty(), byIdList);
        assertEquals(3, all.size());

        TodoList expectList = todoListRepository.findById(list1.getId()).get();
        assertEquals(0, expectList.getIndexOrder());
    }
}
