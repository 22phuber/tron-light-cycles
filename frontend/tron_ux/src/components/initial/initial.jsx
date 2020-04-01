import React from "react";
import { AppBar, Toolbar, Typography } from "@material-ui/core";


const initial = () => {
    return (

        <AppBar position="static">
        <Toolbar>
            <Typography variant="headline" gutterBottom>
                Test
            </Typography>
        </Toolbar>
    </AppBar>

    )
};

export default initial;