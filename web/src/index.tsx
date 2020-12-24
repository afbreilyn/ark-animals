import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { Router } from '@reach/router';
import App from './App';
import * as serviceWorker from './serviceWorker';
import BoardPage from './boards/BoardPage';
import Home from './Home';

ReactDOM.render(
  <React.StrictMode>
    <App>
      <Router>
        <Home default path="/" />
        <BoardPage path="/boards/:boardId" />
      </Router>
    </App>
  </React.StrictMode>,
  document.getElementById('root'),
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
