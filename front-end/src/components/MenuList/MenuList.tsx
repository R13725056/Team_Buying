import { memo, useState } from 'react';
import type { FC } from 'react';
import * as ReactDOM from 'react-dom/client';  // Add this import
import { useNavigate } from 'react-router-dom';  // Add this
import { Menu1 } from '../Menu/Menu';
import { ParticipantForm } from '../ParticipantForm/ParticipantForm';  // Update this line
import { StatusComponent as Status } from '../Status/Status';  // Add this import
import HostForm from '../HostForm_new/HostForm';  // Add this import

import resets from '../_resets.module.css';
import { Component1_Property1Account } from './MenuList_Property1Account/MenuList_Property1Account';
import { Component1_Property1Menu } from './MenuList_Property1Menu/MenuList_Property1Menu';
import { Component5_Property1Create } from './MenuList_Property1Create/MenuList_Property1Create';
import classes from './MenuList.module.css';
import { VectorIcon2 } from './VectorIcon2';
import { VectorIcon } from './VectorIcon';
import { useAuth } from '../../contexts/AuthContext';
import { useParams } from 'react-router-dom';
import QuickLogin from '../QuickLogin/QuickLogin';
import UserBotton from '../UserBotton/UserBotton';
interface Props {
  className?: string;
}
/* @figmaId 29:516 */
export const MenuList: FC<Props> = memo(function MenuList(props = {}) {
  const navigate = useNavigate();  // Add this
  const { host_id, host_form_id } = useParams<{ host_id: string; host_form_id: string }>();
  const { token, username, userId, isLoggedIn } = useAuth();
  const [showComponents, setShowComponents] = useState(false);
  const [showMenu1Modal, setShowMenu1Modal] = useState(false);
  const [showOrderModal, setShowOrderModal] = useState(false);
  const [showStatusModal, setShowStatusModal] = useState(false);
  const [showHostFormModal, setShowHostFormModal] = useState(false);  // Add this

  const userID = localStorage.getItem('userID');
  const showHostBackend = host_id === userId;

  const [isUserOpen, setIsUserOpen] = useState(false);
  const handleUserClick = () => {
    setIsUserOpen(!isUserOpen);
  };
  const handleMenuClick = () => {
    setShowComponents(!showComponents);
  };

  const handleMenu1Click = () => {
    setShowMenu1Modal(true);
  };

  const handleOrderClick = () => {
    if (!isLoggedIn) {
      alert('請先點右下角頭像進行快速登入');
      return; // 阻止後續行動
    }
    setShowOrderModal(true);
  };

  const handleOrderConfirm = () => {
    setShowOrderModal(false);
    if (host_form_id && userId) {
      navigate(`/order-item/status/${host_form_id}/${userId}`);
    }
  };

  return (
    <>
      <div className={`${resets.clapyResets} ${classes.root}`}>
        <div className={classes.frame34}>
          <button className={classes.unnamed} onClick={handleUserClick}>
          <Component1_Property1Account
              className={classes.component2}
              swap={{
                vector: <VectorIcon className={classes.icon} />,
              }}
            />
          </button>
          {isUserOpen &&
                  (isLoggedIn ? (
                    <UserBotton isOpen={isUserOpen} onClose={handleUserClick}></UserBotton>
                  ) : (
                    <QuickLogin isOpen={isUserOpen} onClose={handleUserClick}></QuickLogin>
                  ))}
        </div>
        {showComponents && (
          <>
            <Component5_Property1Create
              className={classes.component6}
              text={{
                create: <div className={classes.create}>Menu</div>,
              }}
              onClick={handleMenu1Click}
            />
            <Component5_Property1Create
              className={classes.component5}
              text={{
                create: <div className={classes.create2}>Order</div>,
              }}
              onClick={handleOrderClick}  // Add this
            />
            {showHostBackend && (
              <Component5_Property1Create
                className={classes.component7}
                text={{
                  create: <div className={classes.create3}>Host backend system</div>,
                }}
                onClick={() => setShowHostFormModal(true)}  // Add this
              />
            )}
          </>
        )}
        <div className={classes.unnamed2} onClick={handleMenuClick}>
          <Component1_Property1Menu
            className={classes.component8}
            swap={{
              vector: <VectorIcon2 className={classes.icon2} />,
            }}
          />
        </div>
      </div>
      {showMenu1Modal && (
        <div className={classes.modalOverlay} onClick={() => setShowMenu1Modal(false)}>
          <div className={classes.modalContent} onClick={e => e.stopPropagation()}>
            <Menu1 />
          </div>
        </div>
      )}
      {showOrderModal && (
        <div className={classes.modalOverlay} onClick={() => setShowOrderModal(false)}>
          <div className={classes.modalContent} onClick={e => e.stopPropagation()}>
            <ParticipantForm
              host_id={host_id}
              host_form_id={host_form_id}
              onConfirm={handleOrderConfirm}
            />
          </div>
        </div>
      )}
      {showHostFormModal && (
        <HostForm
          isOpen={showHostFormModal}
          onClose={() => setShowHostFormModal(false)}
        />
      )}
    </>
  );
});
