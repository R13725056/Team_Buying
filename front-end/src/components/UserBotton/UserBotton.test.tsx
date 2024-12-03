
import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import UserBotton from './UserBotton';
import UserInformation from './UserInformation/UserInformation';
import HistoryList from './HistoryList/HistoryList';
import NowHosting from './NowHosting/NowHosting';
import NowBuying from './NowBuying/NowBuying';
import ReviewList from './ReviewList/ReviewList';

jest.mock('./UserInformation/UserInformation', () => () => <div>User Information Component</div>);
jest.mock('./HistoryList/HistoryList', () => () => <div>History List Component</div>);
jest.mock('./NowHosting/NowHosting', () => () => <div>Now Hosting Component</div>);
jest.mock('./NowBuying/NowBuying', () => () => <div>Now Buying Component</div>);
jest.mock('./ReviewList/ReviewList', () => () => <div>Review List Component</div>);

describe('UserBotton Component', () => {
  const defaultProps = {
    isOpen: true,
    onClose: jest.fn(),
  };

  it('should not render when isOpen is false', () => {
    render(<UserBotton {...defaultProps} isOpen={false} />);
    expect(screen.queryByRole('button')).not.toBeInTheDocument();
  });

});
