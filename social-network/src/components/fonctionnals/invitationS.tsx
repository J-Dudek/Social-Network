import React from 'react'
import { Button, Card, Image } from 'semantic-ui-react'
import monkey from '../../images/monkeyInAsuit.jpg';
import { IInvit } from '../../types/IInvit';
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';

const InvitationS = ({ invit }: { invit: IInvit }) => {
    const { getAccessTokenSilently } = useAuth0();
    const serverUrl = process.env.REACT_APP_SERVER_URL;

    const cancelClick = (e) => {
        alert(invit.t2.id)
        e.preventDefault();
        cancelInvit(e)
        async function cancelInvit(e) {
            const token = await getAccessTokenSilently();
            axios
                .put<IInvit>(`${serverUrl}/friends/friendship/cancel`, invit.t2.id, {

                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                    timeout: 10000,
                })
                .then((response) => {
                    console.log("accept")
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
                    <Card.Description>
                        Since {invit.t2.date}
                    </Card.Description>
                </Card.Content>
                <Card.Content extra>
                    <Button negative onClick={cancelClick}>Cancel</Button>
                </Card.Content>
            </Card>
        </>

    )
}


export default InvitationS