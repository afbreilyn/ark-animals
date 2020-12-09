import React from 'react';
import { fireEvent, render } from '@testing-library/react';

import axios from 'axios';
import { act } from "react-dom/test-utils";
import BoardForm from "./BoardForm";
import { Board } from "./board";
import { navigate } from '@reach/router';

function flushPromises(): Promise<any> {
  return new Promise(resolve => setImmediate(resolve));
}

jest.spyOn(console, 'log');
jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

jest.mock('@reach/router', () => ({
  navigate: jest.fn(),
}));

describe('<BoardForm />', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it('displays a text input and label', () => {
    const { getByLabelText, getByTestId } = render(<BoardForm/>);

    expect(getByLabelText('Make a board')).toBeInTheDocument();
    expect(getByTestId('board-title-input')).toBeTruthy();
  });

  it('can submit the form and navigate away', async () => {
    mockedAxios.post.mockResolvedValue({
      data: {
        title: "razor crest",
        id: 'tk421',
        stickyNotes: [],
      }
    });

    const { getByTestId } = render(<BoardForm/>);
    let inputEl = getByTestId('board-title-input') as HTMLInputElement;

    fireEvent.change(inputEl, { target: { value: 'razor crest' } });
    fireEvent.click(getByTestId('submit-button'));

    await act(flushPromises);

    const myBoard: Board = {
      title: 'razor crest',
      stickyNotes: []
    };

    expect(mockedAxios.post).toHaveBeenCalledWith(
      "/api/boards",
      myBoard,
    );

    expect(navigate).toHaveBeenCalledTimes(1);
    expect(navigate).toHaveBeenCalledWith('/boards/tk421');
  });

  // it('logs an error if one is returned', async () => {
  //   const err = {
  //     status: 422,
  //     response: { message: 'er mer gerd!' },
  //   };
  //
  //   mockedAxios.post.mockRejectedValueOnce(err);
  //
  //   const { getByTestId } = render(<StickyNoteForm afterwards={afterwards}/>);
  //   let inputEl = getByTestId('sticky-text-input') as HTMLInputElement;
  //
  //   fireEvent.change(inputEl, { target: { value: 'mustachio' } });
  //   fireEvent.click(getByTestId('submit-button'));
  //
  //   await act(flushPromises);
  //
  //   expect(afterwards).not.toHaveBeenCalled();
  //
  //   expect(console.log).toHaveBeenCalledWith(err);
  // });
});
