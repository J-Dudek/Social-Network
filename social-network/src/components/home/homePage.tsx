import React from 'react';

import { Grid, Segment, Header } from "semantic-ui-react";
import MySentInvit from "../fonctionnals/mySentInvits";
import MyReceivedInvits from "../fonctionnals/MyReceivedInvits";
import MyFriends from "../fonctionnals/myfriends";
import MyFriendPost from "../fonctionnals/myfriendpost";
import SearchFriends from '../fonctionnals/searchfriends'


const HomePage = ({ register = false }: { register: Boolean }) => {
  if (!register) {
    return null;
  }

  return (
    <Grid container columns="equal">
      <Grid.Row>
        <Grid.Column width={4}>
          <Segment style={{ overflow: "auto", maxHeight: "50vh" }}>
            <div>
              <Segment inverted textAlign="center" color="blue">
                Search friends
              </Segment>
            </div>
            <Segment.Group vertical>
              <SearchFriends />
            </Segment.Group>
          </Segment>
          <Segment style={{ overflow: "auto", maxHeight: "50vh" }}>
            <div>
              <Segment inverted textAlign="center" color="blue">
                Friends
              </Segment>
            </div>
            <Segment.Group vertical>
              <MyFriends />
            </Segment.Group>
          </Segment>
          
        </Grid.Column>
        <Grid.Column width={8}>
          <Segment>
            <Header
              color="blue"
              attached="top"
              block={true}
              dividing={true}
              textAlign="center"
            >
              Les posts de mes amis
            </Header>
            <MyFriendPost />
          </Segment>
        </Grid.Column>
        <Grid.Column width={4}>
          <Segment style={{ overflow: "auto", maxHeight: "50vh" }}>
            <div>
              <Segment inverted textAlign="center" color="blue">
                Received Invitations
              </Segment>
            </div>

            <MyReceivedInvits />
          </Segment>
          <Segment style={{ overflow: "auto", maxHeight: "50vh" }}>
            <div>
              <Segment inverted textAlign="center" color="blue">
                Pending invitation
              </Segment>
            </div>
            <MySentInvit />
          </Segment>
        </Grid.Column>
      </Grid.Row>
    </Grid>
  );
};

export default HomePage;
