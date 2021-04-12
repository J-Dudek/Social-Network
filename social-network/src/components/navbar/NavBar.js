import React from "react";
import Sign from '../identity/signup-button'

import AuthNav from "./auth-nav";

const NavBar = () => {
    return (
        <div className="nav-container mb-3">
            <nav className="navbar navbar-expand-md navbar-light bg-light">
                <div className="container">
                    <div className="navbar-brand logo" />
                    <Sign/>
                    <AuthNav />
                </div>
            </nav>
        </div>
    );
};

export default NavBar;