import { RouteComponentProps } from "@reach/router";
import React, { useEffect, useState } from "react";
import { StickyNote } from "../sticky-notes/sticky-note";
import axios, { AxiosResponse } from "axios";
import { Board } from "./board";
import StickyNoteForm from "../sticky-notes/StickyNoteForm";

interface BoardPageProps extends RouteComponentProps {
  boardId?: string
}

export const BoardPage = ({
                            boardId
                          }: BoardPageProps) => {
  const [board, setBoard] = useState({ title: '', stickyNotes: [] as StickyNote[] } as Board);
  const [stickyNoteList, setStickyNoteList] = useState([] as StickyNote[]);

  useEffect(() => {
    axios.get(`/api/boards/${boardId}`)
      .then((response: AxiosResponse<Board>) => {
        setBoard(response.data);
      })
      .catch((e) => {
        console.log(`error in finding that board: ${e}`);
      });
  }, []);

  const addStickyNoteToState = ((newStickyNote: StickyNote) => {
    setStickyNoteList([...stickyNoteList, newStickyNote]);
  });

  return (
    <div>
      {board.title}
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
        {
          board.stickyNotes && board.stickyNotes.map(sticky => (
            <div key={sticky.content}>
              {sticky.content}
            </div>
          ))
        }
      </div>
    </div>
  );
};

export default BoardPage;