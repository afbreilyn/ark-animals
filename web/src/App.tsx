import React from 'react';
import './App.css';


export const App = (props: any) => {
  return (
    <div className="App">
      {props.children}
      {/*<h1>Make a board to put sticky notes on!</h1>*/}

      {/*<CreateBoardForm/>*/}
    </div>
  );
};

export default App;
