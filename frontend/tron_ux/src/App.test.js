import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders some Element wit text', () => {
  const { getByText } = render(<App />);
  const someElement = getByText(/learn frodo/i);
  expect(someElement).toBeInTheDocument();
});
