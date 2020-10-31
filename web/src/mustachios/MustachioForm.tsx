import React, { FormEvent } from "react";
import axios from "axios";
import { Mustachio } from "./mustachio";

interface MustachioFormProps {
  afterwards: (arg: Mustachio) => void
}

export const MustachioForm = ({
                                afterwards
                              }: MustachioFormProps
) => {
  const mySubmit = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    e.persist();

    const els = e.currentTarget.elements;
    axios.post('/api/mustachios', {
      firstName: (els.namedItem('sticky-text') as HTMLInputElement).value
    })
      .then((response) => {
        afterwards(response.data);
        (e.currentTarget as HTMLFormElement).reset();
        // ^ need to TDD this.... i can't get it to work :(
      })
      .catch((err) => {
        console.log(err);
      });
  };


  return (
    <form
      data-testid="new-card-form"
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

export default MustachioForm;