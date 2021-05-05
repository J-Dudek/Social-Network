import React from 'react';
import axios  from 'axios';
import { useAuth0 } from "@auth0/auth0-react";
import monkey from '../../images/monkeyInAsuit.jpg';

import {Image} from 'semantic-ui-react'

interface IPost{
    idPost?: number;
    message?: string;
    publicationDate?: string;
    userId?: string;
    public?: string;
}

const defaultPost: IPost[] = [];

const Post = () => {
    const { getAccessTokenSilently } = useAuth0();
    const [posts,setPosts]:[IPost[],(posts: IPost[])=>void] = React.useState(
      defaultPost
    );
   
  
    const [error, setError]: [string, (error: string) => void] = React.useState(
      ''
    );
  
  React.useEffect(() => {
    const serverUrl = process.env.REACT_APP_SERVER_URL;
    async function getAll() {
      const token = await getAccessTokenSilently();
      axios
      .get<IPost[]>(`${serverUrl}/posts/all`, {
        
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        timeout: 10000,
      })
      .then((response) => {
        setPosts(response.data);
        
      })
      .catch((ex) => {
        let error = axios.isCancel(ex)
          ? 'Request Cancelled'
          : ex.code === 'ECONNABORTED'
          ? 'A timeout has occurred'
          : ex.response.status === 404
          ? 'Resource Not Found'
          : 'An unexpected error has occurred';
  
        setError(error);
        
      });
      
    }
    getAll();
    
  }, [getAccessTokenSilently]);
  
  return (
    <div className="ui items">
        <div className="item">
            {posts.map((post) =>(
                    // <div className="image">
                    //   <img src="/images/wireframe/image.png">
                    // </div>
                    <div className="content">
                      <a className="header">User id : { post.userId}</a>
                      <div className="meta">
                        <span>{ post.publicationDate }</span>
                      </div>
                      <div className="description">
                        <p></p>
                      </div>
                      <div className="extra">
                      Date : { post.publicationDate}
                      </div>
                    </div>                  
            ))}
        </div>
      
      {error && <p className="error">{error}</p>}
    </div>
  );
  };
  
  export default Post;