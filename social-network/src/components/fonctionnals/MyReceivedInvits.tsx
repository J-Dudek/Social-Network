import React, { useEffect, useState } from 'react'
import { IInvit } from '../../types/IInvit';
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';
import Invitation from './invitationR';

const defaultInvitsReceived: IInvit[] = [];

const MyReceivedInvits = () => {
    const { getAccessTokenSilently } = useAuth0();
    const serverUrl = process.env.REACT_APP_SERVER_URL;
    const [invits, setInvitsR] = useState<IInvit[]>(defaultInvitsReceived);


    let getInvitsR = React.useCallback(async () => {
        const token = await getAccessTokenSilently();
        axios
            .get(`${serverUrl}/friends/friendship/myreceived`, {

                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                }
            })
            .then((response) => {
                let invit: IInvit[] = [];
                response.data.map(r => {
                    const inv: IInvit = {
                        t1: r.t1,
                        t2: r.t2,
                    }
                    invit.push(inv)
                }
                )

                setInvitsR(invit)
            })
            .catch((ex) => {
                console.log(ex);
            });
    }, [getAccessTokenSilently, serverUrl])

    useEffect(() => {
        getInvitsR()

    }, [getAccessTokenSilently, getInvitsR, serverUrl]);


    return (
        <>
            {invits.map((invit) => (<Invitation invit={invit} updateParent={getInvitsR} key={invit.t2.id} />))}
        </>

    )
}


export default MyReceivedInvits