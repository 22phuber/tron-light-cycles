import React, { useEffect } from "react";
import { makeStyles, withStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import IconButton from "@material-ui/core/IconButton";
import DirectionsBikeRoundedIcon from "@material-ui/icons/DirectionsBikeRounded";
import Tooltip from "@material-ui/core/Tooltip";
import CircularProgress from "@material-ui/core/CircularProgress";
import Grid from "@material-ui/core/Grid";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";
import Button from "@material-ui/core/Button";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import FormHelperText from "@material-ui/core/FormHelperText";
import Switch from "@material-ui/core/Switch";
import Select from "@material-ui/core/Select";

const StyledTableCell = withStyles((theme) => ({
  head: {
    backgroundColor: "rgba(0,0,0,0.25)",
    color: theme.palette.common.white,
  },
  body: {
    fontSize: 14,
  },
}))(TableCell);

const StyledTableRow = withStyles(() => ({
  root: {
    "&:nth-of-type(odd)": {
      backgroundColor: "rgba(0,0,0,0.1)",
    },
  },
  hover: {
    "&$hover:hover": {
      backgroundColor: "rgba(0,0,0,0.22)",
    },
  },
}))(TableRow);

const useStyles = makeStyles({
  table: {
    minWidth: 700,
    height: 200,
  },
  circularProgress: {
    margin: "25px",
  },
  root: {
    display: "flex",
    flexWrap: "wrap",
    padding: "16px",
  },
  form: {
    width: "100%",
  },
});

const LobbyTable = (props) => {
  const classes = useStyles();

  const { lobbyPlayers, myPlayerId } = props;

  const [myPlayerColor, setMyPlayerColor] = React.useState("Black");
  let [readyStates, setReadyStateStates] = React.useState({});

  const headerCells = (
    <TableRow>
      <StyledTableCell>PLAYERNAME</StyledTableCell>
      <StyledTableCell align="right">COLOR</StyledTableCell>
      <StyledTableCell align="right">READY</StyledTableCell>
    </TableRow>
  );
  const handleSubmit = (event) => {
    event.preventDefault();
    // form data
    for (const [key, value] of new FormData(event.target).entries()) {
      console.log("[" + key + "]" + value);
    }
  };

  useEffect(() => {
    if (lobbyPlayers) {
      lobbyPlayers.map((player) => {
        if (myPlayerId == player.id) {
          setMyPlayerColor(player.color);
        }
        console.log("name" + player.name + " state" + player.readyState);
        readyStates = { ...readyStates, [player.name]: player.readyState }
      });
    }
    setReadyStateStates(readyStates);
    console.log(readyStates);
  }, []);

  const handleChangeColor = (event) => {
    setMyPlayerColor(event.target.value);
  };

  const handleChangeReady = (event) => {
    setReadyStateStates({
      ...readyStates,
      [event.target.name]: event.target.checked,
    });
  };

  return (
    <div className={classes.root}>
      <form onSubmit={handleSubmit} className={classes.form}>
        <Grid container spacing={1}>
          <Grid item>Lobbyname: Test</Grid>
          <Grid item>Visibility: private</Grid>
          <Grid item>Game mode: Tron classic</Grid>
          <Grid item>Max. Players: 10</Grid>
          <Grid item>
            <TableContainer
              component={Paper}
              className={classes.tableContainer}
            >
              <Table
                className={classes.table}
                size="small"
                aria-label="lobby player list table"
              >
                <TableHead>{headerCells}</TableHead>
                <TableBody>
                  {(lobbyPlayers &&
                    lobbyPlayers.map((player) => (
                      <StyledTableRow key={player.name + "_" + player.id} hover>
                        <StyledTableCell component="th" scope="row">
                          {player.name}
                        </StyledTableCell>
                        <StyledTableCell align="right">
                          <FormControl
                            className={classes.formControl}
                            disabled={myPlayerId != player.id}
                          >
                            <Select
                              value={
                                myPlayerId != player.id
                                  ? player.color
                                  : myPlayerColor
                              }
                              onChange={handleChangeColor}
                              displayEmpty
                              className={classes.selectEmpty}
                            >
                              <MenuItem value="">
                                <em>None</em>
                              </MenuItem>
                              <MenuItem value={"red"}>Red</MenuItem>
                              <MenuItem value={"green"}>Green </MenuItem>
                              <MenuItem value={"black"}>Black </MenuItem>
                              <MenuItem value={"blue"}>Blue</MenuItem>
                              <MenuItem value={"pink"}>Pink</MenuItem>
                              <MenuItem value={"gray"}>Gray</MenuItem>
                            </Select>
                          </FormControl>
                        </StyledTableCell>
                        <StyledTableCell align="right">
                          <Switch
                            disabled={myPlayerId != player.id}
                            checked={readyStates[player.name]}
                            onChange={handleChangeReady}
                            color="primary"
                            name="checkedB"
                            inputProps={{ "aria-label": "ready" }}
                          />
                        </StyledTableCell>
                      </StyledTableRow>
                    ))) || (
                    <StyledTableRow hover>
                      <StyledTableCell colSpan={4} align="center">
                        <div>
                          Loading players ...
                          <br />
                          <CircularProgress
                            color="inherit"
                            className={classes.circularProgress}
                          />
                        </div>
                      </StyledTableCell>
                    </StyledTableRow>
                  )}
                </TableBody>
              </Table>
            </TableContainer>
          </Grid>
          <Grid item>
            <Button
              variant="contained"
              color="primary"
              type="submit"
              spacing={1}
            >
              Start
            </Button>
          </Grid>
        </Grid>
      </form>
    </div>
  );
};

export default LobbyTable;
