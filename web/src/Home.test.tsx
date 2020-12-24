import React from 'react';
import { fireEvent, render } from '@testing-library/react';
import { navigate } from '@reach/router';
import axios from 'axios';
import { act } from 'react-dom/test-utils';
import Home from './Home';

function flushPromises(): Promise<any> {
  return new Promise((resolve) => setImmediate(resolve));
}

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

jest.mock('@reach/router', () => ({
  navigate: jest.fn(),
}));

describe('<Home />', () => {
  it('displays a name (smoke test)', async () => {
    mockedAxios.get.mockResolvedValue({ data: [] });

    const { getByText } = render(<Home />);
    await act(flushPromises);

    expect(getByText('Make a board to put sticky notes on!')).toBeInTheDocument();
  });

  it('displays a form to make a new board', () => {
    const { queryByTestId } = render(<Home />);

    expect(queryByTestId('new-board-form')).toBeTruthy();
  });

  it('navigates to a new page when it can create a board', async () => {
    mockedAxios.post.mockResolvedValue({ data: { title: '2x4', id: 'meowmeowmeow' } });

    const { getByTestId, getByText } = render(<Home />);
    await act(flushPromises);

    const inputEl = getByTestId('board-title-input') as HTMLInputElement;

    fireEvent.change(inputEl, { target: { value: '2x4' } });
    fireEvent.click(getByTestId('submit-button'));

    await act(flushPromises);

    expect(navigate).toHaveBeenCalledTimes(1);
    expect(navigate).toHaveBeenCalledWith('/boards/meowmeowmeow');
  });

  it('shows an error if the board does not properly submit', () => {

  });
});
