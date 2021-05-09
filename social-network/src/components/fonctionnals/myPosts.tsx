import React, { useEffect, useState } from 'react'
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';
import { IPost } from '../../types/IPost';
import PersonnalPost from './personnalPost';
import Axios from 'axios-observable';

const defaultPersonnalPost: IPost[] = [];


const MyPosts = () => {
    const { getAccessTokenSilently } = useAuth0();
    const serverUrl = process.env.REACT_APP_SERVER_URL;
    const [postsPerso, setPosts] = useState<IPost[]>(defaultPersonnalPost);

    useEffect(() => {
        async function getMyPosts() {
            const token = await getAccessTokenSilently();
            Axios
                .get(`${serverUrl}/posts/my`, {

                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    }
                })
                .subscribe(
                    response => setPosts(response.data),
                    error => console.log(error)

                )
                ;
        }
        getMyPosts()

    }, [getAccessTokenSilently, serverUrl]);


    return (
        <>
            {postsPerso.map(post => <PersonnalPost post={post} key={post.idPost} />)}
        </>

    )
}


export default MyPosts