import React, { useEffect, useState } from 'react'
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';
import Friend from './friend';
import { IUser } from '../../types/IUser';
import { Segment } from 'semantic-ui-react';

const defaultFriends: IUser[] = [];

const MyFriends = () => {
    const { getAccessTokenSilently } = useAuth0();
    const serverUrl = process.env.REACT_APP_SERVER_URL;
    const [friends, setFriends] = useState<IUser[]>(defaultFriends);

    useEffect(() => {
        async function getFriends() {
            const token = await getAccessTokenSilently();
            axios
                .get(`${serverUrl}/friends/friendship/friends`, {

                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    }
                })
                .then((response) => {
                    setFriends(response.data)

                })
                .catch((ex) => {
                    console.log(ex);
                });
        }
        getFriends()

    }, [getAccessTokenSilently, serverUrl]);



    return (
        <>
            {friends.map(friend => <Segment key={friend.idUser}><Friend user={friend} /></Segment>)}
        </>

    )
}


export default MyFriends