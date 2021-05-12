import React, { useState, useEffect } from "react";
import { withRouter, RouteComponentProps } from "react-router-dom";
import UserPagePublic from "../friend/UserPagePublic";
import UserPagePrivate from "../friend/UserPagePrivate";
import Loader from "../animations/Loading";
import { useAuth0 } from "@auth0/auth0-react";
import axios from "axios";

interface RouterProps {
  id: string;
}

const defaultIsFriend: boolean = false;

interface UserIdProps extends RouteComponentProps<RouterProps> { }

const UserPage: React.FC<UserIdProps> = ({ match }) => {
  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;
  const [isFriend, setIsFriend] = useState<boolean>(defaultIsFriend);
  const [go, start] = useState<boolean>(false);

  useEffect(() => {
    const fetchIsFriend = async () => {
      const token = await getAccessTokenSilently();

      axios
        .get(`${serverUrl}/friends/friendship/verify/${match.params.id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          setIsFriend(response.data);
          console.log(response.data);
          start(true)
        })
        .catch((ex) => {
          console.log(ex);
        });
    };
    fetchIsFriend();
  }, [getAccessTokenSilently, match.params.id, serverUrl]);

  return (
    <>
      {go ? (
        isFriend ? (
          <UserPagePublic userId={match.params.id} />
        ) : (
          <UserPagePrivate userId={match.params.id} />
        )
      ) : (
        <Loader />
      )}
    </>
  );
};

export default UserPage;
