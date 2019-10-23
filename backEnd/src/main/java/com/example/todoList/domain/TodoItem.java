package com.example.todoList.domain;
import javax.persistence.*;

@Entity
@Table(name = "todo")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private Status status;
    private int indexOrder;

    public int getIndexOrder() {
        return indexOrder;
    }

    public TodoList(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public TodoList() {
    }

    public TodoList(String description) {
        this.description = description;
    }

    public TodoList(Integer id, String description, Status status, int index_order) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.indexOrder = index_order;
    }

    public TodoList(String description, Status status, int indexOrder) {
        this.description = description;
        this.status = status;
        this.indexOrder = indexOrder;
    }

    public void setIndexOrder(int indexOrder) {
        this.indexOrder = indexOrder;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }


    public Integer getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

}
