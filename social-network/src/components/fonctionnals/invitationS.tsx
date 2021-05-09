import React from 'react'
import { Button, Card, Image } from 'semantic-ui-react'
import monkey from '../../images/monkeyInAsuit.jpg';
import { IInvit } from '../../types/IInvit';
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';
type PropsFunction = () => Promise<void>;
const InvitationS = ({ invit, updateParent }: { invit: IInvit, updateParent: PropsFunction }) => {
    const { getAccessTokenSilently } = useAuth0();
    const serverUrl = process.env.REACT_APP_SERVER_URL;

    const cancelClick = (e) => {
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
                    updateParent()
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