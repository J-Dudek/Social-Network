import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { IUser } from "../../types/IUser";
import axios from "axios";
import { Card, Image, Icon } from "semantic-ui-react";
import monkey from '../../images/monkeyInAsuit.jpg';
import ModalUpdateUser from '../friend/modalUpdateUser'

const defaultUser: IUser = {};


const Profile = () => {

  const [user, setUser] = useState<IUser>(defaultUser);
  const [count, setCount] = useState<number>(200);

  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;



  useEffect(() => {
    // Create an scoped async function in the hook

    async function getInfos2() {
      const token = await getAccessTokenSilently();
      axios
        .get(`${serverUrl}/friends/users/aboutUser`, {

          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          }
        })
        .then((response) => {

          console.log(response);
          console.log("data");
          console.log(response.data[0].t1)
          setUser(response.data[0].t1)
          setCount(response.data[0].t2)

        })
        .catch((ex) => {
          console.log(ex);

        });
    }
    getInfos2()

  }, [getAccessTokenSilently, serverUrl]);

  return (

    <div className="ui centered card">
      <Card>
        <Image src={monkey} wrapped ui={false} />
        <Card.Content>
          <Card.Header >{user.username}</Card.Header>
          <Card.Header><small>({user.lastName} {user.firstName})</small></Card.Header>
          <Card.Meta>
            <div><Icon name="mail" /> {user.email} </div>
            <div><Icon name="phone" /> {user.phoneNumber} </div>
          </Card.Meta>
          <Card.Description>
            <div>Hi, I'm {user.firstName}, I'm leave in {user.city}.</div>
            <div><Icon name="birthday cake" />I was born on {user.birthdate}.</div>
          </Card.Description>
        </Card.Content>
        <Card.Content extra>
          <div>
            <Icon name='user' />
            {count} amis
                </div>
        </Card.Content>
      </Card>
      <ModalUpdateUser auser={user} />
    </div>

  );
};

export default Profile;
