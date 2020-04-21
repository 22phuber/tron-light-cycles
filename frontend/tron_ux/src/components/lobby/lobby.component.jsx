import React from "react";
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
});

const LobbyTable = (props) => {
  const classes = useStyles();

  const { lobbyPlayers } = props;

  const headerCells = (
    <TableRow>
      <StyledTableCell>PLAYERNAME</StyledTableCell>
      <StyledTableCell align="right">COLOR</StyledTableCell>
      <StyledTableCell align="right">READY</StyledTableCell>
    </TableRow>
  );

  return (
    <TableContainer component={Paper} className={classes.tableContainer}>
      <Table
        className={classes.table}
        size="small"
        aria-label="lobby player list table"
      >
        <TableHead>{headerCells}</TableHead>
        <TableBody>
          {(lobbyPlayers &&
            lobbyPlayers.map((players) => (
              <StyledTableRow key={players.name + "_" + players.id} hover>
                <StyledTableCell component="th" scope="row">
                  {players.name}
                </StyledTableCell>
                <StyledTableCell align="right">{players.color}</StyledTableCell>
                <StyledTableCell align="right">{players.name}</StyledTableCell>
                <StyledTableCell align="right">

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
  );
};

export default LobbyTable;
