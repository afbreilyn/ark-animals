import axios from 'axios';
import { act } from 'react-dom/test-utils';
import React from 'react';
import { fireEvent, render, within } from '@testing-library/react';
import BoardPage from './BoardPage';
import { StickyNote } from '../sticky-notes/sticky-note';

function flushPromises(): Promise<any> {
  return new Promise((resolve) => setImmediate(resolve));
}

jest.spyOn(console, 'error');
jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('<BoardPage />', () => {
  it('displays a title', async () => {
    mockedAxios.get.mockResolvedValue({
      data: {
        title: 'dope title',
        stickyNotes: [],
      },
    });

    const { getByText } = render(
      <BoardPage boardId="my-cool-board-id" />,
    );

    await act(flushPromises);

    expect(mockedAxios.get).toHaveBeenCalledWith(
      '/api/boards/my-cool-board-id',
    );
    expect(getByText('dope title'));
  });

  describe('list of stickies', () => {
    beforeEach(() => {
      const stickyNote1: StickyNote = {
        id: '9',
        content: 'mando',
        boardId: '1',
      };

      const stickyNote2: StickyNote = {
        id: '7',
        content: 'baby yoda',
        boardId: '2',
      };

      mockedAxios.get.mockResolvedValue({
        data: {
          title: 'dope mustachios',
          stickyNotes: [
            stickyNote1,
            stickyNote2,
          ],
        },
      });

      mockedAxios.delete.mockResolvedValue({
        status: 200,
      });
    });

    it('displays a list of past sticky notes', async () => {
      const { queryByTestId } = render(<BoardPage boardId="my-dope-board-id" />);
      await act(flushPromises);

      const stickyNoteList = queryByTestId('sticky-note-list') as HTMLElement;

      expect(stickyNoteList).toBeTruthy();
      expect(stickyNoteList).not.toBeEmpty();

      const listOfStickyNotes = within(stickyNoteList);

      expect(listOfStickyNotes.getByText('mando', { exact: false })).toBeInTheDocument();
      expect(listOfStickyNotes.getByText('baby yoda', { exact: false })).toBeInTheDocument();
    });

    it('can remove a stickynote from the list', async () => {
      const { queryAllByTestId, queryByTestId } = render(<BoardPage boardId="102" />);
      await act(flushPromises);

      let stickyNoteList = queryByTestId('sticky-note-list') as HTMLElement;
      let listOfStickyNotes = within(stickyNoteList);
      expect(listOfStickyNotes.queryAllByTestId('delete-sticky-note').length).toBe(2);

      const x = queryAllByTestId('delete-sticky-note')[0] as HTMLButtonElement;
      fireEvent.click(x);

      await act(flushPromises);

      expect(mockedAxios.delete).toHaveBeenCalledWith(
        '/api/sticky-notes/9',
      );

      stickyNoteList = queryByTestId('sticky-note-list') as HTMLElement;
      listOfStickyNotes = within(stickyNoteList);
      expect(listOfStickyNotes.queryAllByTestId('delete-sticky-note').length).toBe(1);
      expect(listOfStickyNotes.getByText('baby yoda', { exact: false })).toBeInTheDocument();
    });

    describe('after making a new sticky note', () => {
      it('adds the sticky note to the list', async () => {
        mockedAxios.post.mockResolvedValue({ data: { content: 'ahsoka tano' } });

        const { queryByTestId, getByTestId } = render(<BoardPage boardId="my-dope-board-id" />);
        await act(flushPromises);

        const inputEl = getByTestId('sticky-text-input') as HTMLInputElement;

        fireEvent.change(inputEl, { target: { value: 'ahsoka tano' } });
        fireEvent.click(getByTestId('submit-button'));

        await act(flushPromises);

        const listOfStickyNotes = within(queryByTestId('sticky-note-list') as HTMLElement);

        expect(listOfStickyNotes.getByText('mando', { exact: false })).toBeInTheDocument();
        expect(listOfStickyNotes.getByText('baby yoda', { exact: false })).toBeInTheDocument();
        expect(listOfStickyNotes.getByText('ahsoka tano', { exact: false })).toBeInTheDocument();
      });
    });
  });

  it('displays and logs an error if the call fails', async () => {
    const err = {
      status: 404,
      response: { message: 'whelp!' },
    };

    mockedAxios.get.mockRejectedValueOnce(err);

    render(<BoardPage boardId="my-not-very-good-board-id" />);

    await act(flushPromises);

    expect(console.error).toHaveBeenCalledWith(`error in finding that board: ${err}`);
  });

  describe('the create new stickynotes form', () => {
    beforeEach(async () => {
      mockedAxios.get.mockResolvedValue({ data: { title: 'dooku', stickyNotes: [] } });
    });

    it('displays a form', async () => {
      const { queryByTestId } = render(<BoardPage boardId="id" />);
      await act(flushPromises);

      expect(queryByTestId('new-stickynote-form')).toBeTruthy();
    });

    it('can displays the returned value from the form', async () => {
      mockedAxios.post.mockResolvedValue({ data: { content: 'mustachio' } });

      const { getByTestId, getByText } = render(<BoardPage boardId="id" />);
      await act(flushPromises);

      const inputEl = getByTestId('sticky-text-input') as HTMLInputElement;

      fireEvent.change(inputEl, { target: { value: 'mustachio' } });
      fireEvent.click(getByTestId('submit-button'));

      await act(flushPromises);

      expect(getByText('mustachio', { exact: false })).toBeInTheDocument();
    });
  });
});
