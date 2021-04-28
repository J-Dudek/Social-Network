import React from "react";
import AuthenticationButton from './components/header/authentication-button'
import Logo from './components/header/logo'
import {Header, Segment} from 'semantic-ui-react'

const HeaderComponent = () => (
    <Segment clearing>
      <Header as='h2' floated='right'>
        <AuthenticationButton />                   
      </Header>
      <Header as='h2' floated='left'>
        <Logo />
      </Header>
    </Segment>
  )
  
export default HeaderComponent