import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { IPost } from "../../types/IPost";
import CardPost from "./CardPost";
import Axios from "axios-observable";

const defaultPersonnalPost: IPost[] = [];

const UserPosts = ({ id, isPublic }) => {
  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;
  const [postsPerso, setPosts] = useState<IPost[]>(defaultPersonnalPost);

  let getMyPosts = React.useCallback(async () => {
    const token = await getAccessTokenSilently();
    Axios.get(`${serverUrl}/posts/all${isPublic ? "" : "/public"}/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }).subscribe(
      (response) => setPosts(response.data),
      (error) => console.log(error)
    );
  }, [getAccessTokenSilently, id, isPublic, serverUrl]);

  useEffect(() => {
    getMyPosts();
  }, [getMyPosts]);

  return (
    <>
      {postsPerso.map((post) => (
        <CardPost
          post={post}
        />
      ))}
    </>
  );
};

export default UserPosts;
