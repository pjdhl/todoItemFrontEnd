import React from 'react';
import './App.css';
import List from './Component/TodoList/List';
import { DndProvider } from 'react-dnd';
import createHTML5Backend from 'react-dnd-html5-backend';

function App() {
  return (
    <div className="Todo">
      <DndProvider backend={createHTML5Backend}>
        <header className="container">
          <h1 className="header">Todo list</h1>
          <List></List>
        </header>
      </DndProvider>
    </div>
  );
}

export default App;
