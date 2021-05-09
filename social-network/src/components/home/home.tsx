import React, { useEffect } from 'react';
import axios from 'axios';
import { useAuth0 } from "@auth0/auth0-react";
import { IUser } from '../../types/IUser';
import UpdateUser from '../../services/updateUser';
import HomePage from './homePage';

import Users from './../friend/users';

const defaultUser: IUser = {};


const Home = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { isAuthenticated } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;
  const [users, setUsers]: [IUser, (users: IUser) => void] = React.useState(
    defaultUser
  );


  const [error, setError]: [string, (error: string) => void] = React.useState(
    ''
  );




  useEffect(() => {
    // Create an scoped async function in the hook
    async function getInfos() {
      const token = await getAccessTokenSilently();
      axios
        .get<IUser>(`${serverUrl}/friends/users/logOrsign`, {

          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          timeout: 10000,
        })
        .then((response) => {
          console.log(response);
          console.log("data");
          console.log(response.data);
          setUsers(response.data);

        })
        .catch((ex) => {
          console.log(ex);
          setError("error");

        });

    }
    if (isAuthenticated) {
      getInfos()
    }
  }, [getAccessTokenSilently, isAuthenticated, serverUrl]);

  return (
    <>
      {/* Ici j'ai mis Users mais en soit ça serait mieux la liste des posts des amis*/}
      {(isAuthenticated) ?
        ((users.email) ? (<div><HomePage /></div>) : (<div><UpdateUser /></div>))
        :
        (<div>WIP : Soon there will be a homepage for non logged in users </div>)}
      {error && <p className="error">{error}</p>}

    </>)

};


export default Home;

