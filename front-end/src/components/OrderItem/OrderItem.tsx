import { memo, useState } from 'react';
import type { FC } from 'react';

import resets from '../_resets.module.css';
import { Component1_Property1Account } from './Component1_Property1Account/Component1_Property1Account';
import classes from './OrderItem.module.css';
import { VectorIcon } from './VectorIcon';
import { useNavigate } from 'react-router-dom';
interface Props {
  className?: string;
}

interface OrderDetail {
  name: string;
  items: { itemName: string; number: string; price: number }[];
  total: number;
}

const orderDetails: OrderDetail[] = [
  {
    name: 'Tom',
    items: [
      { itemName: 'Chocolate Cake', number: 'x 1', price: 160 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
    ],
    total: 190,
  },
  {
    name: 'Tom',
    items: [
      { itemName: 'Chocolate Cake', number: 'x 1', price: 160 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
    ],
    total: 190,
  },
  {
    name: 'Tom',
    items: [
      { itemName: 'Chocolate Cake', number: 'x 1', price: 160 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
    ],
    total: 190,
  },
  {
    name: 'Tom',
    items: [
      { itemName: 'Chocolate Cake', number: 'x 1', price: 160 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
      { itemName: 'Black Tea', number: 'x 1', price: 30 },
    ],
    total: 190,
  },

];

export const OrderItem: FC<Props> = memo(function OrderItem(props = {}) {
  const [clickedStates, setClickedStates] = useState<{ [key: number]: boolean }>({});

  const handleTransferClick = (index: number) => {
    setClickedStates(prev => ({
      ...prev,
      [index]: !prev[index]
    }));
  };
  const navigate = useNavigate();
  const handleLogoClick = () => {
    navigate('/');
  };
  return (
    <div className={`${resets.clapyResets} ${classes.container}`}>
      <div className={classes.unsplashQJ0zGkrE1Zg}></div>
      <div className={classes.frame29}></div>
      <div className={classes.logo}>
        <div onClick={handleLogoClick} className={classes.logo21}></div>
        <div className={classes.logo31}></div>
      </div>
      {orderDetails.map((order, index) => (
        <div key={index} className={classes.orderDetails}>
          <div className={classes.header}>
            <Component1_Property1Account
              className={classes.component6}
              swap={{
                vector: <VectorIcon className={classes.icon} />,
              }}
            />
            <div className={classes.name}>{order.name}</div>
          </div>
          <div className={classes.itemList}>
            {order.items.map((item, idx) => (
              <div key={idx} className={classes.item}>
                <div className={classes.itemName}>{item.itemName}</div>
                <div className={classes.number}>{item.number}</div>
                <div className={classes.price}>{item.price}</div>
              </div>
            ))}
            <div className={classes.line3}></div>
            <div className={classes.item}>
              <div className={classes.itemName}>Total</div>
              <div className={classes.number}></div>
              <div className={classes.price}>{order.total}</div>
            </div>
            <button
              className={classes.transferButton}
              onClick={() => handleTransferClick(index)}
              style={{
                backgroundImage: `url('/assets/transfer_${clickedStates[index] ? 'white' : 'green'}.png')`
              }}
            />
          </div>
        </div>
      ))}
    </div>
  );
});
