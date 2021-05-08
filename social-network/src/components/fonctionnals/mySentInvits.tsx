import React, { useEffect, useState } from 'react'
import { IInvit } from '../../types/IInvit';
import { useAuth0 } from "@auth0/auth0-react";
import axios from 'axios';
import Invitation from './invitationS';

const defaultInvitsSent: IInvit[] = [];

const MySentInvits = () => {
    const { getAccessTokenSilently } = useAuth0();
    const serverUrl = process.env.REACT_APP_SERVER_URL;
    const [invitsS, setInvitsS] = useState<IInvit[]>(defaultInvitsSent);

    useEffect(() => {
        async function getMyInvits() {
            const token = await getAccessTokenSilently();
            axios
                .get(`${serverUrl}/friends/friendship/mysent`, {

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

                    setInvitsS(invit)
                })
                .catch((ex) => {
                    console.log(ex);
                });
        }
        getMyInvits()

    }, [getAccessTokenSilently, serverUrl]);


    return (
        <>
            {invitsS.map(invit => <Invitation invit={invit} key={invit.t2.id} />)}
        </>

    )
}


export default MySentInvits