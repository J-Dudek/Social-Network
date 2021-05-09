import React from 'react'
import { Card, Image, Button, Radio } from 'semantic-ui-react';
import { IPost } from '../../types/IPost';
import monkey from '../../images/monkeyInAsuit.jpg';
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';


type PropsFunction = () => Promise<void>;
const PersonnalPost = ({ post, updateParent }: { post: IPost, updateParent: PropsFunction }) => {
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
                    updateParent()

                })
                .catch((ex) => {
                    console.log(ex);
                });

        }
    }
    const changeStatus = (e) => {
        e.preventDefault();
        statusChange()
        async function statusChange() {
            const token = await getAccessTokenSilently();
            axios
                .put<IPost>(`${serverUrl}/posts/update-status`, post.idPost, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                    timeout: 10000,
                })
                .then((response) => {
                    updateParent()

                })
                .catch((ex) => {
                    console.log(ex);
                });

        }
    }


    return (
        <>
            <Card fluid className="postcard" color={post.public ? ("green") : ("red")}>
                <Card.Content>
                    <Button floated='left' icon='trash' id={post.idPost} onClick={handleClick} />

                    <Image
                        floated='right'
                        size='mini'
                        src={monkey}
                    />

                    <Card.Description>{post.message} </Card.Description>
                    <Card.Meta>
                        {post.publicationDate?.substring(0, 10)}  {post.publicationDate?.substring(11, 19)}
                        <div className="status"><Radio toggle
                            label={post.public ? ("Public") : ("Private")}
                            checked={post.public}
                            onChange={changeStatus} /></div>
                    </Card.Meta>




                </Card.Content>

            </Card>
        </>
    )
}

export default PersonnalPost;