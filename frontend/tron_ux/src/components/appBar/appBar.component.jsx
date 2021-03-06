import React, { useEffect, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import AccountCircle from "@material-ui/icons/AccountCircle";
import LoginDialog from "../loginDialog/loginDialog.component";
import RegisterDialog from "../registerDialog/registerDialog.component";
import ProfileDialog from "../profileDialog/profileDialog.component";

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
  myPlayer: {
    paddingRight: theme.spacing(2),
  },
}));

const TronAppBar = (props) => {
  const classes = useStyles();
  const [auth, setAuth] = useState(false);
  const [openLoginDialog, setOpenLoginDialog] = useState(false);
  const [openRegisterDialog, setOpenRegisterDialog] = useState(false);
  const [openProfileDialog, setOpenProfileDialog] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const [myPlayer, setMyPlayer] = useState("");
  const menuOpen = Boolean(anchorEl);

  useEffect(() => {
    setMyPlayer(props.myPlayer.username);
  }, [props.myPlayer]);

  const handleClose = () => {
    setAnchorEl(null);
  };

  const showLoginDialog = () => {
    setOpenLoginDialog(true);
  };

  const hideLoginDialog = () => {
    setOpenLoginDialog(false);
  };

  const showRegisterDialog = () => {
    setOpenRegisterDialog(true);
  };

  const hideRegisterDialog = () => {
    setOpenRegisterDialog(false);
  };
  const hideProfileDialog = () => {
    setOpenProfileDialog(false);
  };

  const handleLogin = (data) => {
    props.handleMyPlayerData(data);
    setAuth(true);
  };

  const handleLogout = () => {
    setAuth(false);
    setAnchorEl(null);
  };

  const handleMyAccount = () => {
    setOpenProfileDialog(true);
  };

  const handleToggle = (event) => {
    setAnchorEl(event.currentTarget);
  };

  return (
    <div className={classes.root}>
      <LoginDialog
        open={openLoginDialog}
        handleClose={hideLoginDialog}
        handleAuth={handleLogin}
      />
      <RegisterDialog
        open={openRegisterDialog}
        handleClose={hideRegisterDialog}
      />
      <ProfileDialog
        open={openProfileDialog}
        handleClose={hideProfileDialog}
      />
      <AppBar color="inherit" position="static">
        <Toolbar>
          <Typography variant="h6" className={classes.title}>
            Tron light cycle game
          </Typography>
          {!auth ? (
            <React.Fragment>
              <div className={classes.myPlayer}>username: [{myPlayer}]</div>
              <div>
                <Button color="inherit" onClick={showRegisterDialog}>
                  Register
                </Button>
                <Button color="inherit" onClick={showLoginDialog}>
                  Login
                </Button>
              </div>
            </React.Fragment>
          ) : (
            <React.Fragment>
              <div className={classes.myPlayer}>username: [{myPlayer}]</div>
              <div>
                <IconButton
                  aria-label="account of current user"
                  aria-controls="menu-appbar"
                  aria-haspopup="true"
                  onClick={handleToggle}
                  color="inherit"
                >
                  <AccountCircle />
                </IconButton>
                <Menu
                  id="menu-appbar"
                  anchorEl={anchorEl}
                  anchorOrigin={{
                    vertical: "top",
                    horizontal: "right",
                  }}
                  keepMounted
                  transformOrigin={{
                    vertical: "top",
                    horizontal: "right",
                  }}
                  open={menuOpen}
                  onClose={handleClose}
                >
                  <MenuItem onClick={handleMyAccount}>My Account</MenuItem>
                  <MenuItem onClick={handleLogout}>Logout</MenuItem>
                </Menu>
              </div>
            </React.Fragment>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
};

export default TronAppBar;
