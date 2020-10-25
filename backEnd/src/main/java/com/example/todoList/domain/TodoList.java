package com.example.todoList.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "todoList")
@Data
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String Name;

    //mappedBy是指集合中的todoItem通过todoList属性与list关联，mappedBy是代表一方要维护关系，默认会维护，如果指定值，则代表放弃维护
    @OneToMany(mappedBy = "todoList",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @OrderBy("indexOrder ASC")
//    @JoinColumn(name = "todo_list_id")//指定集合中的todoItem是通过外键todo_list_id与list表关联
    private List<TodoItem> todoItem = new ArrayList<>();

    public TodoList() {
    }

    public TodoList(String name) {
        Name = name;
    }

    public TodoList(String name, List<TodoItem> todoItem) {
        Name = name;
        this.todoItem = todoItem;
    }

}
