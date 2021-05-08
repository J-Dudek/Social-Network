import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { IUser } from "../../types/IUser";
import axios from "axios";
import { Card, Image, Icon, Grid, Segment, Header } from "semantic-ui-react";
import monkey from '../../images/monkeyInAsuit.jpg';
import ModalUpdateUser from '../friend/modalUpdateUser'
import InvitationR from '../fonctionnals/invitationR'
import InvitationS from '../fonctionnals/invitationS'
import PersonnalPost from '../fonctionnals/personnalPost'
import Friend from '../fonctionnals/friend'
import { IInvit } from "../../types/IInvit";
import { IPost } from "../../types/IPost";

const defaultUser: IUser = {};
const defaultFriends: IUser[] = [];
const defaultInvitsReceived: IInvit[] = [];
const defaultPersonnalPost: IPost[] = [];

const Profile = () => {

  const [user, setUser] = useState<IUser>(defaultUser);
  const [count, setCount] = useState<number>(0);
  const [friends, setFriends] = useState<IUser[]>(defaultFriends);
  const [invitsR, setInvitsR] = useState<IInvit[]>(defaultInvitsReceived);
  const [postsPerso, setPosts] = useState<IPost[]>(defaultPersonnalPost);
  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;

  useEffect(() => {
    // Create an scoped async function in the hook

    async function getMyPosts() {
      const token = await getAccessTokenSilently();
      axios
        .get(`${serverUrl}/posts/my`, {

          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          }
        })
        .then((response) => {
          setPosts(response.data)
        })
        .catch((ex) => {
          console.log(ex);
        });
    }
    getMyPosts()

  }, [getAccessTokenSilently, serverUrl]);

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
    // Create an scoped async function in the hook

    async function getInvitsR() {
      const token = await getAccessTokenSilently();
      axios
        .get<IInvit[]>(`${serverUrl}/friends/friendship/myreceived`, {

          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          }
        })
        .then((response) => {
          console.log(response.data);
          //setInvitsR(response.data)
        })
        .catch((ex) => {
          console.log(ex);
        });
    }
    getInvitsR()

  }, [getAccessTokenSilently, serverUrl]);

  useEffect(() => {
    // Create an scoped async function in the hook

    async function getInfos() {
      const token = await getAccessTokenSilently();
      axios
        .get(`${serverUrl}/friends/users/aboutUser`, {

          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          }
        })
        .then((response) => {
          setUser(response.data.t1)
          setCount(response.data.t2)

        })
        .catch((ex) => {
          console.log(ex);
        });
    }
    getInfos()

  }, [getAccessTokenSilently, serverUrl]);

  return (

    <Grid container columns='equal'>
      <Grid.Row >
        <Grid.Column width={4}>
          <Segment style={{ overflow: 'auto', maxHeight: '50vh' }}>
            <div><Segment inverted color='blue'>
              Friends
             </Segment></div>
            <Segment.Group vertical >
              {friends.map((friend) => (
                <Segment key={friend.idUser}><Friend user={friend} /></Segment>
              ))}
            </Segment.Group>
          </Segment>
        </Grid.Column>
        <Grid.Column width={8}>
          <Segment>
            <Card fluid>
              <Image src={monkey} size='small' centered />
              <Card.Content className="ui middle aligned divided list">
                <span className="right floated content"><ModalUpdateUser auser={user} /></span>
                <Card.Header >{user.username}</Card.Header>

                <Card.Header><small>({user.lastName} {user.firstName})</small></Card.Header>
                <Card.Meta>
                  <div><Icon name="mail" /> {user.email} </div>
                  <div><Icon name="phone" /> {user.phoneNumber} </div>
                </Card.Meta>
                <Card.Description>
                  <div>Hi, I'm {user.firstName}, I'm leave in {user.city}.</div>
                  <div><Icon name="birthday cake" />I was born on {user.birthdate}.</div>
                </Card.Description>
              </Card.Content>
              <Card.Content extra>
                <div>
                  <Icon name='user' />
                  {count} amis
                </div>
              </Card.Content>
            </Card>
          </Segment>
          <Segment style={{ overflow: 'auto', maxHeight: '50vh' }} className="ui middle">
            <Header color="blue" attached="top" block={true} dividing={true} textAlign="center">Les posts de l'users</Header>
            {postsPerso.map(p => (<PersonnalPost post={p} key={p.idPost} />))}
          </Segment>
        </Grid.Column>
        <Grid.Column width={4}>
          <Segment style={{ overflow: 'auto', maxHeight: '50vh' }}>
            <h2>Les invits reçus</h2>
            <Card.Group>
              <InvitationR />
              <InvitationR />
              <InvitationR />
              <InvitationR />
              <InvitationR />
            </Card.Group>
          </Segment>
          <Segment style={{ overflow: 'auto', maxHeight: '50vh' }}>
            <h2>Les invits envoyées</h2>
            <InvitationS />
            <InvitationS />
            <InvitationS />
            <InvitationS />
            <InvitationS />
          </Segment>
        </Grid.Column>

      </Grid.Row>

    </Grid>

  );
};

export default Profile;
