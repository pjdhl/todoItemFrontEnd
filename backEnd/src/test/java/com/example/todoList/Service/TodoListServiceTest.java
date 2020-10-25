package com.example.todoList.Service;

import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.dao.TodoListRepository;
import com.example.todoList.domain.TodoItem;
import com.example.todoList.domain.TodoList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
public class TodoListServiceTest {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;


    @Test
    public void should_return_all_when_create_todo_list() {
        TodoList actual = todoListService.create("todoList1");
        assertEquals("todoList1", actual.getName());
        assertEquals(1, todoListRepository.findAll().size());
    }

    @Test
    public void should_return_true_delete_todo_list() {
        TodoList expect = new TodoList("todoList1");
        TodoItem item = new TodoItem();
        expect.setTodoItem(new ArrayList<TodoItem>(){{add(item);}});
        TodoList actual = todoListRepository.save(expect);

        List<TodoItem> allitem = todoItemRepository.findAll();
        assertEquals(1, allitem.size());

        todoListService.delete(actual.getId());
        List<TodoList> all = todoListRepository.findAll();

        assertEquals(0,all.size());
        List<TodoItem> allitem1 = todoItemRepository.findAll();
        assertEquals(0, allitem1.size());
    }

    @Test
    public void should_return_all_when_get_todo_list() {
        TodoList expect = new TodoList("todoList1");
        TodoList actual = todoListService.create(expect.getName());
        List<TodoList> all = todoListService.getAll();
        assertEquals(1, all.size());
        assertEquals(actual.getName(), all.get(0).getName());
    }
}
