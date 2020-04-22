import React from "react";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";

const styles = (theme) => ({
  background: {
    backgroundPosition: "center",
  },
  paper: {
    marginTop: theme.spacing(20),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },

  submit: {
    margin: theme.spacing(2, 0, 2),
  },
});

function initial(props) {
  const { classes } = props;

  return (
    <Container component="main">
      <div className={classes.paper} maxWidth="xs">
        <Button
          type="submit"
          variant="contained"
          color="primary"
          className={classes.submit}
        >
          Register
        </Button>
        <Button
          type="submit"
          variant="contained"
          color="primary"
          className={classes.submit}
        >
          Guest Login
        </Button>

        <Button
          type="submit"
          variant="contained"
          color="primary"
          className={classes.submit}
        >
          Login
        </Button>
      </div>
    </Container>
  );
}
export default withStyles(styles)(initial);
