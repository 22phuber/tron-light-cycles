import React from "react";
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
import { changeValues } from "../../helpers/api";

const useStyles = makeStyles((theme) => ({
    formControl: {
      margin: theme.spacing(0),
      marginTop: theme.spacing(2),
      minWidth: 120,
      width: '100%',
    },
    colorSelect: {
      width: '100%',
    },
  }));

  const ProfileDialog = (props) => {
    const classes = useStyles();
    //const { username, password, color, email } = props;
    const [state, setState] = React.useState({
      color: "black",
    });

    const handleSubmit = (event) => {
      event.preventDefault();
      // form data
      for (const [key, value] of new FormData(event.target).entries()) {
        console.log("[" + key + "]" + value);
      }
      props.handleClose();
      changeValues.(user, handleChange);
    };

    const handleChange = (event) => {
      const name = event.target.name;
      setState({
        ...state,
        [name]: event.target.value,
      });
    };

    return (
      <div>
        <Dialog
          open={props.open}
          onClose={props.handleClose}
          aria-labelledby="profile-dialog"
        >
          <DialogTitle id="profile-dialog">My Account</DialogTitle>
          <form onSubmit={handleSubmit}>
            <DialogContent>
              <DialogContentText>
                Check your own profile and adjust your settings!
              </DialogContentText>
              <TextField
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
                helperText="Change your password"
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
                helperText="Change your E-Mail"
              />
              <Grid container spacing={3}>
                <Grid item xs={12}>
                  <FormControl className={classes.formControl}>
                    <InputLabel htmlFor="color-native-helper">Color</InputLabel>
                    <NativeSelect
                      value={state.color}
                      onChange={handleChange}
                      className={classes.colorSelect}
                      inputProps={{
                        name: "color",
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
                    <FormHelperText>Change your preferred color</FormHelperText>
                  </FormControl>
                </Grid>
              </Grid>
            </DialogContent>
            <DialogActions>
              <Button onClick={props.handleClose} color="primary">
                Cancel
              </Button>
              <Button type="submit" color="default">
                Save
              </Button>
            </DialogActions>
          </form>
        </Dialog>
      </div>
    );
  };

  export default ProfileDialog;