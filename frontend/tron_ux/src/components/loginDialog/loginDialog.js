import React from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";

const LoginDialog = (props) => {

  const handleSubmit = (event) => {
    event.preventDefault();
    // form data
    for (const [key, value] of new FormData(event.target).entries()) {
      console.log("[" + key + "]" + value);
    }
    props.handleAuth();
    props.handleClose();
  };

  return (
    <div>
      <Dialog
        open={props.open}
        onClose={props.handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Login</DialogTitle>
        <form onSubmit={handleSubmit}>
          <DialogContent>
            <DialogContentText>
              Please enter your username and password here to login.
            </DialogContentText>
            <TextField
              autoFocus
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
              margin="dense"
              id="password"
              name="password"
              label="Password"
              type="password"
              autoComplete="current-password"
              fullWidth
              required
            />
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
