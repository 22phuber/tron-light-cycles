import React from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import Typography from "@material-ui/core/Typography";
import { signIn } from "../../helpers/api";

const LoginDialog = (props) => {
  const [loginFailed, setLoginFailed] = React.useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();
    // form data
    let user = {};
    for (const [key, value] of new FormData(event.target).entries()) {
      user[key] = value;
    }
    // try to signIn user
    signIn(user, handleAuth);
  };

  const handleAuth = (data) => {
    switch (data.responseCode) {
      case 200:
        setLoginFailed(false);
        props.handleAuth(data);
        props.handleClose();
        break;
      default:
        setLoginFailed(true);
        break;
    }
  };

  const handleClick = () => {
    if (loginFailed) {
      setLoginFailed(false);
    }
  };

  return (
    <div>
      <Dialog
        open={props.open}
        onClose={props.handleClose}
        aria-labelledby="login-dialog"
      >
        <DialogTitle id="login-dialog">Login</DialogTitle>
        <form onSubmit={handleSubmit}>
          <DialogContent>
            <DialogContentText>
              Please enter your username and password here to login.
            </DialogContentText>
            <TextField
              autoFocus
              onClick={handleClick}
              margin="dense"
              id="username"
              name="username"
              label="Username"
              type="text"
              autoComplete="username"
              fullWidth
              required
            />
            <TextField
              onClick={handleClick}
              margin="dense"
              id="password"
              name="password"
              label="Password"
              type="password"
              autoComplete="current-password"
              fullWidth
              required
            />
            {loginFailed && (
              <Typography variant="subtitle1" color="error">
                Unknown username or wrong password! Please try again
              </Typography>
            )}
          </DialogContent>
          <DialogActions>
            <Button onClick={props.handleClose} color="primary">
              Cancel
            </Button>
            <Button type="submit" color="default">
              Login
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </div>
  );
};

export default LoginDialog;
