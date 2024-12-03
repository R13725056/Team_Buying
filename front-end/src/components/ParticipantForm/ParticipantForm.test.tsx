import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import { useNavigate } from 'react-router-dom';
import { ParticipantForm } from './ParticipantForm';
import { useCreateParticipantForm } from '../../hooks/UseCreateParticipantFormReturn';
import { useUrlParams } from '../../hooks/useUrlParams';

// Mocks
jest.mock('react-router-dom', () => ({
  useNavigate: jest.fn(),
}));

jest.mock('../../hooks/UseCreateParticipantFormReturn', () => ({
  useCreateParticipantForm: jest.fn(),
}));

jest.mock('../../hooks/useUrlParams', () => ({
  useUrlParams: jest.fn(),
}));

describe('ParticipantForm Component', () => {
  const mockNavigate = jest.fn();
  const mockHandleSubmit = jest.fn();
  const mockAddInputGroup = jest.fn();
  const mockDeleteInputGroup = jest.fn();
  const mockUpdateInputGroup = jest.fn();
  const mockSetIsAnonymous = jest.fn();
  const mockSetUserName = jest.fn();

  beforeEach(() => {
    (useNavigate as jest.Mock).mockReturnValue(mockNavigate);

    (useCreateParticipantForm as jest.Mock).mockReturnValue({
      inputGroups: [{ order: '', quantity: '', price: '', description: '' }],
      isAnonymous: false,
      userName: 'John Doe',
      loading: false,
      error: '',
      menus: [{ products: [{ product: 'Product 1', price: 10 }] }],
      menuLoading: false,
      menuError: '',
      addInputGroup: mockAddInputGroup,
      deleteInputGroup: mockDeleteInputGroup,
      updateInputGroup: mockUpdateInputGroup,
      setIsAnonymous: mockSetIsAnonymous,
      setUserName: mockSetUserName,
      handleSubmit: mockHandleSubmit,
    });

    (useUrlParams as jest.Mock).mockReturnValue({
      hostformId: '123',
      userId: '456',
    });
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should render the participant form', () => {
    render(<ParticipantForm />);
    expect(screen.getByText(/Your Name/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/Enter your name/i)).toHaveValue('John Doe');
  });

  it('should call setUserName on name input change', () => {
    render(<ParticipantForm />);
    const nameInput = screen.getByPlaceholderText(/Enter your name/i);
    fireEvent.change(nameInput, { target: { value: 'Jane Doe' } });
    expect(mockSetUserName).toHaveBeenCalledWith('Jane Doe');
  });

  it('should toggle anonymous state when checkbox is clicked', () => {
    render(<ParticipantForm />);
    const anonymousCheckbox = screen.getByRole('checkbox');
    fireEvent.click(anonymousCheckbox);
    expect(mockSetIsAnonymous).toHaveBeenCalledWith(true);
  });



});
