import React, { useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";


const ExternalApi = () => {
    const [message, setMessage] = useState("");
    const serverUrl = process.env.REACT_APP_SERVER_URL;

    const { getAccessTokenSilently } = useAuth0();


    const callApi = async () => {
        try {
            const token = await getAccessTokenSilently();

            const response = await fetch(
                `${serverUrl}/friends/users/61`,
                {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            const responseData = await response.json();

            setMessage(JSON.stringify(responseData));
        } catch (error) {
            setMessage(error.message);
        }
    };

    const callSecureApi = async () => {
        try {
            const token = await getAccessTokenSilently();

            const response = await fetch(
                `${serverUrl}/posts/all2`,
                {
                    method: 'GET',
                    mode: "cors",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    },
                }
            );

            const responseData = await response.json();

            setMessage(JSON.stringify(responseData));
        } catch (error) {
            setMessage(error.message);
        }
    };

    return (
        <div className="container">
            <h1>External API</h1>
            <p>
                Use these buttons to call an external API. The protected API call has an
                access token in its authorization header. The API server will validate
                the access token using the Auth0 Audience value.
      </p>
            <div
                className="btn-
                group mt-5"
                role="group"
                aria-label="External API Requests Examples"
            >
                <button className="py-2  px-4 bg-green-500 text-white font-semibold rounded-lg shadow-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-400 focus:ring-opacity-75" type="button" onClick={callApi}>
                    fetch private users
        </button>

                <button className="py-2 ml-8 px-4 bg-green-500 text-white font-semibold rounded-lg shadow-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-400 focus:ring-opacity-75" type="button"
                    onClick={callSecureApi}>
                    All post appel secure
                    </button>

            </div>
            {message && (
                <div className="mt-5">
                    <h6 className="muted">Result</h6>
                    <div className="container-fluid">
                        <div className="row">
                            <code className="col-12 text-light bg-dark p-4">{message}</code>
                        </div>
                    </div>
                </div>
            )}

        </div>
    );
};

export default ExternalApi;

