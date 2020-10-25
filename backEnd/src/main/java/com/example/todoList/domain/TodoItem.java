package com.example.todoList.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "todo")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private Status status;
    private int indexOrder;
    private Date createTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "todo_list_id")
    private TodoList todoList;

    @OneToMany(mappedBy = "todoItem",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comment = new ArrayList<>();

    public Integer getTodoListId() {
        return todoList.getId();
    }
}

