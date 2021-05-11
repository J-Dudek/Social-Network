import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { IUser } from "../../types/IUser";
import axios from "axios";
import { Card, Image, Icon, Grid, Segment, Header } from "semantic-ui-react";
import monkey from '../../images/monkeyInAsuit.jpg';
import ModalUpdateUser from '../friend/modalUpdateUser'
import MySentInvit from '../fonctionnals/mySentInvits'
import MyReceivedInvits from '../fonctionnals/MyReceivedInvits'
import MyFriends from '../fonctionnals/myfriends'
import MyPosts from '../fonctionnals/myPosts'

const defaultUser: IUser = {};

const Profile = () => {
  const [user, setUser] = useState<IUser>(defaultUser);
  const [count, setCount] = useState<number>(0);

  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;


  useEffect(() => {

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
            <div><Segment inverted textAlign="center" color='blue'>
              Friends
             </Segment></div>
            <Segment.Group vertical >
              <MyFriends />
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
            <Header color="blue" attached="top" block={true} dividing={true} textAlign="center">My posts</Header>
            <MyPosts />
          </Segment>
        </Grid.Column>
        <Grid.Column width={4}>
          <Segment style={{ overflow: 'auto', maxHeight: '50vh' }}>
            <div><Segment inverted textAlign="center" color='blue'>
              Received Invitations
             </Segment></div>

            <MyReceivedInvits />

          </Segment>
          <Segment style={{ overflow: 'auto', maxHeight: '50vh' }}>
            <div><Segment inverted textAlign="center" color='blue'>
              Pending invitation
             </Segment></div>
            <MySentInvit />
          </Segment>
        </Grid.Column>

      </Grid.Row>

    </Grid>

  );
};

export default Profile;
