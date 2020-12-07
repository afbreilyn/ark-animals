import React from 'react';
import './App.css';
import { RouteComponentProps } from "@reach/router";


export const App = (_: RouteComponentProps) => {
  return (
    <div className="App">
      <h1>Make a board to put sticky notes on!</h1>
    </div>
  );
};

export default App;
