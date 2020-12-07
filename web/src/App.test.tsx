import React from 'react';
import { fireEvent, render, within } from '@testing-library/react';
import App from './App';

import axios from 'axios';
import { act } from "react-dom/test-utils";

function flushPromises(): Promise<any> {
  return new Promise(resolve => setImmediate(resolve));
}

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('<App />', () => {
  it('displays a name (smoke test)', async () => {
    mockedAxios.get.mockResolvedValue({ data: [] });

    const { getByText } = render(<App/>);
    await act(flushPromises);

    expect(getByText('Make a board to put sticky notes on!')).toBeInTheDocument();
  });
});
