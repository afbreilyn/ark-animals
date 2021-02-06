import { RouteComponentProps } from '@reach/router';
import React, { useEffect, useState } from 'react';
import axios, { AxiosResponse } from 'axios';
import { StickyNote } from '../sticky-notes/sticky-note';
import { Board } from './board';
import CreateStickyNoteForm from '../sticky-notes/CreateStickyNoteForm';
import DisplayStickyNote from '../DisplayStickyNote';

interface BoardPageProps extends RouteComponentProps {
  boardId?: string
}

export const BoardPage: React.FC<BoardPageProps> = ({
  boardId,
}: BoardPageProps) => {
  const [board, setBoard] = useState({ title: '', stickyNotes: [] as StickyNote[] } as Board);
  const [stickyNoteList, setStickyNoteList] = useState([] as StickyNote[]);

  useEffect(() => {
    axios.get(`/api/boards/${boardId}`)
      .then((response: AxiosResponse<Board>) => {
        setBoard(response.data);
        setStickyNoteList(response.data.stickyNotes);
      })
      .catch((e) => {
        console.error(`error in finding that board: ${e}`);
      });
  }, [boardId]);

  const addStickyNoteToState = ((newStickyNote: StickyNote) => {
    setStickyNoteList([...stickyNoteList, newStickyNote]);
  });

  const deleteStickyNoteFun = (stickyNoteId: string) => {
    axios.delete(`/api/sticky-notes/${stickyNoteId}`)
      .then(() => {
        const newStickyNoteList = stickyNoteList.filter(
          (stickyNote: StickyNote) => stickyNoteId !== stickyNote.id,
        );
        setStickyNoteList(newStickyNoteList);
      });
  };

  return (
    <div>
      {board.title}
      <CreateStickyNoteForm
        afterwards={addStickyNoteToState}
        boardId={boardId!}
      />

      <div data-testid="sticky-note-list">
        {
          stickyNoteList.map((stickyNote) => (
            <DisplayStickyNote
              stickyNote={stickyNote}
              deleteFun={deleteStickyNoteFun}
            />
          ))
        }
      </div>
    </div>
  );
};

export default BoardPage;
