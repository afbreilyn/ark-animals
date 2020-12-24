import React from 'react';
import './App.css';
import { RouteComponentProps } from '@reach/router';
import CreateBoardForm from './CreateBoardForm';

export const Home = (_: RouteComponentProps) => (
  <div className="App">
    <h1>Make a board to put sticky notes on!</h1>

    <CreateBoardForm />
  </div>
);

export default Home;
