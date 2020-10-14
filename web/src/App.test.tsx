import React from 'react'
import { render, fireEvent } from '@testing-library/react'
import App from './App'

import axios from 'axios';
import {act} from "react-dom/test-utils";

function flushPromises(): Promise<any> {
  return new Promise(resolve => setImmediate(resolve));
}

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('<App />', () => {
  it('displays a name (smoke test)', () => {
    const { getByText } = render(<App/>)
    expect(getByText('cards!')).toBeInTheDocument()
  })

  describe('the form', () => {
    it('displays a form', () => {
      const { queryByTestId } = render(<App/>)

      expect(queryByTestId('new-card-form')).toBeTruthy()
    })

    it('displays a text input and label', () => {
      const { getByLabelText, getByTestId } = render(<App/>)

      expect(getByLabelText('Sticky note text')).toBeInTheDocument()
      expect(getByTestId('sticky-text-input')).toBeTruthy()
    })

    it('can submit the form', async () => {
      mockedAxios.get.mockResolvedValue({ data: "hello from axios" });

      const { getByTestId, getByText } = render(<App/>)
      const inputEl = getByTestId('sticky-text-input') as HTMLInputElement

      inputEl.value = 'just some text'

      fireEvent.click(getByTestId('submit-button'))

      await act(flushPromises);

      expect(getByText("hello from axios", {exact: false})).toBeInTheDocument();
    })
  })
})
