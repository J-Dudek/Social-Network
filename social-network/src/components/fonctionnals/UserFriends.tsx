import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import Friend from "./friend";
import { IUser } from "../../types/IUser";
import { Segment } from "semantic-ui-react";
import Axios from "axios-observable";

const defaultFriends: IUser[] = [];

const UserFriends = ({ id }) => {
  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;
  const [friends, setFriends] = useState<IUser[]>(defaultFriends);

  useEffect(() => {
    async function getFriends() {
      const token = await getAccessTokenSilently();
      Axios.get(`${serverUrl}/friends/friendship/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }).subscribe((response) => setFriends(response.data));
    }
    getFriends();
  }, [getAccessTokenSilently, serverUrl]);

  return (
    <>
      {friends.map((friend) => (
        <Segment key={friend.idUser}>
          <Friend user={friend} />
        </Segment>
      ))}
    </>
  );
};

export default UserFriends;
