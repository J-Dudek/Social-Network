import React from "react";
import AuthenticationButton from './identity/authentication-button'
import {Header,  Segment } from 'semantic-ui-react'

const HeaderComponent = () => (
    <Segment clearing>
      <Header as='h2' floated='right'>
        <AuthenticationButton />                   
      </Header>
      <Header as='h2' floated='left'>
        LOGO
      </Header>
    </Segment>
  )
  
export default HeaderComponent