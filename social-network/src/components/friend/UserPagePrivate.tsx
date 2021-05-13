import React, { useState, useEffect } from "react";
import { IUser } from "../../types/IUser";
import UserFriends from "../fonctionnals/UserFriends";
import UserPost from "../fonctionnals/UserPosts";
import { useAuth0 } from "@auth0/auth0-react";
import {
  Grid,
  Segment,
  Header,
} from "semantic-ui-react";
import axios from "axios";
import CardProfil from "../fonctionnals/cardProfil";

const defaultUser: IUser = {};

const UserPagePrivate = ({ userId }) => {
  const [user, setUser] = useState<IUser>(defaultUser);
  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;

  useEffect(() => {
    const fetchUser = async () => {
      const token = await getAccessTokenSilently();
      axios
        .get(`${serverUrl}/friends/users/friend/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        })
        .then((response) => {

          setUser(response.data);
        })
        .catch((ex) => {
          console.log(ex);
        });

    };

    fetchUser();
  }, [getAccessTokenSilently, userId, serverUrl]);


  return (
    <Grid container columns="equal">
      <Grid.Row>
        <Grid.Column width={4}>
          <Segment style={{ overflow: "auto", maxHeight: "50vh" }}>
            <div>
              <Segment inverted textAlign="center" color="blue">
                Friends
                </Segment>
            </div>
            <Segment.Group >
              <UserFriends id={userId} />
            </Segment.Group>
          </Segment>
        </Grid.Column>
        <Grid.Column width={8}>
          <Segment>
            <CardProfil user={user} friend={false} />
          </Segment>
          <Segment
            style={{ overflow: "auto", maxHeight: "50vh" }}
            className="ui middle"
          >
            <Header
              color="blue"
              attached="top"
              block={true}
              dividing={true}
              textAlign="center"
            >
              Les posts de l'users
              </Header>
            <UserPost id={userId} isPublic={false} />
          </Segment>
        </Grid.Column>
      </Grid.Row>
    </Grid>
  );
};

export default UserPagePrivate;
