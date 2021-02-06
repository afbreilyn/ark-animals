import React from 'react';
import { StickyNote } from './sticky-notes/sticky-note';

interface DisplayStickyNoteProps {
  stickyNote: StickyNote,
  deleteFun: (id: string) => void,
}

export const DisplayStickyNote = ({
  stickyNote,
  deleteFun,
}: DisplayStickyNoteProps) => (
  <div key={stickyNote.content}>
    {stickyNote.content}
    <button
      type="button"
      data-testid="delete-sticky-note"
      onClick={() => deleteFun(stickyNote.id)}
    >
      X
    </button>
  </div>
);

export default DisplayStickyNote;
