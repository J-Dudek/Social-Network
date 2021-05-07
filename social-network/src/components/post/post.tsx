import React from 'react';
import axios  from 'axios';
import { useAuth0 } from "@auth0/auth0-react";
import monkey from '../../images/monkeyInAsuit.jpg';
import CardPost from '../fonctionnals/CardPost';
import { Card, Image, Icon, Grid, Segment, Header } from "semantic-ui-react";

import { setupMaster } from 'cluster';

interface IPost{
    idPost?: number;
    message?: string;
    publicationDate?: string;
    userId?: string;
    public?: string;
}

export interface IUser{
  idUser?: number;
  firstName?: string;
  lastName?: string;
  birthdate?: string;
  email?: string;
  phoneNumber?: string;
  city?: string;
  signInDate?: string;
  username?: string;
  new?: boolean;
}

const defaultPost: IPost[] = [];

const Post = () => {
    const { getAccessTokenSilently } = useAuth0();
    const [posts,setPosts]:[IPost[],(posts: IPost[])=>void ] = React.useState(
      defaultPost
    );

    const [user,setUser]:[IPost[],(posts: IPost[])=>void ] = React.useState(
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
        console.log(ex);  
        setError(error);        
      });
      
    }
    getAll();
    
  }, [getAccessTokenSilently]);

  
  
  return (

    <Grid container columns='equal'>
        <Grid.Row >
        <Grid.Column width={10}>
            {posts.map((post) =>(
              <div>
                <CardPost post = {post} />
              </div>
                
            ))}
    
      {error && <p className="error">{error}</p>}
        </Grid.Column>
        </Grid.Row>
    </Grid>

    
  );
  };
  
  export default Post;

