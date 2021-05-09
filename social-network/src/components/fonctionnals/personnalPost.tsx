import React from 'react'
import { Card, Image, Button } from 'semantic-ui-react';
import { IPost } from '../../types/IPost';
import monkey from '../../images/monkeyInAsuit.jpg';
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';


const PersonnalPost = ({ post }: { post: IPost }) => {
    const { getAccessTokenSilently } = useAuth0();
    const serverUrl = process.env.REACT_APP_SERVER_URL;

    const handleClick = (e) => {
        e.preventDefault();
        deletePost(e)
        async function deletePost(e) {
            const token = await getAccessTokenSilently();
            axios
                .delete<IPost>(`${serverUrl}/posts/${e.target.id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                    timeout: 10000,
                })
                .then((response) => {


                })
                .catch((ex) => {
                    console.log(ex);
                });

        }
    }


    return (
        <>
            <Card fluid className="postcard">
                <Card.Content>
                    <Button floated='left' icon='trash' id={post.idPost} onClick={handleClick} />
                    <Image
                        floated='right'
                        size='mini'
                        src={monkey}
                    />

                    <Card.Description>{post.message} </Card.Description>
                    <Card.Meta>{post.publicationDate?.substring(0, 10)}  {post.publicationDate?.substring(11, 19)} </Card.Meta>
                </Card.Content>

            </Card>
        </>
    )
}

export default PersonnalPost;