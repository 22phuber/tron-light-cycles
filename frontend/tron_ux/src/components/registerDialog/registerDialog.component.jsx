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
import InputLabel from '@material-ui/core/InputLabel';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import NativeSelect from '@material-ui/core/NativeSelect';

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 120,
  },
}));

const colors = [{}];

const RegisterDialog = (props) => {
  const classes = useStyles();

  const [state, setState] = React.useState({
    age: "",
    name: "hai",
  });

  const handleSubmit = (event) => {
    event.preventDefault();
    // form data
    for (const [key, value] of new FormData(event.target).entries()) {
      console.log("[" + key + "]" + value);
    }
    // TODO: register user
    // props.handleAuth();
    props.handleClose();
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
        aria-labelledby="register-dialog"
      >
        <DialogTitle id="register-dialog">Register</DialogTitle>
        <form onSubmit={handleSubmit}>
          <DialogContent>
            <DialogContentText>
              Create an Account to join the Tron light cycle universe and enable
              additional features.
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
              id="name"
              name="name"
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
              margin="dense"
              id="password"
              name="password"
              label="Password"
              type="password"
              autoComplete="current-password"
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
              <Grid item xs={6}>
                <FormControl className={classes.formControl}>
                  <InputLabel htmlFor="age-native-helper">Age</InputLabel>
                  <NativeSelect
                    value={state.age}
                    onChange={handleChange}
                    inputProps={{
                      name: "age",
                      id: "age-native-helper",
                    }}
                  >
                    <option aria-label="None" value="" />
                    <option value={10}>Ten</option>
                    <option value={20}>Twenty</option>
                    <option value={30}>Thirty</option>
                  </NativeSelect>
                  <FormHelperText>Some important helper text</FormHelperText>
                </FormControl>
              </Grid>
            </Grid>
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
      </Dialog>
    </div>
  );
};

export default RegisterDialog;

// class Register extends Component {
//   render() {
//     return (
//       <Dialog
//         open
//         onRequestClose={this.props.toggleRegister}
//         fullScreen={this.props.fullScreen}>
//         <DialogTitle>Registration</DialogTitle>
//         <DialogContent>
//           <TextField
//             autoFocus
//             margin="dense"
//             id="name"
//             label="Username"
//             type="text"
//             fullWidth
//           />
//           <TextField
//             autoFocus
//             margin="dense"
//             id="name"
//             label="Password"
//             type="password"
//             fullWidth
//           />
//           <TextField
//             autoFocus
//             margin="dense"
//             id="name"
//             label="Email"
//             type="email"
//             fullWidth
//           />
//         </DialogContent>
//         <DialogActions>
//           <Button onClick={this.props.toggleRegister} color="primary">
//             Cancel
//           </Button>
//           <Button onClick={this.props.toggleRegister} color="primary">
//             OK
//           </Button>
//         </DialogActions>
//       </Dialog>
//     );
//   }
// }

// export default Register;
