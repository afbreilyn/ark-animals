import React from 'react';
import './App.css';
import { RouteComponentProps } from "@reach/router";
import BoardForm from "./boards/BoardForm";


export const Home = (_: RouteComponentProps) => {
  return (
    <div className="App">
      <h1>Make a board to put sticky notes on!</h1>

      <BoardForm/>
    </div>
  );
};

export default Home;
