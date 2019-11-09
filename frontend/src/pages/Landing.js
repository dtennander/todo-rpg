import React from "react";
import { GoogleLogin } from 'react-google-login';


const Landing = ({storeToken, handleError, clientId}) => {
    return (
        <div>
            <GoogleLogin
                clientId="848941796451-a83bla4b2vk7oqvrbti9tat7urvqd41r.apps.googleusercontent.com"
                buttonText="Login with Google"
                onSuccess={rsp => storeToken(rsp.tokenId)}
                onFailure={handleError}
                cookiePolicy={'single_host_origin'}
            />
        </div>
    );
};
export default Landing