import React from 'react';
import { fireEvent, render } from '@testing-library/react';
import DisplayStickyNote from './DisplayStickyNote';
import { StickyNote } from './sticky-notes/sticky-note';

describe('<DisplayStickyNote />', () => {
  const sillyStickyNote: StickyNote = {
    content: 'mustachio',
    id: '8',
    boardId: '0',
  };

  const delFunc = jest.fn();

  it('displays the content', () => {
    const { getByText } = render(
      <DisplayStickyNote
        stickyNote={sillyStickyNote}
        deleteFun={delFunc}
      />,
    );

    expect(getByText('mustachio', { exact: false })).toBeInTheDocument();
  });

  it('calls the delete function with the ID', () => {
    const { queryByTestId } = render(
      <DisplayStickyNote
        stickyNote={sillyStickyNote}
        deleteFun={delFunc}
      />,
    );

    fireEvent.click(queryByTestId('delete-sticky-note') as HTMLButtonElement);

    expect(delFunc).toBeCalledWith('8');
  });
});
