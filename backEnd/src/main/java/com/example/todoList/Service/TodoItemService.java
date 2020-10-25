package com.example.todoList.Service;

import com.example.todoList.dao.TodoItemRepository;
import com.example.todoList.dao.TodoListRepository;
import com.example.todoList.domain.Status;
import com.example.todoList.domain.TodoItem;
import com.example.todoList.domain.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private EntityManager entityManager;


    public List<TodoItem>  getAll() {

        List<TodoItem> allByOrderByIndexOrder = todoItemRepository.findAllByOrderByIndexOrder();
        return allByOrderByIndexOrder;
    }

     public TodoItem  create(String description,Integer todoListId) {
        int indexOrder = todoItemRepository.findAll().size();
        TodoItem item = new TodoItem();
        item.setDescription(description);
        item.setStatus(Status.pending);
        item.setIndexOrder(indexOrder);
        item.setCreateTime(new Date());
         Optional<TodoList> todoList = todoListRepository.findById(todoListId);
         if(todoList.isPresent()){
             TodoList todoList1 = todoList.get();
             item.setTodoList(todoList1);
             return todoItemRepository.save(item);
         }else{
             throw  new NullPointerException("todoList id not exist");
         }
    }


    public boolean update(Integer id,String status) {
        TodoItem one = todoItemRepository.findById(id).get();
        if (one.equals(null)) {
            return false;
        }else{
            if (status.equals(Status.pending.toString()) || status.equals(Status.finish.toString())) {
                one.setStatus(Status.valueOf(status));
                todoItemRepository.save(one);
                return true;
            }else{
                return false;
            }
        }
    }

    //TODO:遇到问题，级连删除
    public boolean delete(Integer id) {
        Optional<TodoItem> itemOption = todoItemRepository.findById(id);
        if (!itemOption.isPresent()) {
            return false;
        }else {
            TodoItem item = itemOption.get();
            int indexOrder = item.getIndexOrder();
//            todoListRepository.findById(item.getTodoListId())
//                    .get().getTodoItem()
//                    .removeIf(a -> a.getId() == item.getId());
            todoItemRepository.delete(item);
//            entityManager.remove(item);
//            entityManager.flush();
            List<TodoItem> allByOrderByIndexOrder = todoItemRepository.findAllByOrderByIndexOrder();
            for (TodoItem todoItem : allByOrderByIndexOrder) {
                if(todoItem.getIndexOrder() > indexOrder){
                    todoItem.setIndexOrder(todoItem.getIndexOrder()-1);
                    todoItemRepository.save(todoItem);
                }
            }
        }
        return true;
    }

    public List<TodoItem> updateIndex(Integer startIndex, Integer endIndex) {
        List<TodoItem> allOrderByIndexOrder = todoItemRepository.findAllByOrderByIndexOrder();
        if(startIndex < endIndex){
            changeIndexOrder(startIndex, endIndex, allOrderByIndexOrder);
        }else if(startIndex > endIndex){
            for (TodoItem todoItem: allOrderByIndexOrder) {
                if(startIndex > allOrderByIndexOrder.size() || endIndex > allOrderByIndexOrder.size()){
                    throw new IllegalArgumentException("index order has over list size");
                }else if (todoItem.getIndexOrder() == startIndex){
                    todoItem.setIndexOrder(endIndex);
                    todoItemRepository.save(todoItem);
                }else if(todoItem.getIndexOrder() >= endIndex && todoItem.getIndexOrder()<startIndex){
                    todoItem.setIndexOrder(todoItem.getIndexOrder() + 1);
                    todoItemRepository.save(todoItem);
                }
            }
        }
        return todoItemRepository.findAllByOrderByIndexOrder();
    }

    private void changeIndexOrder(Integer startIndex, Integer endIndex, List<TodoItem> allOrderByIndexOrder) {
        for (TodoItem todoItem: allOrderByIndexOrder) {
            if(startIndex > allOrderByIndexOrder.size() || endIndex > allOrderByIndexOrder.size()){
                throw new IllegalArgumentException("index order has over list size");
            }else if (todoItem.getIndexOrder() == startIndex){
                todoItem.setIndexOrder(endIndex);
                todoItemRepository.save(todoItem);
                //TODO:test
            }else if(todoItem.getIndexOrder() > startIndex && todoItem.getIndexOrder() <= endIndex){
                todoItem.setIndexOrder(todoItem.getIndexOrder() - 1);
                todoItemRepository.save(todoItem);
            }
        }
    }
}
