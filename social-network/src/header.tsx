import React from "react";
import AuthenticationButton from './components/header/authentication-button'
import Logo from './components/header/logo'
import {Header, Segment} from 'semantic-ui-react'
import CreatePost from './components/fonctionnals/createPost'

const HeaderComponent = () => (
    <Segment clearing>
      <Header as='h2' floated='right'>
        <AuthenticationButton />           
      </Header>

      <Header as='h2' floated='left'>
        <Logo />
        <CreatePost  />        
      </Header>
    </Segment>
  )
  
export default HeaderComponent