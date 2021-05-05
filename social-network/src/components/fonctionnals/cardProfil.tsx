import React from 'react'
import { Card,Image } from 'semantic-ui-react';
import { IUser } from '../../types/IUser';
import monkey from '../../images/monkeyInAsuit.jpg';

const CardProfil =({ user }:{ user:IUser })=>  {
    
    return (
        <>
        <Card key={user.idUser}>
            <Image
              floated='right'
              size='mini'
              src={monkey}
            />
            <Card.Content>
              <Card.Header>{user.firstName}  {user.lastName}</Card.Header>
            </Card.Content>
            <Card.Meta>Lives in : {user.city} Join : {user.signInDate}</Card.Meta>
            <Card.Description>
              <div>
                Email : {user.email}
              </div>
              <div>
                Phone : {user.phoneNumber}
              </div>
        </Card.Description>
    </Card>
    </>
    )
}
export default CardProfil;
// ({budgets}: BudgetProps)