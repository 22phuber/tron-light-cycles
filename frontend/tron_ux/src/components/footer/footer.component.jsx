import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import { Typography, Link } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  root: {
    padding: theme.spacing(2),
  },
}));

const Footer = () => {
  const classes = useStyles();

  return (
    <footer className={classes.root}>
      <Typography variant="body2" color="textSecondary" align="center">
        <Link
          color="inherit"
          href="https://github.com/22phuber/tron-light-cycles"
        >
          tron-light-cycles on github.com
        </Link>
        <br />
        {"- a ZHAW project -"}
        <br />
        {"Copyright © "}
        {new Date().getFullYear()}
        {""}
      </Typography>
    </footer>
  );
};

export default Footer;
