import React from 'react';
import axios  from 'axios';
import { useAuth0 } from "@auth0/auth0-react";
import CardProfil from '../fonctionnals/cardProfil'

import {Card,Input} from 'semantic-ui-react'

interface IUser{
  idUser: string;
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
       console.log(ex);
      setError("error");
      
    });
    
  }
  getAll();
  
}, [getAccessTokenSilently]);

  return (
    <div>
      <Input icon='users' iconPosition='left' placeholder='Search users...' />
  <div className="ui items">
    
    {users.map((user) =>(
      
      <CardProfil user={user} key={ user.idUser}/>
      ))}

    
    {error && <p className="error">{error}</p>}
      </div>
      </div>
);
};

export default User;