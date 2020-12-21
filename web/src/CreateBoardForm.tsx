import React, { FormEvent } from 'react';
import { navigate } from "@reach/router";
import axios from "axios";

export const CreateBoardForm = () => {
  const submitBoardForm = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    e.persist();

    const els = e.currentTarget.elements;
    axios.post('/api/boards', {
      title: (els.namedItem('board-title') as HTMLInputElement).value,
      stickyNotes: [],
    })
      .then((response) => {
        // (e.target as HTMLFormElement).reset();
        // afterwards(response.data);
        navigate(`/boards/${response.data.id}`)
      })
      .catch((err) => {
        // console.log(err);
      });
  };

  return (
    <form
      data-testid="new-board-form"
      onSubmit={e => submitBoardForm(e)}
    >
      <label htmlFor="board-title">Make a board</label>
      <input
        id="board-title"
        name="board-title"
        data-testid="board-title-input"
      />
      <button type="submit" data-testid="submit-button">Submit</button>
    </form>
  );
};

export default CreateBoardForm;

