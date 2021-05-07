import React from 'react'
import { Button, Card, Image } from 'semantic-ui-react'
import monkey from '../../images/monkeyInAsuit.jpg';
const InvitationR = () => (
    <>
        <Card fluid>
            <Card.Content>
                <Image
                    floated='right'
                    size='mini'
                    src={monkey}
                />
                <Card.Header>Steve Sanders</Card.Header>
                <Card.Meta>Friends of Elliot</Card.Meta>
                <Card.Description>
                    Steve wants to add you to the group <strong>best friends</strong>
                </Card.Description>
            </Card.Content>
            <Card.Content extra>
                <div className='ui two buttons'>
                    <Button>Decline</Button>
                    <Button.Or />
                    <Button positive>Accept</Button>
                </div>
            </Card.Content>
        </Card>
    </>
)

export default InvitationR