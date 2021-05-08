import React , { useEffect, useState }from 'react'
import { useAuth0 } from "@auth0/auth0-react";
import axios  from 'axios';
import { IPost } from '../../types/IPost';
import { IUser } from '../../types/IUser';
import { useForm, Controller } from "react-hook-form";
<<<<<<< refs/remotes/origin/postcard
<<<<<<< refs/remotes/origin/postcard
import {Modal , Checkbox, Form, Icon, Input, Button, Header, Radio, Select, TextArea,} from 'semantic-ui-react';
=======
import {Modal , Form, Icon, Button, Header} from 'semantic-ui-react';
>>>>>>> :construction: edit post
=======
import {Modal , Checkbox, Form, Icon, Input, Button, Header, Radio, Select, TextArea,} from 'semantic-ui-react';
>>>>>>> :poop: post edition

type Post = {
    idPost?: number;
    message?: string;
    publicationDate?: string ;
    userId?: string;
<<<<<<< refs/remotes/origin/postcard
<<<<<<< refs/remotes/origin/postcard
    public?: number;
}

const isPublic = [
    { key: '0', text: '0', value: 0 },
    { key: '1', text: '1', value: 1 },
  ]

<<<<<<< refs/remotes/origin/postcard
=======
    public?: string;
}

>>>>>>> :construction: edit post
=======
    public?: number;
}

const isPublic = [
    { key: '0', text: '0', value: 0 },
    { key: '1', text: '1', value: 1 },
  ]

>>>>>>> :poop: post edition
=======
const defaultUser: IUser = {};

>>>>>>> :adhesive_bandage: edit post user id
function CreatePost(){
    const { getAccessTokenSilently } = useAuth0();
    const [user, setUser] = useState<IUser>(defaultUser);
    const [count, setCount] = useState<number>(0);
    const serverUrl = process.env.REACT_APP_SERVER_URL;
    const [open, setOpen] = React.useState(false)
    const { handleSubmit, control } = useForm<Post>();
    const setData = (data: Post) => {
        console.log("toto data", data);
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
              setCount(response.data.t2)
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
                console.log("tot" + ex);

            });

    }

    return (
        <Modal

            onClose={() => setOpen(false)}
            onOpen={() => setOpen(true)}
            open={open}
            trigger={<Button>Show Modal</Button>}
        >
            <Modal.Header>You can change your infos</Modal.Header>
            <Modal.Content image>
                <Modal.Description>
                    <Form onSubmit={handleSubmit((data) => setData(data))} >

<<<<<<< refs/remotes/origin/postcard
<<<<<<< refs/remotes/origin/postcard
=======
>>>>>>> :poop: post edition
                    <Controller control={control} name="public"
                            render={({ field: { onChange, onBlur, value, ref } }) => (
                                <Form.Field
                                control={Select}
                                options={isPublic}
                                label={{ children: 'Public', htmlFor: 'public' }}
                                placeholder='Public'
                                selected={value}
                                search
                                searchInput={{ id: 'public' }}
                              />
                            )}

                        />

<<<<<<< refs/remotes/origin/postcard
=======
>>>>>>> :construction: edit post
=======
>>>>>>> :poop: post edition
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
<<<<<<< refs/remotes/origin/postcard
<<<<<<< refs/remotes/origin/postcard

                        
=======
>>>>>>> :construction: edit post
=======

                        
>>>>>>> :poop: post edition
                        
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