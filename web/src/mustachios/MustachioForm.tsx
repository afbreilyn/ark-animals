import React, { FormEvent } from "react";
import axios from "axios";

interface MustachioFormProps {
  afterwards: (arg: string) => void
}

export const MustachioForm = ({
                                afterwards
                              }: MustachioFormProps
) => {
  const mySubmit = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    const els = e.currentTarget.elements;
    axios.post('/api/mustachios', {
      firstName: (els.namedItem('sticky-text') as HTMLInputElement).value
    })
      .then((response) => {
        afterwards("hello from the backend: " + response.data.firstName);
      });
  };

  return (
    <form data-testid="new-card-form" onSubmit={e => mySubmit(e)}>
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