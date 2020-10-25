package com.example.todoList.Service;

import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.dao.TodoListRepository;
import com.example.todoList.domain.Status;
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
    private TodoListRepository todoListRepository;


    @Test
    public void should_return_list_when_get_all() {
        TodoList todoList = new TodoList("todolisttodoListSave.getId()");
        todoList.setId(1);
        Integer id = todoListRepository.save(todoList).getId();
        TodoList todoListSave = todoListRepository.findById(id).get();

        todoItemService.create("todo1",todoListSave.getId());
        todoItemService.create("todo2",todoListSave.getId());
        todoItemService.create("todo3",todoListSave.getId());

        List<TodoItem> getAll = todoItemService.getAll();

        assertEquals("todo1", getAll.get(0).getDescription());
        assertEquals(3, getAll.size());
    }

    @Test
    public void should_status_is_pending_and_index_order_from_0_start_and_have_create_time_when_add_item() {
        String todo_one = "todo one";
        TodoList todoList = new TodoList("todolisttodo");
        todoList.setId(2);
        Integer id = todoListRepository.save(todoList).getId();
        TodoList todoListSave = todoListRepository.findById(id).get();
        TodoItem createList = todoItemService.create(todo_one,todoListSave.getId());

        assertEquals(todo_one,createList.getDescription());
        assertEquals(Status.pending, createList.getStatus());
        assertEquals(0, createList.getIndexOrder());
        assertNotNull(createList.getCreateTime());
    }
    @Test
    public void should_add_order_index_from_the_end_when_add_item() {
        TodoList todoList = new TodoList("todolisttodo");
        todoList.setId(2);
        Integer id = todoListRepository.save(todoList).getId();
        TodoList todoListSave = todoListRepository.findById(id).get();
        TodoItem list1 = todoItemService.create("todo1",todoListSave.getId());
        TodoItem list2 = todoItemService.create("todo2",todoListSave.getId());

        String add_description = "add todoList3";
        TodoItem createList = todoItemService.create(add_description,todoListSave.getId());

        assertEquals(add_description,createList.getDescription());
        assertEquals(list1.getStatus(), createList.getStatus());
        assertEquals(3, todoItemRepository.findAll().size());
        assertEquals(2, createList.getIndexOrder());
    }
    @Test
    public void should_update_index_order_when_change_list_order() {
        TodoList todoList = new TodoList("todolisttodo");
        todoList.setId(2);
        Integer id = todoListRepository.save(todoList).getId();
        TodoList todoListSave = todoListRepository.findById(id).get();
        TodoItem todo1 = todoItemService.create("todo1",todoListSave.getId());
        TodoItem todo2 = todoItemService.create("todo2",todoListSave.getId());
        TodoItem todo3 = todoItemService.create("todo3",todoListSave.getId());

        todoItemService.updateIndex(2, 0);

        assertEquals(1, todo1.getIndexOrder());
        assertEquals(2, todo2.getIndexOrder());
        assertEquals(0, todo3.getIndexOrder());
    }


    @Test
    public void should_not_update_index_order_when_change_order_index_the_same() {
        TodoList todoList = new TodoList("todolisttodo");
        todoList.setId(2);
        Integer id = todoListRepository.save(todoList).getId();
        TodoList todoListSave = todoListRepository.findById(id).get();
        TodoItem todo1 = todoItemService.create("todo1",todoListSave.getId());
        TodoItem todo2 = todoItemService.create("todo2",todoListSave.getId());
        TodoItem todo3 = todoItemService.create("todo3",todoListSave.getId());

        todoItemService.updateIndex(2, 2);

        assertEquals(0,todo1.getIndexOrder());
        assertEquals(1, todo2.getIndexOrder());
        assertEquals(2, todo3.getIndexOrder());
    }


    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_order_index_has_more_than_list_size() {
        TodoList todoList = new TodoList("todolisttodo");
        todoList.setId(2);
        Integer id = todoListRepository.save(todoList).getId();
        TodoList todoListSave = todoListRepository.findById(id).get();
        TodoItem todo1 = todoItemService.create("todo1",todoListSave.getId());
        TodoItem todo2 = todoItemService.create("todo2",todoListSave.getId());
        TodoItem todo3 = todoItemService.create("todo3",todoListSave.getId());

        todoItemService.updateIndex(4, 1);
        assertEquals(0, todo1.getIndexOrder());
        assertEquals(1, todo2.getIndexOrder());
        assertEquals(2, todo3.getIndexOrder());
    }

    @Test
    public void should_return_true_and_index_order_change_when_delete_list() {
        TodoList todoList = new TodoList("todolisttodo");
        todoList.setId(2);
        Integer id = todoListRepository.save(todoList).getId();
        TodoList todoListSave = todoListRepository.findById(id).get();
        TodoItem todo1 = todoItemService.create("todo1",todoListSave.getId());
        TodoItem todo2 = todoItemService.create("todo2",todoListSave.getId());
        TodoItem todo3 = todoItemService.create("todo3",todoListSave.getId());

        todoItemService.delete(todo1.getId());

        Optional<TodoItem> byIdList = todoItemRepository.findById(todo1.getId());
        assertEquals(Optional.empty(), byIdList);

        List<TodoItem> all = todoItemRepository.findAll();
        assertEquals(2, all.size());


        assertEquals(0, todo2.getIndexOrder());
        assertEquals(1, todo3.getIndexOrder());

        assertEquals(0, todoListSave.getTodoItem().size());

    }



    @Test
    public void should_delete_todo_item() {
        TodoList todoList = new TodoList("todolisttodo");
        todoList.setId(2);
        Integer id = todoListRepository.save(todoList).getId();
        TodoList todoListSave = todoListRepository.findById(id).get();
        TodoItem todo1 = todoItemService.create("todo1",todoListSave.getId());

        todoItemService.delete(todo1.getId());
        assertFalse(todoItemRepository.findById(todo1.getId()).isPresent());
        assertEquals(0, todoItemRepository.findAllById(new ArrayList<Integer>(){{
            add(todo1.getId());
        }}).size());
    }
}
