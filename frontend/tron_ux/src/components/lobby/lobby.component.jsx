import React, { useEffect, useState, useRef } from "react";
import { makeStyles, withStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import { CircularProgress, LinearProgress } from "@material-ui/core";
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
import FileCopyIcon from "@material-ui/icons/FileCopyOutlined";
import DirectionsBikeIcon from "@material-ui/icons/DirectionsBike";

import { locationURL } from "../../helpers/helpers";

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
  copyToClipboard: {
    marginLeft: "10px",
    "&:hover": {
      cursor: "pointer",
    },
  },
  LinearProgress: {
    margin: "25px",
    wdith: "100%",
  },
  progress: {
    margin: "25px",
    wdith: "50%",
    flexGrow: "0.66",
    textAlign: "center",
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
  cycleIcon: {
    color: "#666",
    marginRight: "10px",
    verticalAlign: "middle",
  },
  readyCircularProgress: {
    marginRight: "15px",
    color: "orange",
    animationDuration: "2000ms",
  },
  waitCircularProgress: {
    marginLeft: "15px",
    color: "primary",
    animationDuration: "750ms",
  },
  root: {
    display: "flex",
    flexWrap: "wrap",
    alignItems: "center",
    justifyContent: "center",
    padding: theme.spacing(4),
    "& > *": {
      margin: theme.spacing(0),
    },
  },
  form: {
    width: "100%",
  },
  gridItem: {
    padding: theme.spacing(1),
    textAlign: "center",
    color: theme.palette.text.secondary,
  },
  cardTitle: {
    fontSize: 14,
  },
  cardShareLink: {
    backgroundColor: "#505050",
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

const LobbyTable = (props) => {
  const classes = useStyles();
  const gameLinkRef = useRef(null);
  // dispatch vars from props
  const { players, myPlayer, gameConfig, gameId, host } = props;

  // TODO: disable used colors from other users in select dropdown!
  // TODO: Send my Player updates (color/ready) to gameserver!
  const [playerState, setPlayerState] = useState(null);
  const [gameHost, setGameHost] = useState(null);
  const [myPlayerState, setMyPlayerState] = useState(null);
  const [currentGameId, setCurrentGameId] = useState(null);
  const [usedPlayerColors, setUsedPlayerColors] = useState([]);
  const [allPlayerReadyState, setAllPlayerReadyState] = useState(false);

  useEffect(() => {
    setPlayerState(players);
    var allPlayersReady = true;
    if (Array.isArray(players) && players.length) {
      players.map((player) => {
        setUsedPlayerColors(usedPlayerColors.concat(player.color));
        if (player.ready === false) allPlayersReady = false;
        return true;
      });
    }
    setAllPlayerReadyState(allPlayersReady);
    return () => {
      setUsedPlayerColors([]);
    };
    // eslint-disable-next-line
  }, [players]);

  useEffect(() => {
    setCurrentGameId(gameId);
  }, [gameId]);

  useEffect(() => {
    setMyPlayerState(myPlayer);
  }, [myPlayer]);

  useEffect(() => {
    setGameHost(host);
  }, [host]);

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

  const copyToClipboard = (event) => {
    const textToCopy = gameLinkRef.current.innerText;
    if (typeof navigator.clipboard.writeText !== "undefined") {
      // using clipboard api
      navigator.clipboard.writeText(textToCopy).then(
        function () {
          console.log("Successful copied text to clipboard.");
        },
        function () {
          console.log("Copy to clipboard failed!");
        }
      );
    } else {
      // alternative (deprecated) method
      var textArea = document.createElement("textarea");
      textArea.style.position = "fixed";
      textArea.style.top = 0;
      textArea.style.left = 0;
      textArea.style.width = "2em";
      textArea.style.height = "2em";
      textArea.style.padding = 0;
      textArea.style.border = "none";
      textArea.style.outline = "none";
      textArea.style.boxShadow = "none";
      textArea.style.background = "transparent";
      textArea.value = textToCopy;
      document.body.appendChild(textArea);
      textArea.focus();
      textArea.select();
      try {
        var msg = document.execCommand("copy") ? "successful" : "unsuccessful";
        console.log("Copying text command was " + msg);
      } catch (error) {
        console.log("ERROR: Unable to copy: " + error);
      }
      document.body.removeChild(textArea);
    }
    // set focus
    event.target.focus();
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
      {currentGameId ? (
        <React.Fragment>
          <Grid
            container
            spacing={3}
            direction="row"
            justify="center"
            alignItems="center"
          >
            <Grid item className={classes.gridItem} xs={12}>
              <Card className={classes.cardShareLink}>
                <CardContent>
                  <Typography
                    className={classes.cardTitle}
                    color="textSecondary"
                    gutterBottom
                  >
                    Share game URL
                  </Typography>
                  <Typography variant="h5" component="h2">
                    <span ref={gameLinkRef}>
                      {locationURL() + "/?id=" + currentGameId}
                    </span>
                    {
                      /* Logical shortcut for only displaying the
                       * Icon if the copy command exists */
                      (document.queryCommandSupported("copy") ||
                        typeof navigator.clipboard.writeText !==
                          "undefined") && (
                        <Tooltip
                          title="Copy to clipboard"
                          aria-label="Copy to clipboard"
                        >
                          <FileCopyIcon
                            onClick={copyToClipboard}
                            className={classes.copyToClipboard}
                            fontSize="small"
                            color="inherit"
                          />
                        </Tooltip>
                      )
                    }
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
            <Grid item className={classes.gridItem} xs={5}>
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
                    {gameConfig.name || "-"}
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
                    {gameConfig.public !== null
                      ? gameConfig.public
                        ? "public"
                        : "private"
                      : "-"}
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
                    {gameConfig.mode || "-"}
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
                    {gameConfig.playersAllowed || "-"}
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
                Leave Lobby
              </Button>
            </Grid>
            <Grid item xs={6}>
              {gameHost.clientId === myPlayer.clientId ? (
                <Button
                  variant="contained"
                  color="primary"
                  onClick={props.handleStartGame}
                  fullWidth
                  disabled={!allPlayerReadyState}
                >
                  Start Game
                </Button>
              ) : (
                <Button
                  variant="contained"
                  color="primary"
                  onClick={(e) => {
                    e.preventDefault();
                  }}
                  fullWidth
                  disabled
                >
                  Wait for other players
                  <CircularProgress
                    disableShrink
                    size={22}
                    thickness={4}
                    className={classes.waitCircularProgress}
                  />
                </Button>
              )}
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
                      {(Array.isArray(playerState) &&
                        playerState.length &&
                        playerState.map((player) => (
                          <StyledTableRow
                            key={
                              normalizeName(
                                player.playerName || player.clientId
                              ) +
                              "_" +
                              player.clientId
                            }
                            hover
                          >
                            <StyledTableCell component="th" scope="row">
                              <Tooltip
                                title={player.clientId}
                                aria-label="player id tooltip"
                              >
                                <DirectionsBikeIcon
                                  fontSize="small"
                                  className={classes.cycleIcon}
                                />
                              </Tooltip>
                              {player.playerName || player.clientId}{" "}
                              {gameHost &&
                              player.clientId === gameHost.clientId ? (
                                <Tooltip
                                  title="Game Host"
                                  aria-label="game host"
                                >
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
                              {myPlayerState.clientId === player.clientId ? (
                                // Select: Myplayers color?
                                <div className={classes.colorSelectWrapper}>
                                  <Tooltip
                                    title={myPlayerState.color}
                                    aria-label={myPlayerState.color}
                                  >
                                    <Paper
                                      elevation={3}
                                      className={classes.colorPaper}
                                      style={{
                                        backgroundColor: myPlayerState.color,
                                      }}
                                    >
                                      {"\u00A0"}
                                    </Paper>
                                  </Tooltip>
                                  <FormControl
                                    className={classes.formControl}
                                    disabled={
                                      myPlayerState.clientId !== player.clientId
                                    }
                                  >
                                    <Select
                                      value={myPlayerState.color}
                                      onChange={(e) => {
                                        handleMyPlayerChanges(e, "color");
                                      }}
                                      name={
                                        normalizeName(
                                          player.playerName || player.clientId
                                        ) + "_color"
                                      }
                                      displayEmpty
                                      className={classes.selectColor}
                                    >
                                      <MenuItem value={"red"}>Red</MenuItem>
                                      <MenuItem value={"green"}>Green</MenuItem>
                                      <MenuItem value={"lightGreen"}>
                                        Light Green
                                      </MenuItem>
                                      <MenuItem value={"lime"}>Lime</MenuItem>
                                      <MenuItem value={"black"}>Black</MenuItem>
                                      <MenuItem value={"blue"}>Blue</MenuItem>
                                      <MenuItem value={"yellow"}>
                                        Yellow
                                      </MenuItem>
                                      <MenuItem value={"lightBlue"}>
                                        Light Blue
                                      </MenuItem>
                                      <MenuItem value={"indigo"}>
                                        Indigo
                                      </MenuItem>
                                      <MenuItem value={"cyan"}>Cyan</MenuItem>
                                      <MenuItem value={"purple"}>
                                        Purple
                                      </MenuItem>
                                      <MenuItem value={"deepPurple"}>
                                        Deep Purple
                                      </MenuItem>
                                      <MenuItem value={"amber"}>Amber</MenuItem>
                                      <MenuItem value={"orange"}>
                                        Orange
                                      </MenuItem>
                                      <MenuItem value={"deepOrange"}>
                                        Deep Orange
                                      </MenuItem>
                                      <MenuItem value={"teal"}>Teal</MenuItem>
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
                                    elevation={0}
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
                              {myPlayerState.clientId === player.clientId ? ( // Switch: Myplayers ready?
                                <Switch
                                  disabled={
                                    myPlayerState.clientId !== player.clientId
                                  }
                                  checked={myPlayerState.ready}
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
                                  title="Player is not yet ready ..."
                                  aria-label="Player is not yet ready ..."
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
                              Wait for players to join ...
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
        </React.Fragment>
      ) : (
        <div className={classes.progress}>
          <div>Requesting new game ...</div>
          <div>
            <LinearProgress className={classes.LinearProgress} />
          </div>
          <div>
            <Button
              variant="outlined"
              color="secondary"
              size="small"
              onClick={props.exitLobby}
            >
              Cancel
            </Button>
          </div>
        </div>
      )}
    </div>
  );
};

export default LobbyTable;
