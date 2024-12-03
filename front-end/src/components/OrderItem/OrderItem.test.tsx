import { render, screen } from '@testing-library/react';
import { OrderItem } from './OrderItem';
import { BrowserRouter } from 'react-router-dom';
import { AuthContext } from '../../contexts/AuthContext';

jest.mock('../../hooks/useOrderForms', () => ({
  __esModule: true,
  default: jest.fn(() => ({
    orderDetails: [
      {
        name: 'Test Order',
        items: [
          { itemName: 'Item 1', number: 2, price: 10 },
          { itemName: 'Item 2', number: 1, price: 5 },
        ],
        total: 25,
        status: 0,
      },
    ],
    loading: false,
    error: null,
    handleTransfer: jest.fn(),
  })),
}));

jest.mock('../../hooks/useReadMenu', () => ({
  __esModule: true,
  default: jest.fn(() => ({
    menuData: [],
    loading: false,
    error: null,
  })),
}));

describe('OrderItem Component', () => {
  it('should render order details correctly when logged in', () => {
    render(
      <AuthContext.Provider
        value={{
          token: 'mockToken',
          username: 'mockUsername',
          userId: 'mockUserId',
          isLoggedIn: true,
          login: jest.fn(),
          logout: jest.fn(),
        }}
      >
        <BrowserRouter>
          <OrderItem />
        </BrowserRouter>
      </AuthContext.Provider>
    );

    // 檢查是否渲染了訂單名稱
    expect(screen.getByText('Test Order')).toBeInTheDocument();

    // 檢查是否渲染了第一項物品
    expect(screen.getByText('Item 1')).toBeInTheDocument();

    // 檢查是否渲染了總金額
    expect(screen.getByText('25')).toBeInTheDocument();
  });
});
