import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('Renders main title', () => {
  const { getByText } = render(<App />);
  const someElement = getByText(/Tron light cycle game/i);
  expect(someElement).toBeInTheDocument();
});
