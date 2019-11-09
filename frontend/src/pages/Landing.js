import React, {useEffect} from "react";
import { GoogleLogin } from 'react-google-login';
import {createUseStyles, useTheme} from "react-jss";

const useStyles = createUseStyles(theme => ({
    title: {
        color: theme.primary,
        margin: 0,
        padding: "15px"
    },
    container: {
        height: "100vh",
        backgroundColor: theme.white,
        color: theme.black
    },
    login: {
        width: "200px",
        height: "50px",

    }
}));

const Landing = ({storeToken, handleError, clientId}) => {
    const theme = useTheme();
    const styles = useStyles(theme);

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>ToDo RPG</h1>
            <GoogleLogin className={styles.login}
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