import React from 'react'
import { Button, Card, Image } from 'semantic-ui-react'
import monkey from '../../images/monkeyInAsuit.jpg';
const InvitationS = () => (
    <>
        <Card>
            <Card.Content>
                <Image
                    floated='right'
                    size='mini'
                    src={monkey}
                />
                <Card.Header>Steve Sanders</Card.Header>
                <Card.Description>
                    Since 20-12-2020
                </Card.Description>
            </Card.Content>
            <Card.Content extra>
                <Button negative>Cancel</Button>
            </Card.Content>
        </Card>
    </>
)

export default InvitationS