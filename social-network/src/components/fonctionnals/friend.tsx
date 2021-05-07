import React from 'react'
import { Feed } from 'semantic-ui-react'
import Monkey from '../../images/monkeyInAsuit.jpg'
import { IUser } from '../../types/IUser'

const Friend = ({ user }: { user: IUser }) => (
    <a href="/profile">
        <Feed>
            <Feed.Event>
                <Feed.Label image={Monkey} />
                <Feed.Content>
                    <Feed.Summary>
                        {user.firstName} {user.lastName} is your friend
        </Feed.Summary>

                </Feed.Content>
            </Feed.Event>
        </Feed>
    </a>
)

export default Friend