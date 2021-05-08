import React from 'react'
import { Card, Image, Icon, Button } from 'semantic-ui-react';
import { IPost } from '../../types/IPost';
import monkey from '../../images/monkeyInAsuit.jpg';
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';


const PersonnalPost = ({ post }: { post: IPost }) => {
    const { getAccessTokenSilently } = useAuth0();


    const serverUrl = process.env.REACT_APP_SERVER_URL;

    function handleClick(idPost) {
        async function deletePost(idPost) {
            const token = await getAccessTokenSilently();
            axios
                .delete<IPost>(`${serverUrl}/posts/${idPost}`, {

                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                    timeout: 10000,
                })
                .then((response) => {
                    console.log(response.data)

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
                    <Button floated='left' icon='trash' />
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