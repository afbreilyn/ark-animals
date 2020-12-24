import React from 'react';
import './App.css';

export const App = (props: any) => {
  const { children } = props;

  return (
    <div className="App">
      {children}
    </div>
  );
};

export default App;
