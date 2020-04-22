import React, { useEffect } from "react";
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
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";

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
}));

const LobbyTable = (props) => {
  const classes = useStyles();

  const { lobbyData, lobbyPlayers, myPlayerId } = props;
  const [myPlayerColor, setMyPlayerColor] = React.useState("black");
  // TODO: disable used colors from other users in select dropdown!
  const [usedPlayerColors, setUsedPlayerColors] = React.useState([]);
  const [myReadyState, setMyReadyState] = React.useState(false);

  useEffect(() => {
    if (lobbyPlayers) {
      lobbyPlayers.map((player) => {
        if (myPlayerId === player.id) {
          setMyPlayerColor(player.color);
          setMyReadyState(true); // set current player to ready
        }
        console.log(player.color);
        setUsedPlayerColors(usedPlayerColors.concat(player.color));
        return true;
      });
    }
  }, [lobbyPlayers, myPlayerId, usedPlayerColors]);

  const handleChangeColor = (event) => {
    setMyPlayerColor(event.target.value);
  };

  const handleMyReadyState = (event) => {
    setMyReadyState(event.target.checked);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // form data
    for (const [key, value] of new FormData(event.target).entries()) {
      console.log("[" + key + "]" + value);
    }
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
                  {lobbyData.gamename || "GameName"}
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
                  {lobbyData.visibility || "public"}
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
                  {lobbyData.mode || "classic"}
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
                  {lobbyData.maxplayers || "5"}
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
                    {(lobbyPlayers &&
                      lobbyPlayers.map((player) => (
                        <StyledTableRow
                          key={
                            player.name.replace(/\s+/g, "-").toLowerCase() +
                            "_" +
                            player.id
                          }
                          hover
                        >
                          <StyledTableCell component="th" scope="row">
                            {player.name}
                          </StyledTableCell>
                          <StyledTableCell align="right">
                            <FormControl
                              className={classes.formControl}
                              disabled={myPlayerId !== player.id}
                            >
                              <Select
                                value={
                                  myPlayerId !== player.id
                                    ? player.color
                                    : myPlayerColor
                                }
                                onChange={handleChangeColor}
                                name={player.name + "_color"}
                                displayEmpty
                                className={classes.selectEmpty}
                              >
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
                              disabled={myPlayerId !== player.id}
                              checked={
                                myPlayerId !== player.id
                                  ? /true/i.test(player.readyState)
                                  : myReadyState
                              }
                              onChange={handleMyReadyState}
                              color="primary"
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
          </Grid>
        </Grid>
      </form>
    </div>
  );
};

export default LobbyTable;
