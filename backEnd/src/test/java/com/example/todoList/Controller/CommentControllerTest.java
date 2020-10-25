package com.example.todoList.Controller;


import com.example.todoList.Service.CommentService;
import com.example.todoList.dao.CommentRepository;
import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.domain.Comment;
import com.example.todoList.domain.TodoItem;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository  commentRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;

    @MockBean
    private CommentService mockCommentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_return_200_when_get_all_comments_with_todoItemId() throws Exception {

        mockMvc.perform(get("/api/2/comments"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_200_when_create_comment_with_todoItemId() throws Exception {

        TodoItem item = new TodoItem();
        TodoItem saveItem = todoItemRepository.save(item);

        Comment comment = new Comment();
        comment.setText("comment");

        String value = objectMapper.writeValueAsString(comment);
        when(mockCommentService.create(saveItem.getId(), comment)).thenReturn(comment);
        mockMvc.perform(post("/api/"+saveItem.getId()+"/comments")
                .content(value)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
//
//    @Test
//    public void should_return_200_when_delete_comment_with_id() throws Exception {
//
//        mockMvc.perform(delete("/api/2/comment"))
//                .andExpect(status().isOk());
//    }
}
