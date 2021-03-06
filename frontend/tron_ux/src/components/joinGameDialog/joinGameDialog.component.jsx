import React from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";

const JoinGameDialog = (props) => {
  return (
    <div>
      <Dialog
        open={props.open}
        onClose={props.handleClose}
        aria-labelledby="login-dialog"
      >
        <DialogTitle id="login-dialog">Join game</DialogTitle>
        <DialogContent>
          <DialogContentText>Do you want to join this game?</DialogContentText>
          <TextField
            id="game-id-read-only-input"
            label="Game ID"
            defaultValue={props.joinGameState.gameId}
            InputProps={{
              readOnly: true,
            }}
          />
          {props.joinGameState.name && (
            <TextField
              id="game-name-read-only-input"
              label="Game name"
              defaultValue={props.joinGameState.name}
              InputProps={{
                readOnly: true,
              }}
            />
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={props.handleClose} color="primary">
            Cancel
          </Button>
          <Button color="default" onClick={props.handleJoinGame}>
            Join
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default JoinGameDialog;
