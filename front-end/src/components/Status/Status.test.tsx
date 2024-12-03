import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import StatusComponent from './Status';
import { useStatusHook } from '../../hooks/useStatusHook';
import { useAuth } from '../../contexts/AuthContext';

// Mock `useStatusHook`
jest.mock('../../hooks/useStatusHook', () => ({
  useStatusHook: jest.fn(),
}));

// Mock `useAuth`
jest.mock('../../contexts/AuthContext', () => ({
  useAuth: jest.fn(),
}));

// Mock react-router-dom hooks
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: jest.fn(),
  useParams: jest.fn(() => ({
    host_form_id: '123',
    user_id: '456',
  })),
}));

describe('StatusComponent', () => {
  const mockNavigate = jest.fn();
  const mockUseStatusHook = jest.requireMock('../../hooks/useStatusHook').useStatusHook;
  const mockUseAuth = jest.requireMock('../../contexts/AuthContext').useAuth;

  beforeEach(() => {
    jest.clearAllMocks();

    // Mock `useNavigate`
    require('react-router-dom').useNavigate.mockReturnValue(mockNavigate);

    // Mock `useAuth`
    mockUseAuth.mockReturnValue({
      token: 'fake-token',
      username: 'Test User',
      userId: '456',
      isLoggedIn: true,
    });

    // Mock `useStatusHook`
    mockUseStatusHook.mockReturnValue({
      statusData: {
        teamBuyingName: 'Test Team Buying',
        hostcontact: 'http://example.com/contact',
        order: [
          { product: 'Product A', number: 2, price: 100 },
          { product: 'Product B', number: 1, price: 200 },
        ],
        transferInformation: 'Bank Transfer',
        paymentSatus: 0,
        teambuyingHostId: 789,
      },
      loading: false,
      error: null,
      total: 400,
      formattedDeadline: '2024-12-31 23:59',
    });
  });

  it('renders the component with required elements', () => {
    render(
      <MemoryRouter>
        <StatusComponent />
      </MemoryRouter>
    );

    // Check key elements
    expect(screen.getByText('Test Team Buying')).toBeInTheDocument();
    expect(screen.getByText('Bank Transfer')).toBeInTheDocument();
    expect(screen.getByText('Product A')).toBeInTheDocument();
    expect(screen.getByText('Product B')).toBeInTheDocument();
    expect(screen.getByText('$400')).toBeInTheDocument();
  });

  it('handles notify button click', async () => {
    render(
      <MemoryRouter>
        <StatusComponent />
      </MemoryRouter>
    );

    // Simulate Notify button click
    const notifyButton = screen.getByText('Notify');
    fireEvent.click(notifyButton);

    // Verify button state after click
    expect(screen.getByText('Notify')).toBeInTheDocument();
  });

  it('handles review button click and opens the review modal', () => {
    // Mock payment status as completed
    mockUseStatusHook.mockReturnValueOnce({
      statusData: {
        teamBuyingName: 'Test Team Buying',
        hostcontact: 'http://example.com/contact',
        order: [
          { product: 'Product A', number: 2, price: 100 },
          { product: 'Product B', number: 1, price: 200 },
        ],
        transferInformation: 'Bank Transfer',
        paymentSatus: 1, // Payment completed
        teambuyingHostId: 789,
      },
      loading: false,
      error: null,
      total: 400,
      formattedDeadline: '2024-12-31 23:59',
    });

    render(
      <MemoryRouter>
        <StatusComponent />
      </MemoryRouter>
    );

    // Simulate Review button click
    const reviewButton = screen.getByText('Review');
    fireEvent.click(reviewButton);

    // Verify the review modal is opened
    expect(screen.getByText("Test User's Review")).toBeInTheDocument();
  });

  it('renders loading state when loading is true', () => {
    mockUseStatusHook.mockReturnValueOnce({
      statusData: null,
      loading: true,
      error: null,
      total: 0,
      formattedDeadline: '',
    });

    render(
      <MemoryRouter>
        <StatusComponent />
      </MemoryRouter>
    );

    // Check loading state
    expect(screen.getByText('Loading...')).toBeInTheDocument();
  });

  it('renders error state when an error occurs', () => {
    mockUseStatusHook.mockReturnValueOnce({
      statusData: null,
      loading: false,
      error: 'Failed to fetch data',
      total: 0,
      formattedDeadline: '',
    });

    render(
      <MemoryRouter>
        <StatusComponent />
      </MemoryRouter>
    );

    // Check error message
    expect(screen.getByText('Error: Failed to fetch data')).toBeInTheDocument();
  });
});
