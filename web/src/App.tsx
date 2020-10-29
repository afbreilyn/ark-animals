import React, { useState } from 'react';
import './App.css';
import MustachioForm from "./mustachios/MustachioForm";

export const App = () => {
  const [greeting, setGreeting] = useState("");

  return (
    <div className="App">
      <h1>cards!</h1>
      <MustachioForm
        afterwards={setGreeting}
      />
      <h1>
        {greeting}
      </h1>
    </div>
  );
};

export default App;
