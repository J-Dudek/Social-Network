import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useAuth0 } from "@auth0/auth0-react";
import { IUser } from '../../types/IUser';
import UpdateUser from '../../services/updateUser';
import HomePage from './homePage';
import HomePageLogOut from './homePageLogOut';


const defaultUser: IUser = {};


const Home = () => {
  const { getAccessTokenSilently } = useAuth0();
  const { isAuthenticated } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;
  const [users, setUsers]: [IUser, (users: IUser) => void] = React.useState(
    defaultUser
  );
  const [register, setRegister] = useState<boolean>(false);
  const [logged, setLogged] = useState<boolean>(false);

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
          setUsers(response.data);
          setLogged(true)
          if (response.data.email) {
            setRegister(true)
          } else {
            setRegister(false)
          }
        })
        .catch((ex) => {
          console.log(ex);

        });

    }
    if (isAuthenticated) {
      getInfos()
    }
  }, [getAccessTokenSilently, isAuthenticated, serverUrl]);


  return (
    <>

      {(isAuthenticated) ?
        (logged && <div><UpdateUser register={register} /> <HomePage register={register} /></div>)
        :
        (<div><HomePageLogOut /></div>)}



    </>)

};


export default Home;

