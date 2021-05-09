import React, { useEffect, useState } from "react";
import axios from 'axios';
import { useAuth0 } from "@auth0/auth0-react";
import CardPost from '../fonctionnals/CardPost';
import { Grid, Segment } from "semantic-ui-react";

interface IPost {
  idPost?: number;
  message?: string;
  publicationDate?: string;
  userId?: string;
  public?: boolean;
}

export interface IUser {
  idUser?: string;
  firstName?: string;
  lastName?: string;
  birthdate?: string;
  email?: string;
  phoneNumber?: string;
  city?: string;
  signInDate?: string;
  username?: string;
  new?: boolean;
}

const defaultPost: IPost[] = [];
const defaultFriends: IUser[] = [];

const MyFriendPost = () => {
  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;
  const [friends, setFriends] = useState<IUser[]>(defaultFriends);
  const [posts, setPosts] = useState<IPost[]>(defaultPost);

  useEffect(() => {
    // Create an scoped async function in the hook

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

  useEffect(() => {
    async function getfriendsPosts(friends) {
      const token = await getAccessTokenSilently();
      axios
        .post(`${serverUrl}/posts/all/friends`, friends, {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        })
        .then((response) => {
          setPosts(response.data)
        })
        .catch((ex) => {
          console.log(ex);
        });
    }
    getfriendsPosts(friends)
  }, [friends, getAccessTokenSilently, serverUrl]);


  return (
    <Segment style={{ padding: '4%' }} className="ui middle">
      <Grid>
        <Grid.Row >
          <Grid.Column width={14}>

            {posts.map((post) => (
              <div>
                <CardPost post={post} />
              </div>

            ))}
          </Grid.Column>
        </Grid.Row>
      </Grid>
    </Segment>
  );
};

export default MyFriendPost;

