import React, { useEffect, useState } from 'react';
import './App.css';
import StickyNoteForm from "./sticky-notes/StickyNoteForm";
import { StickyNote } from "./sticky-notes/sticky-note";
import axios, { AxiosResponse } from "axios";


export const App = () => {
  const [stickyNoteList, setStickyNoteList] = useState([] as StickyNote[]);

  const addStickyNoteToState = ((newStickyNote: StickyNote) => {
    setStickyNoteList([...stickyNoteList, newStickyNote]);
  })

  useEffect(() => {
    axios.get('/api/mustachios')
      .then((response: AxiosResponse<StickyNote[]>) => {
        setStickyNoteList(response.data);
      })
      .catch((e) => {
        console.log(`error in get call!: ${e}`);
      });
  }, []);

  return (
    <div className="App">
      <h1>cards!</h1>
      <StickyNoteForm
        afterwards={addStickyNoteToState}
      />
      <div data-testid="sticky-note-list">
        {
          stickyNoteList.map(stickyNote => (
            <div key={stickyNote.content}>
              {stickyNote.content}
            </div>
          ))
        }
      </div>
    </div>
  );
};

export default App;
