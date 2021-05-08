import React, { useState, useEffect } from "react";
import { IUser } from "../../types/IUser";
import { IPost } from "../../types/IPost";
import Friend from "../fonctionnals/friend";
import PersonnalPost from "../fonctionnals/personnalPost";
import { useAuth0 } from "@auth0/auth0-react";
import { Card, Image, Icon, Grid, Segment, Header } from "semantic-ui-react";
import monkey from "../../images/monkeyInAsuit.jpg";
import axios from "axios";

const defaultUser: IUser = {};
const defaultFriends: IUser[] = [];
const defaultPersonnalPost: IPost[] = [];
const defaultIsFriend: boolean = false;

const UserPage = ({ match }) => {
  const [user, setUser] = useState<IUser>(defaultUser);
  const [friends, setFriends] = useState<IUser[]>(defaultFriends);
  const [postsPerso, setPosts] = useState<IPost[]>(defaultPersonnalPost);
  const [isFriend, setIsFriend] = useState<boolean>(defaultIsFriend);
  const [count, setCount] = useState<number>(0);
  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;

  useEffect(() => {
    fetchIsFriend();
  }, []);

  const fetchIsFriend = async () => {
    const token = await getAccessTokenSilently();

    axios
      .get(`${serverUrl}/friends/friendship/verify/${match.params.id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        setIsFriend(response.data);
        fetchUser(response.data);
        fetchUserFriends(response.data);
        fetchUserPosts(response.data);
        console.log(response.data);
      })
      .catch((ex) => {
        console.log(ex);
      });
  };

  const fetchUser = async (isYourFriend: boolean) => {
    const token = await getAccessTokenSilently();

    console.log("IS FRIEND ? " + isYourFriend);

    axios
      .get(
        `${serverUrl}/friends/users${isYourFriend ? "/friend" : ""}/${
          match.params.id
        }`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      )
      .then((response) => {
        console.log(response.data);
        setUser(response.data.t1);
        setCount(response.data.t2);
      })
      .catch((ex) => {
        console.log(ex);
      });
    console.log(match);
  };

  const fetchUserFriends = async (isYourFriend: boolean) => {
    const token = await getAccessTokenSilently();
    console.log("IS FRIEND ? " + isYourFriend);
    axios
      .get(`${serverUrl}/friends/friendship/${match.params.id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        console.log(response.data);
        setFriends(response.data);
      })
      .catch((ex) => {
        console.log(ex);
      });
  };

  const fetchUserPosts = async (isYourFriend: boolean) => {
    const token = await getAccessTokenSilently();
    console.log("IS FRIEND ? " + isYourFriend);
    axios
      .get(
        `${serverUrl}/posts/all${isYourFriend ? "" : "/public"}/${
          match.params.id
        }`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      )
      .then((response) => {
        console.log(response.data);
        setPosts(response.data);
      })
      .catch((ex) => {
        console.log(ex);
      });
  };

  return <h1>Friend</h1>;

  /*
  return (
    <Grid container columns="equal">
      <Grid.Row>
        <Grid.Column width={4}>
          <Segment style={{ overflow: "auto", maxHeight: "50vh" }}>
            <div>
              <Segment inverted color="blue">
                Friends
              </Segment>
            </div>
            <Segment.Group vertical>
              {friends.map((friend) => (
                <Segment key={friend.idUser}>
                  <Friend user={friend} />
                </Segment>
              ))}
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
                    <Icon name="mail" /> {user.email}{" "}
                  </div>
                  <div>
                    <Icon name="phone" /> {user.phoneNumber}{" "}
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
            {postsPerso.map((p) => (
              <PersonnalPost post={p} key={p.idPost} />
            ))}
          </Segment>
        </Grid.Column>
      </Grid.Row>
    </Grid>
  );
  */
};

export default UserPage;
