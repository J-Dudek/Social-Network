import React from "react";
import {useAuth0} from "@auth0/auth0-react";

const ProfileButton = () => {
    return (
        <a href="/profile"><button 
            class="ui primary button"
            >Profile
        </button></a>
    );
};

export default ProfileButton;
