import React from "react";
import { Feed } from "semantic-ui-react";
import Monkey from "../../images/monkeyInAsuit.jpg";
import { IUser } from "../../types/IUser";
import { Link } from "react-router-dom";

const Friend = ({ user }: { user: IUser }) => (

  <Feed>
    <Feed.Event>
      <Feed.Label image={Monkey} />
      <Feed.Content>
        <Feed.Summary>
          <Link to={`/user/${user.idUser}`}>
            {user.firstName} {user.lastName}
          </Link>
        </Feed.Summary>
      </Feed.Content>
    </Feed.Event>
  </Feed>

);

export default Friend;
