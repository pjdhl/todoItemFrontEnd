package com.example.todoList.Service;


import com.example.todoList.dao.CommentRepository;
import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.dao.TodoListRepository;
import com.example.todoList.domain.Comment;
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

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private TodoListRepository todoListRepository;


    @Test
    public void should_return_comment_when_create_comment() {
        TodoList todoList = new TodoList();
        todoList.setName("list");
        TodoList saveList = todoListRepository.save(todoList);

        TodoItem item = new TodoItem();
        item.setStatus(Status.pending);
        item.setTodoList(saveList);
        item.setIndexOrder(0);
        item.setDescription("todoItem");
        item.setCreateTime(new Date());
        TodoItem saveItem = todoItemRepository.save(item);

        Comment comment = new Comment();
        comment.setText("comment");

        Comment saveComment = commentService.create(saveItem.getId(), comment);

        List<Comment> all = commentRepository.findAll();
        assertEquals(1,all.size());

        Comment actualComment = commentRepository.findById(saveComment.getId()).get();

        assertNotNull(actualComment);
        assertEquals(comment.getText(), actualComment.getText());
    }

    @Test
    public void should_throw_exception_when_create_comment_with_todoItem_id_not_exist() {
        Comment comment = new Comment();
        comment.setText("comment");

        assertThrows(RuntimeException.class,()->
            commentService.create(1, comment)
        );
    }

    @Test
    public void should_return_all_comments() {
        TodoList todoList = new TodoList();
        todoList.setName("list");
        TodoList saveList = todoListRepository.save(todoList);

        TodoItem item = new TodoItem();
        item.setStatus(Status.pending);
        item.setTodoList(saveList);
        item.setIndexOrder(0);
        item.setDescription("todoItem");
        item.setCreateTime(new Date());
        TodoItem saveItem = todoItemRepository.save(item);

        Comment comment = new Comment();
        comment.setText("comment");
        comment.setTodoItem(saveItem);
        commentRepository.save(comment);

        List<Comment> allByTodoItemId = commentService.getAllByTodoItemId(saveItem.getId());

        assertEquals(1, allByTodoItemId.size());

    }

    @Test
    public void should_return_true_when_delete_comment_by_id() {
        TodoList todoList = new TodoList();
        todoList.setName("list");
        TodoList saveList = todoListRepository.save(todoList);

        TodoItem item = new TodoItem();
        item.setStatus(Status.pending);
        item.setTodoList(saveList);
        item.setIndexOrder(0);
        item.setDescription("todoItem");
        item.setCreateTime(new Date());
        TodoItem saveItem = todoItemRepository.save(item);

        Comment comment = new Comment();
        comment.setText("comment");
        comment.setTodoItem(saveItem);
        Comment saveComment = commentRepository.save(comment);

        boolean delete = commentService.delete(saveComment.getId());


        assertEquals( true,delete);
        assertEquals(Optional.empty(), commentRepository.findById(saveComment.getId()));
    }
}
