import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { Menu1 } from './Menu';
import useReadMenu from '../../hooks/useReadMenu';

// Mock `useReadMenu`
jest.mock('../../hooks/useReadMenu', () => ({
  __esModule: true,
  default: jest.fn(),
}));

// Mock `useParams`
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useParams: jest.fn(),
}));

describe('Menu1 Component', () => {
  const mockUseReadMenu = jest.requireMock('../../hooks/useReadMenu').default;
  const mockUseParams = jest.requireMock('react-router-dom').useParams;

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders loading state', () => {
    // Mock `useParams` and `useReadMenu`
    mockUseParams.mockReturnValue({ host_form_id: '123' });
    mockUseReadMenu.mockReturnValue({
      menuData: null,
      loading: true,
      error: null,
    });

    render(
      <MemoryRouter>
        <Menu1 />
      </MemoryRouter>
    );

    expect(screen.getByText('Loading...')).toBeInTheDocument();
  });

  it('renders error state', () => {
    // Mock `useParams` and `useReadMenu`
    mockUseParams.mockReturnValue({ host_form_id: '123' });
    mockUseReadMenu.mockReturnValue({
      menuData: null,
      loading: false,
      error: 'Failed to fetch menu',
    });

    render(
      <MemoryRouter>
        <Menu1 />
      </MemoryRouter>
    );

    expect(screen.getByText('Error: Failed to fetch menu')).toBeInTheDocument();
  });


  it('handles menu data gracefully', () => {
    // Mock `useParams` and `useReadMenu`
    mockUseParams.mockReturnValue({ host_form_id: '123' });
    mockUseReadMenu.mockReturnValue({
      menuData: null,
      loading: false,
      error: null,
    });

    render(
      <MemoryRouter>
        <Menu1 />
      </MemoryRouter>
    );

    // Check if no image is rendered
    expect(screen.queryByTestId('menuImage')).not.toBeInTheDocument();
  });
});
