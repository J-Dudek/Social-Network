import React from "react";

import LoginButton from "./login-button";
import LogoutButton from "./logout-button";
import SignupButton from "./signup-button";
import {useAuth0} from "@auth0/auth0-react";

const AuthenticationButton = () => {
    const { isAuthenticated } = useAuth0();

    return isAuthenticated ? <LogoutButton /> : <div><LoginButton /> <SignupButton/> </div>;
};

export default AuthenticationButton;