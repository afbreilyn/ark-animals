import React from 'react';
import { fireEvent, render } from '@testing-library/react';

import axios from 'axios';
import { act } from "react-dom/test-utils";
import StickyNoteForm from "./StickyNoteForm";
import { StickyNote } from "./sticky-note";

function flushPromises(): Promise<any> {
  return new Promise(resolve => setImmediate(resolve));
}

jest.spyOn(console, 'log');
jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;
const afterwards = jest.fn();

describe('<StickyNoteForm />', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it('displays a text input and label', () => {
    const { getByLabelText, getByTestId } = render(<StickyNoteForm afterwards={afterwards}/>);

    expect(getByLabelText('Sticky note text')).toBeInTheDocument();
    expect(getByTestId('sticky-text-input')).toBeTruthy();
  });

  it('can submit and clear the form', async () => {
    mockedAxios.post.mockResolvedValue({ data: { content: "mustachio" } });

    const { getByTestId } = render(<StickyNoteForm afterwards={afterwards}/>);
    let inputEl = getByTestId('sticky-text-input') as HTMLInputElement;

    fireEvent.change(inputEl, { target: { value: 'mustachio' } });
    fireEvent.click(getByTestId('submit-button'));

    await act(flushPromises);

    const myStickyNote: StickyNote = {
      content: 'mustachio'
    };

    expect(mockedAxios.post).toHaveBeenCalledWith(
      "/api/sticky-notes",
      myStickyNote,
    );
    expect(afterwards).toHaveBeenCalledWith(myStickyNote);
  });

  it('logs an error if one is returned', async () => {
    const err = {
      status: 422,
      response: { message: 'er mer gerd!' },
    };

    mockedAxios.post.mockRejectedValueOnce(err);

    const { getByTestId } = render(<StickyNoteForm afterwards={afterwards}/>);
    let inputEl = getByTestId('sticky-text-input') as HTMLInputElement;

    fireEvent.change(inputEl, { target: { value: 'mustachio' } });
    fireEvent.click(getByTestId('submit-button'));

    await act(flushPromises);

    expect(afterwards).not.toHaveBeenCalled();

    expect(console.log).toHaveBeenCalledWith(err);
  });
});
