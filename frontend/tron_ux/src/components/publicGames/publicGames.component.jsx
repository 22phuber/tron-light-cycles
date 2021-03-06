import React, { useEffect, useState } from "react";
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
import { CircularProgress } from "@material-ui/core";

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
  progress: {
    margin: "25px",
  },
});

const GameTable = (props) => {
  const classes = useStyles();

  const { publicGames } = props;
  const [noGamesState, setNoGamesState] = useState(false);

  useEffect(() => {
    setNoGamesState(false);
    setTimeout(function () {
      if (
        publicGames &&
        Array.isArray(publicGames) &&
        publicGames.length <= 0
      ) {
        setNoGamesState(true);
      }
    }, 3000);
  }, [publicGames]);

  const headerCells = (
    <TableRow>
      <StyledTableCell>NAME</StyledTableCell>
      <StyledTableCell align="right">MODE</StyledTableCell>
      <StyledTableCell align="right">PLAYERS</StyledTableCell>
      <StyledTableCell align="right">JOIN</StyledTableCell>
    </TableRow>
  );

  return (
    <TableContainer component={Paper} className={classes.tableContainer}>
      <Table
        className={classes.table}
        size="small"
        aria-label="public game list table"
      >
        <TableHead>{headerCells}</TableHead>
        <TableBody>
          {Array.isArray(publicGames) && publicGames.length > 0 ? (
            publicGames.map((game) => (
              <StyledTableRow key={game.name + "_" + game.id} hover>
                <StyledTableCell component="th" scope="row">
                  {game.name}
                </StyledTableCell>
                <StyledTableCell align="right">{game.mode}</StyledTableCell>
                <StyledTableCell align="right">
                  {game.playersJoined + "/" + game.playersAllowed}
                </StyledTableCell>
                <StyledTableCell align="right">
                  <Tooltip title={'Join "' + game.name + '"'}>
                    <IconButton
                      color="inherit"
                      aria-label="join game"
                      onClick={(e) =>
                        props.handleJoinGame({
                          gameId: game.id,
                          name: game.name,
                        })
                      }
                    >
                      <DirectionsBikeRoundedIcon />
                    </IconButton>
                  </Tooltip>
                </StyledTableCell>
              </StyledTableRow>
            ))
          ) : noGamesState ? (
            <StyledTableRow hover>
              <StyledTableCell colSpan={4} align="center">
                <div>
                  <em>
                    No public games are running <br />
                    Trying again in a few seconds ...
                  </em>
                </div>
              </StyledTableCell>
            </StyledTableRow>
          ) : (
            <StyledTableRow hover>
              <StyledTableCell colSpan={4} align="center">
                <div>
                  <em>Searching for public games ...</em>
                  <br />
                  <CircularProgress
                    color="secondary"
                    className={classes.progress}
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

export default GameTable;
