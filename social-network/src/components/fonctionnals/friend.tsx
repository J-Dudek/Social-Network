import React from 'react'
import { Feed } from 'semantic-ui-react'
import Monkey from '../../images/monkeyInAsuit.jpg'

const Friend = () => (
    <>
    <Feed>
        <Feed.Event>
            <Feed.Label image={Monkey} />
            <Feed.Content>
                <Feed.Date>Since 21-12-2020</Feed.Date>
                <Feed.Summary>
                    Bob is your friend
        </Feed.Summary>

            </Feed.Content>
        </Feed.Event>
        </Feed>
        </>
)

export default Friend