import { useAuth0 } from "@auth0/auth0-react";
import { Route, Switch } from "react-router-dom";
import Loading from "./components/animations/Loading";
import HeaderComponent from "./header";
import Profile from "./components/header/profile";
import User from "./components/friend/users";
import UserPage from "./components/friend/UserPage";
import Home from "./components/home/home";
import MyFriendPost from "./components/fonctionnals/myfriendpost";
import Post from "./components/post/post";
import ProtectedRoute from "./auth/protected-route";
import { QueryClient, QueryClientProvider } from "react-query";
import Footer from "./components/footer/footer";
import FourOfour from "./components/fourOfour/fourOfour";

import "./App.css";
import "semantic-ui-css/semantic.min.css";


const queryClient = new QueryClient();

function App() {
  const { isLoading } = useAuth0();

  if (isLoading) {
    return <Loading />;
  }

  return (
    <QueryClientProvider client={queryClient}>
      <div id="app" className="d-flex flex-column h-100">
        <HeaderComponent />
        <div className="ui center aligned grid">
          <div>
            <Switch>
              <Route exact path="/" component={Home} />
              <ProtectedRoute path="/profile" exact component={Profile} />
              <ProtectedRoute path="/user/:id" component={UserPage} />
              <ProtectedRoute
                path="/myfriendpost"
                exact
                component={MyFriendPost}
              />
              <ProtectedRoute path="/users" exact component={User} />
              <ProtectedRoute path="/posts" exact component={Post} />
              <Route component={FourOfour} />
            </Switch>
          </div>
        </div>
        <Footer />
      </div>
    </QueryClientProvider>
  );
}

export default App;
