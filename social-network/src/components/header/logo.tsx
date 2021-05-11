import React from "react";
import { Image } from 'semantic-ui-react'
import logo from '../../images/logo2.jpg'

const Logo = () => {
    return (
        <Image as='a'
            size='large'
            href='/'
            src={logo} className="App-logo"/>
    );
};

export default Logo;
