import React from 'react'

import { Form } from 'semantic-ui-react'
import { useForm, Controller } from "react-hook-form";
import ReactDatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import axios from 'axios';
import { useAuth0 } from "@auth0/auth0-react";
import { IUser } from '../types/IUser'

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


const UpdateUser = () => {

  const { getAccessTokenSilently } = useAuth0();
  const serverUrl = process.env.REACT_APP_SERVER_URL;
  const { handleSubmit, control } = useForm<User>();
  const setData = (data: User) => {

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
        return window.location.href = '/';
      })
      .catch((ex) => {
        console.log(ex);
        setError(ex);
      });

  }
  return (
    <>
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
        <button className="ui primary button" type='submit'> Save </button>

      </Form>

    </>
  );
}

export default UpdateUser