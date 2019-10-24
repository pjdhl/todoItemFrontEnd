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


    @Test
    public void should_return_list_when_get_all() {
        TodoItem list1 = new TodoItem("todo1", Status.pending);
        TodoItem list2 = new TodoItem("todo2", Status.pending);
        TodoItem list3 = new TodoItem("todo3", Status.pending);

        todoItemRepository.save(list1);
        todoItemRepository.save(list2);
        todoItemRepository.save(list3);

        List<TodoItem> getAll = todoItemService.getAll();

        assertEquals("todo1", getAll.get(0).getDescription());
        assertEquals(3, getAll.size());
    }

    @Test
    public void should_status_is_pending_and_index_order_from_0_start_when_add_item() {
        String todo_one = "todo one";
        TodoItem createList = todoItemService.create(todo_one);

        assertEquals(todo_one,createList.getDescription());
        assertEquals(Status.pending, createList.getStatus());
        assertEquals(0, createList.getIndexOrder());
    }
    @Test
    public void should_add_order_index_from_the_end_when_add_item() {
        TodoItem list1 = new TodoItem(1,"homework1", Status.pending,0);
        TodoItem list2 = new TodoItem(2,"homework2", Status.pending,1);

        todoItemRepository.save(list1);
        todoItemRepository.save(list2);

        String add_description = "add todoList3";
        TodoItem createList = todoItemService.create(add_description);

        assertEquals(add_description,createList.getDescription());
        assertEquals(list1.getStatus(), createList.getStatus());
        assertEquals(3, todoItemRepository.findAll().size());
        assertEquals(2, createList.getIndexOrder());
    }
    @Test
    public void should_update_index_order_when_change_list_order() {
        TodoItem todo1 = todoItemService.create("todo1");
        TodoItem todo2 = todoItemService.create("todo2");
        TodoItem todo3 = todoItemService.create("todo3");

        todoItemService.updateIndex(2, 0);

        assertEquals(1, todo1.getIndexOrder());
        assertEquals(2, todo2.getIndexOrder());
        assertEquals(0, todo3.getIndexOrder());
    }


    @Test
    public void should_not_update_index_order_when_change_order_index_the_same() {
        TodoItem todo1 = todoItemService.create("todo1");
        TodoItem todo2 = todoItemService.create("todo2");
        TodoItem todo3 = todoItemService.create("todo3");

        todoItemService.updateIndex(2, 2);

        assertEquals(0,todo1.getIndexOrder());
        assertEquals(1, todo2.getIndexOrder());
        assertEquals(2, todo3.getIndexOrder());
    }


    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_order_index_has_more_than_list_size() {
        TodoItem todo1 = todoItemService.create("todo1");
        TodoItem todo2 = todoItemService.create("todo2");
        TodoItem todo3 = todoItemService.create("todo3");

        todoItemService.updateIndex(4, 1);
        assertEquals(0, todo1.getIndexOrder());
        assertEquals(1, todo2.getIndexOrder());
        assertEquals(2, todo3.getIndexOrder());
    }

    @Test
    public void should_return_true_and_index_order_change_when_delete_list() {
        TodoItem todo1 = todoItemService.create("todo1");
        TodoItem todo2 = todoItemService.create("todo2");
        TodoItem todo3 = todoItemService.create("todo3");

        todoItemService.delete(todo1.getId());

        Optional<TodoItem> byIdList = todoItemRepository.findById(todo1.getId());
        assertEquals(Optional.empty(), byIdList);

        List<TodoItem> all = todoItemRepository.findAll();
        assertEquals(2, all.size());

        assertEquals(0, todo2.getIndexOrder());
        assertEquals(1, todo3.getIndexOrder());
    }
}
