import React from 'react'
import { Button, Header, Image, Modal } from 'semantic-ui-react'
import Monkey from '../../images/monkeyInAsuit.jpg'
import { useForm, Controller } from "react-hook-form";
import { Form } from 'semantic-ui-react'
import ReactDatePicker from "react-datepicker";
import { useAuth0 } from "@auth0/auth0-react";
import { IUser } from '../../types/IUser';
import axios from 'axios';
type User = {
    idUser?: string;
    firstName?: string;
    lastName?: string;
    birthdate?: Date;
    email?: string;
    phoneNumber?: string;
    city?: string;
    username?: string;
    new: false;
}

function ModalUpdateUser(user?: IUser) {
    const { getAccessTokenSilently } = useAuth0();
    const serverUrl = process.env.REACT_APP_SERVER_URL;
    const [open, setOpen] = React.useState(false)
    const { handleSubmit, control } = useForm<User>();
    const setData = (data: User) => {
        console.log("data", data);
        updateInfos(data);
    };
    const [error, setError]: [string, (error: string) => void] = React.useState(
        ''
    );

    async function updateInfos(user: User) {
        const token = await getAccessTokenSilently();
        axios
            .put<IUser>(`${serverUrl}/friends/users/update`, user, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            })
            .then(() => {
                return window.location.href = '/profile';
            })
            .catch((ex) => {
                console.log(ex);
                setError(ex);
            });

    }

    return (
        <Modal
            onClose={() => setOpen(false)}
            onOpen={() => setOpen(true)}
            open={open}
            trigger={<Button>Edit Profil</Button>}
        >
            <Modal.Header>You can change your infos</Modal.Header>
            <Modal.Content image>
                <Image size='medium' src={Monkey} wrapped />
                <Modal.Description>
                    <Header>Default Profile Image</Header>
                    <Form onSubmit={handleSubmit((data) => setData(data))} >

                        <Controller control={control} name="lastName"
                            render={({ field: { onChange, onBlur, value, ref } }) => (
                                <Form.Input fluid label='Last Name'
                                    onChange={onChange}
                                    placeholder='Last Name'
                                    onBlur={onBlur}
                                    selected={value}
                                    required={true}
                                    icon='user' iconPosition='left'
                                />
                            )}
                        />
                        <Controller control={control} name="firstName"
                            render={({ field: { onChange, onBlur, value, ref } }) => (
                                <Form.Input fluid label='First Name'
                                    onChange={onChange}
                                    placeholder='First Name'
                                    onBlur={onBlur}
                                    selected={value}
                                    required={true}
                                    icon='user' iconPosition='left'
                                />
                            )}
                        />

                        <Controller control={control} name="email"
                            render={({ field: { onChange, onBlur, value, ref } }) => (
                                <Form.Input fluid label='Email'
                                    onChange={onChange}
                                    placeholder='Email'
                                    onBlur={onBlur}
                                    selected={value}
                                    required={true}
                                    icon='mail' iconPosition='left'
                                />
                            )}
                        />
                        <Controller control={control} name="phoneNumber"
                            render={({ field: { onChange, onBlur, value, ref } }) => (
                                <Form.Input fluid label='Phone Number'
                                    onChange={onChange}
                                    placeholder='Phone Number'
                                    onBlur={onBlur}
                                    selected={value}
                                    required={true}
                                    icon='phone' iconPosition='left'
                                />
                            )}
                        />
                        <Controller control={control} name="city"
                            render={({ field: { onChange, onBlur, value, ref } }) => (
                                <Form.Input fluid label='City'
                                    onChange={onChange}
                                    placeholder='City'
                                    onBlur={onBlur}
                                    selected={value}
                                    required={true}
                                    icon='home' iconPosition='left'
                                />
                            )}
                        />
                        <Controller control={control} name="username"
                            render={({ field: { onChange, onBlur, value, ref } }) => (
                                <Form.Input fluid label='Username'
                                    onChange={onChange}
                                    placeholder='Username'
                                    onBlur={onBlur}
                                    selected={value}
                                    required={true}
                                    icon='user' iconPosition='left'
                                />
                            )}
                        />
                        <div>
                            <div>Birthdate</div>
                            <Controller control={control} name="birthdate"
                                render={({ field: { onChange, onBlur, value, ref } }) => (
                                    <ReactDatePicker
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        selected={value}
                                        required={true}
                                    />
                                )}
                            />
                        </div >
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

export default ModalUpdateUser
