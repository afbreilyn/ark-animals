import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('<App />', () => {
  it('displays whatever I goddamn well tell it to', () => {
    const { getByText } = render(
      <App>
        <p>Hello</p>
      </App>,
    );

    getByText('Hello');
  });
});
