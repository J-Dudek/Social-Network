import React from 'react';
import axios  from 'axios';
import { useAuth0 } from "@auth0/auth0-react";
import monkey from '../../images/monkeyInAsuit.jpg';

import {Card, Image} from 'semantic-ui-react'

interface IUser{
  idUser: number;
  firstName: string;
  lastName: string;
  birthdate?: string;
  email: string;
  phoneNumber: string;
  city?: string;
  signInDate?: string;
  username?: string;
}

const defaultUser: IUser[] = [];

const User = () => {
  const { getAccessTokenSilently } = useAuth0();
  const [users,setUsers]:[IUser[],(users: IUser[])=>void] = React.useState(
    defaultUser
  );
 

  const [error, setError]: [string, (error: string) => void] = React.useState(
    ''
  );

React.useEffect(() => {
  const serverUrl = process.env.REACT_APP_SERVER_URL;
  async function getAll() {
    const token = await getAccessTokenSilently();
    axios
    .get<IUser[]>(`${serverUrl}/friends/users/all`, {
      
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      timeout: 10000,
    })
    .then((response) => {
      setUsers(response.data);
      
    })
    .catch((ex) => {
      let error = axios.isCancel(ex)
        ? 'Request Cancelled'
        : ex.code === 'ECONNABORTED'
        ? 'A timeout has occurred'
        : ex.response.status === 404
        ? 'Resource Not Found'
        : 'An unexpected error has occurred';

      setError(error);
      
    });
    
  }
  getAll();
  
}, [getAccessTokenSilently]);

return (
  <div className="UserCards">
    
    <Card.Group>
    {users.map((user) =>(
        <Card key={user.idUser}>
            <Image
              floated='right'
              size='mini'
              src={monkey}
            />
            <Card.Content>
              <Card.Header>{user.firstName}  {user.lastName}</Card.Header>
            </Card.Content>
            <Card.Meta>Lives in : {user.city} Join : {user.signInDate}</Card.Meta>
            <Card.Description>
              <div>
                Email : {user.email}
              </div>
              <div>
                Phone : {user.phoneNumber}
              </div>
        </Card.Description>
          </Card>
      ))}

    </Card.Group>
    
    {error && <p className="error">{error}</p>}
  </div>
);
};

export default User;