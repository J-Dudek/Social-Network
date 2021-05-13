import React from 'react'
import { Button, Card, Image } from 'semantic-ui-react'
import monkey from '../../images/monkeyInAsuit.jpg';
import { IInvit } from '../../types/IInvit';
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';
type PropsFunction = () => Promise<void>;

const InvitationR = ({ invit, updateParent }: { invit: IInvit, updateParent: PropsFunction }) => {
    const { getAccessTokenSilently } = useAuth0();
    const serverUrl = process.env.REACT_APP_SERVER_URL;

    const acceptClick = (e) => {
        e.preventDefault();
        acceptInvit(e)
        async function acceptInvit(e) {
            const token = await getAccessTokenSilently();
            axios
                .put<IInvit>(`${serverUrl}/friends/friendship/accept`, invit.t2.id, {

                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                    timeout: 10000,
                })
                .then((response) => {
                    console.log(response)
                    updateParent()
                })
                .catch((ex) => {
                    console.log(ex);
                });
        }
    }
    const declineClick = (e) => {
        e.preventDefault();
        declineInvit(e)
        async function declineInvit(e) {
            const token = await getAccessTokenSilently();
            axios
                .delete(`${serverUrl}/friends/friendship/reject/${invit.t2.id}`, {

                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                    timeout: 10000,
                })
                .then((response) => {
                    updateParent()
                    console.log(response.data)
                })
                .catch((ex) => {
                    console.log(ex);
                });
        }
    }

    return (

        <>
            <Card>
                <Card.Content>
                    <Image
                        floated='right'
                        size='mini'
                        src={monkey}
                    />
                    <Card.Header>{invit.t1.firstName} {invit.t1.lastName}</Card.Header>
                    <Card.Meta>{invit.t1.username}</Card.Meta>
                    <Card.Description>
                        want to be your friend. since {invit.t2.date}
                    </Card.Description>
                </Card.Content>
                <Card.Content extra>
                    <div className='ui two buttons'>
                        <Button onClick={declineClick}>Decline</Button>
                        <Button.Or />
                        <Button positive onClick={acceptClick}>Accept</Button>
                    </div>
                </Card.Content>
            </Card>
        </>
    )

}

export default InvitationR