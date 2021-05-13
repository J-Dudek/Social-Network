import React from 'react'
import { Button, Card, Icon, Image, Popup } from 'semantic-ui-react';
import { IUser } from '../../types/IUser';
import monkey from '../../images/monkeyInAsuit.jpg';
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';
import { IInvit } from '../../types/IInvit';

const CardProfil = ({ user, friend = true }: { user: IUser, friend: boolean }) => {
  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;

  const removeFriend = (e) => {
    e.preventDefault();
    cancelInvit(e)
    async function cancelInvit(e) {
      const token = await getAccessTokenSilently();
      axios
        .delete(`${serverUrl}/friends/friendship/deleteFriend/${user.idUser}`, {

          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          timeout: 10000,
        })
        .catch((ex) => {
          console.log(ex);
        });
    }
  }

  const invitUser = (e) => {
    e.preventDefault();
    cancelInvit(e)
    async function cancelInvit(e) {
      const token = await getAccessTokenSilently();
      axios
        .post(`${serverUrl}/friends/friendship/sendInvit`, user.idUser, {

          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          timeout: 10000,
        })
        .catch((ex) => {
          console.log(ex);
        });
    }
  }


  return (
    <>
      <Card fluid >
        <Image src={monkey} size='small' centered />
        <Card.Content className="ui middle aligned divided list">
          <span className="right floated content">
            {friend ? (
              <Popup
                content='Friend removed'
                on='click'
                pinned
                trigger={<Button icon="remove user" onClick={removeFriend} />}
              />

            )
              : (
                <Popup
                  content='Invitation sent!'
                  on='click'
                  pinned
                  trigger={<Button icon="add user" onClick={invitUser} />}
                />
              )}
          </span>
          <Card.Header >{user.username}</Card.Header>

          <Card.Header><small>({user.lastName} {user.firstName})</small></Card.Header>
          {friend && (
            <Card.Meta>
              <div><Icon name="mail" /> {user.email} </div>
              <div><Icon name="phone" /> {user.phoneNumber} </div>
            </Card.Meta>)}
          <Card.Description>
            <div>Hi, I'm {user.firstName}{friend && (<>, I'm leave in {user.city}.</>)}</div>
            {friend && (<div><Icon name="birthday cake" />I was born on {user.birthdate}.</div>)}
          </Card.Description>
        </Card.Content>

      </Card>
    </>
  )
}
export default CardProfil
