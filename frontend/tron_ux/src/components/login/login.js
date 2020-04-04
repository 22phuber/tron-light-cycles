import React, {Component} from 'react';
import Dialog from "@material-ui/core/Dialog";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";

class Login extends Component {
  render() {
    return (
      <Dialog 
        open 
        onRequestClose={this.props.toggleLogin}
        fullScreen={this.props.fullScreen}>
        <DialogTitle>Login</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Username"
            type="text"
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Password"
            type="password"
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={this.props.toggleLogin} color="primary">
            Cancel
          </Button>
          <Button onClick={this.props.toggleLogin} color="primary">
            Login
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}

export default Login;