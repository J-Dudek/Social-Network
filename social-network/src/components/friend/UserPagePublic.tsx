import React, { useState, useEffect } from "react";
import { IUser } from "../../types/IUser";
import UserFriends from "../fonctionnals/UserFriends";
import UserPosts from "../fonctionnals/UserPosts";
import { useAuth0 } from "@auth0/auth0-react";
import {
  Card,
  Image,
  Icon,
  Grid,
  Segment,
  Header,
  Loader,
} from "semantic-ui-react";
import monkey from "../../images/monkeyInAsuit.jpg";
import axios from "axios";

const defaultUser: IUser = {};

const UserPagePublic = ({ userId }) => {
  const [user, setUser] = useState<IUser>(defaultUser);
  const [count, setCount] = useState<number>(0);
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
          console.log(response.data);
          setUser(response.data);
        })
        .catch((ex) => {
          console.log(ex);
        });
      console.log(userId);
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
              <Segment.Group vertical>
                <UserFriends id={userId} />
              </Segment.Group>
            </Segment>
          </Grid.Column>
          <Grid.Column width={8}>
            <Segment>
              <Card fluid>
                <Image src={monkey} size="small" centered />
                <Card.Content className="ui middle aligned divided list">
                  <Card.Header>{user.username}</Card.Header>

                  <Card.Header>
                    <small>
                      ({user.lastName} {user.firstName})
                    </small>
                  </Card.Header>
                  <Card.Meta>
                    <div>
                      <Icon name="mail" /> {user.email}
                    </div>
                    <div>
                      <Icon name="phone" /> {user.phoneNumber}
                    </div>
                  </Card.Meta>
                  <Card.Description>
                    <div>
                      Hi, I'm {user.firstName}, I'm leave in {user.city}.
                    </div>
                    <div>
                      <Icon name="birthday cake" />I was born on {user.birthdate}.
                    </div>
                  </Card.Description>
                </Card.Content>
                <Card.Content extra>
                  <div>
                    <Icon name="user" />
                    {count} amis
                  </div>
                </Card.Content>
              </Card>
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
              <UserPosts id={userId} isPublic={true} />
            </Segment>
          </Grid.Column>
        </Grid.Row>
      </Grid>
    );
  };


export default UserPagePublic;
