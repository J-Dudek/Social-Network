import React from 'react'
import { Card,Image,Segment } from 'semantic-ui-react';
import { IUser } from '../../types/IUser';
import { IPost } from '../../types/IPost';
import monkey from '../../images/monkeyInAsuit.jpg';
import { useAuth0 } from "@auth0/auth0-react";
import axios  from 'axios';

const defaultUser: IUser = {};

const CardPost =({ post }:{ post:IPost })=>  {

    
        const { getAccessTokenSilently } = useAuth0();
        const [user,setUser]:[IUser,(user: IUser)=> void] = React.useState(
          defaultUser
        );
       
      
        const [error, setError]: [string, (error: string) => void] = React.useState(
          ''
        );
      
      React.useEffect(() => {
        const serverUrl = process.env.REACT_APP_SERVER_URL;
        async function getAll() {
          const token = await getAccessTokenSilently();
          axios
          .get<IUser>(`${serverUrl}/friends/users/${post.userId}`, {
            
            headers: {
              Authorization: `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
            timeout: 10000,
          })
          .then((response) => {
            setUser(response.data);
            
          })
          .catch((ex) => {
             console.log(ex);
            setError("error");
            
          });
          
        }
        getAll();
        
      }, [getAccessTokenSilently]);
    
    return (
        <>
        <Card fluid className="postcard">
            <Card.Content>
            <Image
                    floated='right'
                    size='mini'
                    src={monkey}
                />
              <Card.Header>{user.firstName}  {user.lastName}</Card.Header>
            
              <Card.Description>{post.message} </Card.Description>
              <Card.Meta>{post.publicationDate?.substring(0,10)}  {post.publicationDate?.substring(11,19)} </Card.Meta>
            </Card.Content>
            
        </Card>
        </>
    )
}

export default CardPost;