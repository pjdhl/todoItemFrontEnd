import React, { useState, useEffect } from 'react';
import './App.css';
import ListComponent from './Component/TodoList/List';
import { DndProvider } from 'react-dnd';
import createHTML5Backend from 'react-dnd-html5-backend';
import axios from 'axios';
import { BrowserRouter } from "react-router-dom";


const App = () => {
  const [todoList, setTodoList] = useState([]);
  const [addValue, setAddValue] = useState("");

  useEffect(() => {
    getTodoList();
  }, [])
  const getTodoList = () => {
    axios.get("http://localhost:8080/api/todo/list")
      .then((res) => {
        setTodoList(res.data)
      })
  }
  const saveTodoList = (name) => {
    axios({
      method: 'post',
      url: 'http://localhost:8080/api/todo/list',
      params: {
        name: name,
      }
    }).then((res) => {
      getTodoList();
    }).catch((error) => {
      console.log(error)
    });
  }
  const changeText = (e) => {
    setAddValue(e.target.value)
  }
  const addTodoList = () => {
    saveTodoList(addValue);
    setAddValue("");

  }
  const deleteTodoList = (index) => {
    axios({
      method: 'delete',
      url: `${'http://localhost:8080/api/todo/list/' + index}`
    }).then((res) => {
      getTodoList();
    }).catch((error) => {
      console.log(error)
    });
  }
  const List = todoList.map((item, index) => {
    const sortNumber = (a, b) => {
      return a.indexOrder - b.indexOrder;

    }
    return (
      <div key={index} className="List">
        <ListComponent
          title={item.name}
          items={item.todoItem}
          listId={item.id}
          refresh={() => { getTodoList() }}
          remove={() => { deleteTodoList(item.id) }}
        >
        </ListComponent>
      </div>
    )
  })
  return (
    <div className="Todo">
      <BrowserRouter>
        <DndProvider backend={createHTML5Backend}>
          <header className="container">
            <h1 className="header">Todo list</h1>
            <input type="text" onChange={changeText} value={addValue} className="add-list" placeholder="add a new todo list..." />
            <button className="addBtn" onClick={addTodoList}>添加TodoList</button>
          </header>
          <main className="main">
            <ul className="main-ul">{List}</ul>
          </main>
        </DndProvider>
      </BrowserRouter>
    </div>
  );
}

export default App;
