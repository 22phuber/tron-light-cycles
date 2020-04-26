import React, { useEffect, useState } from "react";
import { makeStyles, withStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import CircularProgress from "@material-ui/core/CircularProgress";
import Grid from "@material-ui/core/Grid";
import FormControl from "@material-ui/core/FormControl";
import Button from "@material-ui/core/Button";
import MenuItem from "@material-ui/core/MenuItem";
import Switch from "@material-ui/core/Switch";
import Select from "@material-ui/core/Select";
import Tooltip from "@material-ui/core/Tooltip";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import HomeIcon from "@material-ui/icons/Home";
import HowToRegIcon from "@material-ui/icons/HowToReg";

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

const useStyles = makeStyles((theme) => ({
  table: {
    minWidth: "90%",
    minHeight: 200,
  },
  circularProgress: {
    margin: "25px",
  },
  readyHowToRegIcon: {
    color: "green",
    marginRight: "15px",
  },
  hostHomeIcon: {
    color: "green",
    marginLeft: "5px",
    verticalAlign: "middle",
  },
  readyCircularProgress: {
    marginRight: "15px",
    color: "orange",
    animationDuration: "2000ms",
  },
  root: {
    display: "flex",
    flexWrap: "wrap",
    padding: theme.spacing(4),
    "& > *": {
      margin: theme.spacing(0),
    },
  },
  form: {
    width: "100%",
  },
  gridItem: {
    padding: theme.spacing(2),
    textAlign: "center",
    color: theme.palette.text.secondary,
  },
  cardTitle: {
    fontSize: 14,
  },
  card: {
    backgroundColor: "#636363",
  },
  selectColor: {
    width: "100%",
  },
  formControl: {
    width: "50%",
  },
  colorPaper: {
    width: "50%",
  },
  colorSelectWrapper: {
    display: "flex",
  },
}));

// TODO: Show Generated Game Link

const LobbyTable = (props) => {
  const classes = useStyles();
  // dispatch vars from props
  const { players, myPlayer, gameConfig } = props;

  // TODO: disable used colors from other users in select dropdown!
  const [playerState, setPlayerState] = useState(null);
  const [usedPlayerColors, setUsedPlayerColors] = useState([]);

  useEffect(() => {
    if (players) {
      players.map((player) => {
        setUsedPlayerColors(usedPlayerColors.concat(player.color));
        return true;
      });
    }
    // add myPlayer as first player in array
    setPlayerState([myPlayer, ...players]);
    console.log(gameConfig);
    console.log(players);
  }, [players, myPlayer, gameConfig]);

  const handleMyPlayerChanges = (event, setting) => {
    switch (setting) {
      case "ready":
        props.handleMyPlayer("ready", event.target.checked);
        break;
      case "color":
        props.handleMyPlayer("color", event.target.value);
        break;
      default:
        break;
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // form data
    for (const [key, value] of new FormData(event.target).entries()) {
      console.log("[" + key + "]" + value);
    }
  };

  /**
   * Replaces ' ' (space or multiple spaces) with '_' (underline)
   * @param {*} name
   */
  const normalizeName = (name) => {
    return name.replace(/\s+/g, "_").toLowerCase();
  };

  const headerCells = (
    <TableRow>
      <StyledTableCell>PLAYERNAME</StyledTableCell>
      <StyledTableCell align="right">COLOR</StyledTableCell>
      <StyledTableCell align="right">READY</StyledTableCell>
    </TableRow>
  );

  return (
    <div className={classes.root}>
      <form onSubmit={handleSubmit} className={classes.form}>
        <Grid
          container
          spacing={5}
          direction="row"
          justify="center"
          alignItems="center"
        >
          <Grid item className={classes.gridItem} xs={4}>
            <Card className={classes.card}>
              <CardContent>
                <Typography
                  className={classes.cardTitle}
                  color="textSecondary"
                  gutterBottom
                >
                  Lobbyname
                </Typography>
                <Typography variant="h5" component="h2">
                  {gameConfig.name}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item className={classes.gridItem} xs>
            <Card className={classes.card}>
              <CardContent>
                <Typography
                  className={classes.cardTitle}
                  color="textSecondary"
                  gutterBottom
                >
                  Visibility
                </Typography>
                <Typography variant="h5" component="h2">
                  {gameConfig.public ? "public" : "private"}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item className={classes.gridItem} xs>
            <Card className={classes.card}>
              <CardContent>
                <Typography
                  className={classes.cardTitle}
                  color="textSecondary"
                  gutterBottom
                >
                  Game mode
                </Typography>
                <Typography variant="h5" component="h2">
                  {gameConfig.mode}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item className={classes.gridItem} xs>
            <Card className={classes.card}>
              <CardContent>
                <Typography
                  className={classes.cardTitle}
                  color="textSecondary"
                  gutterBottom
                >
                  Max. Players
                </Typography>
                <Typography variant="h5" component="h2">
                  {gameConfig.playersAllowed}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
        <Grid container spacing={5} justify="center">
          <Grid item xs={3}>
            <Button
              variant="contained"
              color="secondary"
              fullWidth
              onClick={props.exitLobby}
            >
              Cancel
            </Button>
          </Grid>
          <Grid item xs={6}>
            <Button variant="contained" color="primary" type="submit" fullWidth>
              Start
            </Button>
          </Grid>
          <Grid container spacing={1}>
            <Grid item xs={12}>
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
                    {(playerState &&
                      playerState.map((player) => (
                        <StyledTableRow
                          key={
                            normalizeName(player.name) + "_" + player.clientId
                          }
                          hover
                        >
                          <StyledTableCell component="th" scope="row">
                            {player.name}{" "}
                            {player.clientId === gameConfig.host ? (
                              <Tooltip title="Game Host" aria-label="game host">
                                <HomeIcon
                                  fontSize="small"
                                  className={classes.hostHomeIcon}
                                />
                              </Tooltip>
                            ) : (
                              "\u00A0"
                            )}
                          </StyledTableCell>
                          <StyledTableCell align="right">
                            {myPlayer.clientId === player.clientId ? (
                              // Select: Myplayers color?
                              <div className={classes.colorSelectWrapper}>
                                <Tooltip
                                  title={myPlayer.color}
                                  aria-label={myPlayer.color}
                                >
                                  <Paper
                                    elevation={3}
                                    className={classes.colorPaper}
                                    style={{
                                      backgroundColor: myPlayer.color,
                                    }}
                                  >
                                    {"\u00A0"}
                                  </Paper>
                                </Tooltip>
                                <FormControl
                                  className={classes.formControl}
                                  disabled={
                                    myPlayer.clientId !== player.clientId
                                  }
                                >
                                  <Select
                                    value={myPlayer.color}
                                    onChange={(e) => {
                                      handleMyPlayerChanges(e, "color");
                                    }}
                                    name={normalizeName(player.name) + "_color"}
                                    displayEmpty
                                    className={classes.selectColor}
                                  >
                                    <MenuItem value={"red"}>Red</MenuItem>
                                    <MenuItem value={"green"}>Green </MenuItem>
                                    <MenuItem value={"black"}>Black </MenuItem>
                                    <MenuItem value={"blue"}>Blue</MenuItem>
                                    <MenuItem value={"pink"}>Pink</MenuItem>
                                    <MenuItem value={"gray"}>Gray</MenuItem>
                                  </Select>
                                </FormControl>
                              </div>
                            ) : (
                              // player color as paper
                              <Tooltip
                                title={player.color}
                                aria-label={player.color}
                              >
                                <Paper
                                  elevation={3}
                                  style={{
                                    backgroundColor: player.color,
                                  }}
                                >
                                  {"\u00A0"}
                                </Paper>
                              </Tooltip>
                            )}
                          </StyledTableCell>
                          <StyledTableCell align="right">
                            {myPlayer.clientId === player.clientId ? ( // Switch: Myplayers ready?
                              <Switch
                                disabled={myPlayer.clientId !== player.clientId}
                                checked={myPlayer.ready}
                                onChange={(e) => {
                                  handleMyPlayerChanges(e, "ready");
                                }}
                                color="primary"
                                inputProps={{ "aria-label": "ready" }}
                              />
                            ) : // ICON: players ready?
                            /true/i.test(player.ready) ? (
                              <Tooltip title="ready" aria-label="ready">
                                <HowToRegIcon
                                  className={classes.readyHowToRegIcon}
                                />
                              </Tooltip>
                            ) : (
                              <Tooltip
                                title="Waiting for player ..."
                                aria-label="Waiting for player ..."
                              >
                                <CircularProgress
                                  variant="indeterminate"
                                  disableShrink
                                  size={22}
                                  thickness={4}
                                  className={classes.readyCircularProgress}
                                />
                              </Tooltip>
                            )}
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
          </Grid>
        </Grid>
      </form>
    </div>
  );
};

export default LobbyTable;
