import React from "react";
import { Container, Icon, Menu } from 'semantic-ui-react'

const Footer = () => (
  <div className="ui vertical footer segment form-page">
    <Container textAlign='center'>
      M1IIIS2 - Pierre Darcas / Julien Dudek / Morgan Lombard -
      find us on
      <Menu.Item
        href="https://github.com/J-Dudek/Social-Network"
        position="right"
        target="_blank"
      >
        <Icon name="github" size="big" color="black" />
      </Menu.Item>
    </Container>
  </div>
)

export default Footer