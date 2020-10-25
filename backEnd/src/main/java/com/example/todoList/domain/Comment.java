package com.example.todoList.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private String text;

    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "comment_item_id")
    @JsonProperty("comment_item_id")
    @JsonIgnore
    private TodoItem todoItem;
}

