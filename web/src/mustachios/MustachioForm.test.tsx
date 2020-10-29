import React from 'react';
import { render, fireEvent } from '@testing-library/react';

import axios from 'axios';
import { act } from "react-dom/test-utils";
import MustachioForm from "./MustachioForm";
import { Mustachio } from "./mustachio";

function flushPromises(): Promise<any> {
  return new Promise(resolve => setImmediate(resolve));
}

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;
const afterwards = jest.fn();

describe('<MustachioForm />', () => {
  it('displays a text input and label', () => {
    const { getByLabelText, getByTestId } = render(<MustachioForm afterwards={afterwards}/>);

    expect(getByLabelText('Sticky note text')).toBeInTheDocument();
    expect(getByTestId('sticky-text-input')).toBeTruthy();
  });

  it('can submit the form', async () => {
    mockedAxios.post.mockResolvedValue({ data: { firstName: "mustachio" } });

    const { getByTestId, getByText } = render(<MustachioForm afterwards={afterwards}/>);
    const inputEl = getByTestId('sticky-text-input') as HTMLInputElement;

    inputEl.value = 'mustachio';

    fireEvent.click(getByTestId('submit-button'));

    await act(flushPromises);

    const myMustachio: Mustachio = {
      firstName: 'mustachio'
    };

    expect(mockedAxios.post).toHaveBeenCalledWith(
      "/api/mustachios",
      myMustachio,
    );

    expect(afterwards).toHaveBeenCalledWith('hello from the backend: mustachio');
  });
});
