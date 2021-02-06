import React from 'react';
import { fireEvent, render } from '@testing-library/react';

import axios from 'axios';
import { act } from 'react-dom/test-utils';
import CreateStickyNoteForm from './CreateStickyNoteForm';
import { StickyNote } from './sticky-note';

function flushPromises(): Promise<any> {
  return new Promise((resolve) => setImmediate(resolve));
}

jest.spyOn(console, 'error');
jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;
const afterwards = jest.fn();

describe('<CreateStickyNoteForm />', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it('displays a text input and label', () => {
    const { getByLabelText, getByTestId } = render(<CreateStickyNoteForm afterwards={afterwards} boardId="whatever" />);

    expect(getByLabelText('Sticky note text')).toBeInTheDocument();
    expect(getByTestId('sticky-text-input')).toBeTruthy();
  });

  it('can submit and clear the form', async () => {
    const myStickyNote: StickyNote = {
      id: '5',
      content: 'mustachio',
      boardId: 'board-id',
    };
    mockedAxios.post.mockResolvedValue({ data: myStickyNote });

    const { getByTestId } = render(<CreateStickyNoteForm afterwards={afterwards} boardId="board-id" />);
    const inputEl = getByTestId('sticky-text-input') as HTMLInputElement;

    fireEvent.change(inputEl, { target: { value: 'mustachio' } });
    fireEvent.click(getByTestId('submit-button'));

    await act(flushPromises);
    expect(mockedAxios.post).toHaveBeenCalledWith(
      '/api/sticky-notes',
      myStickyNote,
    );
    expect(afterwards).toHaveBeenCalledWith(myStickyNote);
    expect(inputEl.value).toBe('');
  });

  it('logs an error if one is returned', async () => {
    const err = {
      status: 422,
      response: { message: 'er mer gerd!' },
    };

    mockedAxios.post.mockRejectedValueOnce(err);

    const { getByTestId } = render(<CreateStickyNoteForm afterwards={afterwards} boardId="swag" />);
    const inputEl = getByTestId('sticky-text-input') as HTMLInputElement;

    fireEvent.change(inputEl, { target: { value: 'mustachio' } });
    fireEvent.click(getByTestId('submit-button'));

    await act(flushPromises);

    expect(afterwards).not.toHaveBeenCalled();

    expect(console.error).toHaveBeenCalledWith(err);
  });
});
