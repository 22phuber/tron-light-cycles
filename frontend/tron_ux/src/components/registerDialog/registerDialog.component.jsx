import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Dialog from "@material-ui/core/Dialog";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import Grid from "@material-ui/core/Grid";
import InputLabel from "@material-ui/core/InputLabel";
import FormHelperText from "@material-ui/core/FormHelperText";
import FormControl from "@material-ui/core/FormControl";
import NativeSelect from "@material-ui/core/NativeSelect";
import { signUp } from "../../helpers/api";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(0),
    marginTop: theme.spacing(2),
    minWidth: 120,
    width: "100%",
  },
  colorSelect: {
    width: "100%",
  },
}));

const RegisterDialog = (props) => {
  const classes = useStyles();
  const [registerState, setRegisterState] = useState({
    success: false,
    failed: false,
  });

  const [state, setState] = useState({
    cycle_color: "black",
    password: "",
    password_repeat: "",
  });
  const [formErrorState, setFormErrorState] = useState({ password: false });

  const handleSubmit = (event) => {
    event.preventDefault();
    // form data
    let user = {};
    for (const [key, value] of new FormData(event.target).entries()) {
      if (key === "lastname") {
        user["name"] = value;
      }
      user[key] = value;
    }
    signUp(user, handleRegister);
  };

  const handleRegister = (data) => {
    switch (data.responseCode) {
      case 200:
        setRegisterState((prevRegisterState) => {
          return { ...prevRegisterState, success: true };
        });
        break;
      default:
        setRegisterState((prevRegisterState) => {
          return { ...prevRegisterState, failed: true };
        });
        break;
    }
  };

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    switch (name) {
      case "password":
      case "password_repeat":
        if (state.password === value) {
          setFormErrorState((prevFormErrorState) => {
            return { ...prevFormErrorState, password: false };
          });
        } else {
          setFormErrorState((prevFormErrorState) => {
            return { ...prevFormErrorState, password: true };
          });
        }
        break;
      default:
        break;
    }
    setState((prevState) => {
      return { ...prevState, [name]: value };
    });
  };

  return (
    <div>
      <Dialog
        open={props.open}
        onClose={props.handleClose}
        aria-labelledby="register-dialog"
      >
        <DialogTitle id="register-dialog">Register</DialogTitle>
        {!registerState.success ? (
          <form onSubmit={handleSubmit}>
            <DialogContent>
              <DialogContentText>
                Create an Account to join the Tron light cycle universe and
                enable additional features.
              </DialogContentText>
              <TextField
                autoFocus
                margin="dense"
                id="firstname"
                name="firstname"
                label="Firstname"
                type="text"
                autoComplete="firstname"
                fullWidth
                required
              />
              <TextField
                margin="dense"
                id="lastname"
                name="lastname"
                label="Name"
                type="text"
                autoComplete="lastname"
                fullWidth
                required
              />
              <TextField
                margin="dense"
                id="username"
                name="username"
                label="Username"
                type="text"
                autoComplete="username"
                fullWidth
                required
                helperText="This is also your players username"
              />
              <TextField
                error={formErrorState.password}
                margin="dense"
                id="password"
                name="password"
                label="Password"
                type="password"
                onChange={handleChange}
                autoComplete="current-password"
                fullWidth
                required
              />
              <TextField
                error={formErrorState.password}
                margin="dense"
                id="password_repeat"
                name="password_repeat"
                label="Confirm password"
                type="password"
                onChange={handleChange}
                autoComplete="confirm-password"
                fullWidth
                required
              />
              <TextField
                margin="dense"
                id="email"
                name="email"
                label="E-Mail"
                type="text"
                autoComplete="email"
                fullWidth
                required
              />
              <Grid container spacing={3}>
                <Grid item xs={12}>
                  <FormControl className={classes.formControl}>
                    <InputLabel htmlFor="color-native-helper">Color</InputLabel>
                    <NativeSelect
                      value={state.cycle_color}
                      onChange={handleChange}
                      className={classes.colorSelect}
                      inputProps={{
                        name: "cycle_color",
                        id: "color-native-helper",
                      }}
                    >
                      <option value={"red"}>Red</option>
                      <option value={"green"}>Green</option>
                      <option value={"black"}>Black</option>
                      <option value={"blue"}>Blue</option>
                      <option value={"pink"}>Pink</option>
                      <option value={"gray"}>Gray</option>
                    </NativeSelect>
                    <FormHelperText>Choose your players color</FormHelperText>
                  </FormControl>
                </Grid>
              </Grid>
              {registerState.failed && (
                <Typography variant="subtitle1" color="error">
                  Username already exists! Please change the username and try
                  again
                </Typography>
              )}
            </DialogContent>
            <DialogActions>
              <Button onClick={props.handleClose} color="primary">
                Cancel
              </Button>
              <Button type="submit" color="default">
                Register
              </Button>
            </DialogActions>
          </form>
        ) : (
          <React.Fragment>
            <DialogContent>
              <DialogContentText>
                You have successfully registered... AWESOME!
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button onClick={props.handleClose} color="default">
                OK
              </Button>
            </DialogActions>
          </React.Fragment>
        )}
      </Dialog>
    </div>
  );
};

export default RegisterDialog;
