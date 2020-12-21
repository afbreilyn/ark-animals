import React, { FormEvent } from "react";
import axios from "axios";
import { StickyNote } from "./sticky-note";

interface StickyNoteFormProps {
  afterwards: (arg: StickyNote) => void,
  boardId: string
}

export const CreateStickyNoteForm = ({
                                       afterwards,
                                       boardId,
                                     }: StickyNoteFormProps
) => {
  const mySubmit = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    e.persist();

    const els = e.currentTarget.elements;
    axios.post('/api/sticky-notes', {
      content: (els.namedItem('sticky-text') as HTMLInputElement).value,
      boardId: boardId,
    })
      .then((response) => {
        (e.target as HTMLFormElement).reset();
        afterwards(response.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <form
      data-testid="new-stickynote-form"
      onSubmit={e => mySubmit(e)}
    >
      <label htmlFor="sticky-text">Sticky note text</label>
      <input
        id="sticky-text"
        name="sticky-text"
        data-testid="sticky-text-input"
      />
      <button type="submit" data-testid="submit-button">Submit</button>
    </form>
  );
};

export default CreateStickyNoteForm;