import React from 'react';
import {useAuth0} from "@auth0/auth0-react";
import {Switch} from "react-router-dom";
import 'semantic-ui-css/semantic.min.css'
import Loading from './components/animations/Loading';
import HeaderComponent from './components/header'
import Profile from './components/identity/profile'
import ProtectedRoute from "./auth/protected-route";
import ExternalApi from './api/external-api';

function App() {
  const { isLoading } = useAuth0();

  if (isLoading) {
    return <Loading />;
  }

  return (
     <div id="app" className="d-flex flex-column h-100">
      <HeaderComponent />
      <div className="container flex-grow-1">
        <Switch>
          
          <ProtectedRoute path="/profile" component={Profile} />
          <ProtectedRoute path="/external-api" component={ExternalApi} />
        </Switch>
      </div>
    </div>
  );
}

export default App;
