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

    expect(getByText('cards!')).toBeInTheDocument();
  });

  describe('the form', () => {
    beforeEach(async () => {
      mockedAxios.get.mockResolvedValue({ data: [] });
    })

    it('displays a form', async () => {
      const { queryByTestId } = render(<App/>);
      await act(flushPromises);

      expect(queryByTestId('new-card-form')).toBeTruthy();
    });

    it('can displays the returned value from the form', async () => {
      mockedAxios.post.mockResolvedValue({ data: { firstName: "mustachio" } });

      const { getByTestId, getByText } = render(<App/>);
      await act(flushPromises);

      const inputEl = getByTestId('sticky-text-input') as HTMLInputElement;

      fireEvent.change(inputEl, { target: { value: 'mustachio' } });
      fireEvent.click(getByTestId('submit-button'));

      await act(flushPromises);

      expect(getByText("mustachio", { exact: false })).toBeInTheDocument();
    });
  });

  describe('the past mustachios', () => {
    it('displays a list of past mustachios', async () => {
      mockedAxios.get.mockResolvedValue({
        data: [
          { firstName: 'mando' },
          { firstName: 'baby yoda' },
        ]
      });

      const { queryByTestId } = render(<App/>);
      await act(flushPromises);

      const mustachioList = queryByTestId('mustachio-list') as HTMLElement;

      expect(mustachioList).toBeTruthy();
      expect(mustachioList).not.toBeEmpty();

      const listOfMustachios = within(mustachioList);

      expect(listOfMustachios.getByText("mando", { exact: false })).toBeInTheDocument();
      expect(listOfMustachios.getByText("baby yoda", { exact: false })).toBeInTheDocument();
    });

    describe('after making a new mustachio', () => {
      it('adds the mustachio to the list', async () => {
        mockedAxios.get.mockResolvedValue({
          data: [
            { firstName: 'mando' },
            { firstName: 'baby yoda' },
          ]
        });
        mockedAxios.post.mockResolvedValue({ data: { firstName: "ahsoka tano" } });

        const { queryByTestId, getByTestId } = render(<App/>);
        await act(flushPromises);

        const inputEl = getByTestId('sticky-text-input') as HTMLInputElement;

        fireEvent.change(inputEl, { target: { value: 'ahsoka tano' } });
        fireEvent.click(getByTestId('submit-button'));

        await act(flushPromises);

        const listOfMustachios = within(queryByTestId('mustachio-list') as HTMLElement);

        expect(listOfMustachios.getByText("mando", { exact: false })).toBeInTheDocument();
        expect(listOfMustachios.getByText("baby yoda", { exact: false })).toBeInTheDocument();
        expect(listOfMustachios.getByText("ahsoka tano", { exact: false })).toBeInTheDocument();
      });
    });
  });
});
