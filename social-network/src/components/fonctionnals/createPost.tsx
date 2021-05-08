import React , { useEffect, useState }from 'react'
import { useAuth0 } from "@auth0/auth0-react";
import axios  from 'axios';
import { IPost } from '../../types/IPost';
import { IUser } from '../../types/IUser';
import { useForm, Controller } from "react-hook-form";

import {Modal , Form, Button,  Select,} from 'semantic-ui-react';

type Post = {
    idPost?: number;
    message?: string;
    publicationDate?: string ;
    userId?: string;
    public?: boolean;
}



const isPublic = [
    { key: '0', text: '0', value: false },
    { key: '1', text: '1', value: true },
  ]

const defaultUser: IUser = {};
var defaultPublic: boolean = false;

function CreatePost(){
    const { getAccessTokenSilently } = useAuth0();
    const [user, setUser] = useState<IUser>(defaultUser);
    const serverUrl = process.env.REACT_APP_SERVER_URL;
    const [open, setOpen] = React.useState(false)
    const { handleSubmit, control } = useForm<Post>();
    const   handleDropDownSelect = (event, data) => {
           defaultPublic = data.value;
    };       
    const setData = (data: Post) => {
        data.public = defaultPublic;
        updateInfos(data);
    };



    useEffect(() => {
        // Create an scoped async function in the hook
    
        async function getInfos() {
          const token = await getAccessTokenSilently();
          axios
            .get(`${serverUrl}/friends/users/aboutUser`, {
    
              headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json',
              }
            })
            .then((response) => {
              setUser(response.data.t1)
            })
            .catch((ex) => {
              console.log(ex);
            });
        }
        getInfos()
    
      }, [getAccessTokenSilently, serverUrl]);

    async function updateInfos(post: Post) {
        const token = await getAccessTokenSilently();
        post.userId = user.idUser;
        axios
            .post<IPost>(`${serverUrl}/posts`, post, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            })
            .then(() => {
                return window.location.href = '/posts';
            })
            .catch((ex) => {
                console.log(ex);

            });

    }


    return (
        <Modal

            onClose={() => setOpen(false)}
            onOpen={() => setOpen(true)}
            open={open}
            trigger={<Button>Ecrire un post</Button>}
        >
            <Modal.Header>You can change your infos</Modal.Header>
            <Modal.Content image>
                <Modal.Description>
                    <Form onSubmit={handleSubmit((data) => setData(data))} >

                    <Controller control={control} name="public"
                            render={({ field: { onChange, onBlur, value, ref } }) => (
                                <Form.Field
                                control={Select}
                                options={isPublic}
                                label={{ children: 'Public', htmlFor: 'public' }}
                                onChange={handleDropDownSelect}
                                placeholder='Public'
                                selected={value}
                                search
                                searchInput={{ id: 'public' }}
                              />
                            
                              
                            )}

                        />


                        <Controller control={control} name="message"
                            render={({ field: { onChange, onBlur, value, ref } }) => (
                                <Form.TextArea fluid label='Message'
                                    onChange={onChange}
                                    placeholder='Message'
                                    onBlur={onBlur}
                                    selected={value}
                                    required={true}
                                    icon='message' iconPosition='left'
                                />
                            )}
                        />

                        
                        <Modal.Actions>
                            <Button color='black' onClick={() => setOpen(false)}>
                                Cancel
                            </Button>
                            <Button
                                content="Save"
                                labelPosition='right'
                                icon='checkmark'
                                onClick={() => handleSubmit}
                                positive
                            />
                        </Modal.Actions>

                    </Form>
                </Modal.Description>
            </Modal.Content>

        </Modal>
    )
}

export default CreatePost;