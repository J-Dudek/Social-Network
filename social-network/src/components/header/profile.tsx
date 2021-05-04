import React from "react";
import {Card, Icon, Image} from 'semantic-ui-react'


import {useAuth0} from "@auth0/auth0-react";


const Profile = () => {
    const { user } = useAuth0();
    const { name, picture, email } = user;

    return (
        
        <div className="ui centered card">
            <Card>
              <Image src={picture} wrapped ui={false} />
              <Card.Content>
                <Card.Header>{name}</Card.Header>
                <Card.Meta>
                  <span className='date'>{email}</span>
                </Card.Meta>
                <Card.Description>
                  Garde t-on la desc?
                </Card.Description>
              </Card.Content>
              <Card.Content extra>
                <div>
                  <Icon name='user' />
                  ici le nobre d'ami
                </div>
              </Card.Content>
            </Card>
            
        </div>
        
    );
};

export default Profile;