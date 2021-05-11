import React, { useState } from 'react'
import { useAuth0 } from "@auth0/auth0-react";
import Friend from './friend';
import { IUser } from '../../types/IUser';
import { Input, Segment } from 'semantic-ui-react';
import Axios from 'axios-observable';

const defaultFriends: IUser[] = [];
const serverUrl = process.env.REACT_APP_SERVER_URL;


const UseFetchUsers = (query: any) => {
    const [users, setUsers] = React.useState(defaultFriends)
    const { getAccessTokenSilently } = useAuth0();
    React.useEffect(() => {

        async function getFriends(input) {

            const token = await getAccessTokenSilently();
            Axios
                .post(`${serverUrl}/friends/users/search`, input, {

                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                })
                .subscribe(
                    response => setUsers(response.data)
                );
        }
        if (query && query.target.value.length > 3) {
            const input = query.target.value;
            getFriends(input)
        } else {
            setUsers(defaultFriends);
        }



    }, [getAccessTokenSilently, query])

    return { users }
}
const Users = ({ friends }) => {
    return (
        <>
            {friends.map(friend => <Segment key={friend.idUser}><Friend user={friend} /></Segment>)}
        </>

    )
}

const SearchUsers = ({ query, setQuery }) => (
    <>
        <Input placeholder='Search...' onChange={setQuery} data={query} icon="users" iconPosition='left' />
    </>


)

const SearchFriends = () => {
    const [query, setQuery] = useState("")
    const { users } = UseFetchUsers(query)

    return (
        <>
            <SearchUsers query={query} setQuery={setQuery} />
            <Users friends={users} />
        </>
    )
}

export default SearchFriends






/**
 * {friends.map(friend => <Segment key={friend.idUser}><Friend user={friend} /></Segment>)}
 */


