import { useAuth0 } from "@auth0/auth0-react";
import { Route, Switch } from "react-router-dom";
import "semantic-ui-css/semantic.min.css";
import Loading from "./components/animations/Loading";
import HeaderComponent from "./header";
import Profile from "./components/header/profile";
import User from "./components/friend/users";
// import UserPage from "./components/friend/UserPage";
import Home from "./components/home/home";
import MyFriendPost from "./components/fonctionnals/myfriendpost";
import Post from "./components/post/post";
import ProtectedRoute from "./auth/protected-route";
import ExternalApi from "./api/external-api";
import "./App.css";
import { QueryClient, QueryClientProvider } from "react-query";
import Footer from './components/footer/footer'

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
              {/* <Profile /> */}
              <Route exact path="/" component={Home} />
              <ProtectedRoute path="/profile" component={Profile} />
              {/* <ProtectedRoute path="/user/:id" component={UserPage} /> */}
              <ProtectedRoute path="/external-api" component={ExternalApi} />
              <ProtectedRoute path="/myfriendpost" component={MyFriendPost} />
              <ProtectedRoute path="/users" component={User} />
              <ProtectedRoute path="/posts" component={Post} />
            </Switch>
          </div>
        </div>
        <Footer/>
      </div>
    </QueryClientProvider>
  );
}

export default App;
