import React, { useEffect, useState } from 'react';
import './App.css';
import MustachioForm from "./mustachios/MustachioForm";
import { Mustachio } from "./mustachios/mustachio";
import axios, { AxiosResponse } from "axios";


export const App = () => {
  const [mustachioList, setMustachioList] = useState([] as Mustachio[]);

  const setDatMustachio = ((meow: Mustachio) => {
    setMustachioList([...mustachioList, meow]);
  })

  useEffect(() => {
    axios.get('/api/mustachios')
      .then((response: AxiosResponse<Mustachio[]>) => {
        setMustachioList(response.data);
      })
      .catch((e) => {
        console.log(`error in get call!: ${e}`);
      });
  }, []);

  return (
    <div className="App">
      <h1>cards!</h1>
      <MustachioForm
        afterwards={setDatMustachio}
      />
      <div data-testid="mustachio-list">
        {
          mustachioList.map(mustachio => (
            <div key={mustachio.firstName}>
              {mustachio.firstName}
            </div>
          ))
        }
      </div>
    </div>
  );
};

export default App;
