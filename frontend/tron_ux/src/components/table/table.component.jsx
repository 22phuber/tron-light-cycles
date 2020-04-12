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
});

function createData(name, mode, players, gameid) {
  return { name, mode, players, gameid };
}

const rows = [
  createData(
    "ZHAWBrainspopper",
    "Tron classic",
    "2/3",
    "c-47Bs_zTEOZ7-U7TigC7g"
  ),
  createData(
    "BoldBrainsOMG",
    "Battle Royale",
    "99/100",
    "T6FVRKkSH0e-lDdL7FtH_w"
  ),
  // createData(
  //   "ZHAW Curvaceous Hips",
  //   "Tron classic",
  //   "6/10",
  //   "ywHt7nTLXUWZd4qD-CNkrg"
  // ),
  createData("ZHAW Irish", "Tron classic", "6/8", "tlyoDxNV1EucmSlcfM-9yA"),
  // createData(
  //   "Uber Bold Maggot",
  //   "Tron classic",
  //   "2/4",
  //   "ngxTuENWsECwEwRNWuUruw"
  // ),
  createData(
    "Disguised Maggot",
    "Battle Royal",
    "66/75",
    "h5diOYzwdESSmWE6yubojQ"
  ),
  //   createData(
  //     "CurvaceousBrainsLOL",
  //     "Tron classic",
  //     "3/12",
  //     "XIARcUYdCkaorW8LJFR1xQ"
  //   ),
  //   createData("FatBrainsOMG", "Tron classic", "4/9", "wMUkfAvp2EG5kStnhP5WzQ"),
  //   createData(
  //     "BoldHipsLOL",
  //     "Battle Royale",
  //     "143/200",
  //     "yA0_e1togUqqKMZb6TxYZQ"
  //   ),
  //   createData("FatLipsLMAO", "Battle Royale", "29/30", "4Hl8BgwukUmzyy85Mo97NA"),
  //   createData("Iamcurvaceous", "Tron classic", "5/5", "_Eyv9vxwG0uIVK-Nc-HuiQ"),
];

export default function SimpleTable() {
  const classes = useStyles();

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
          {rows.map((row) => (
            <StyledTableRow key={row.name} hover>
              <StyledTableCell component="th" scope="row">
                {row.name}
              </StyledTableCell>
              <StyledTableCell align="right">{row.mode}</StyledTableCell>
              <StyledTableCell align="right">{row.players}</StyledTableCell>
              <StyledTableCell align="right">
                <Tooltip title={"Join [" + row.gameid + "]"}>
                  <IconButton color="inherit" aria-label="add to shopping cart">
                    <DirectionsBikeRoundedIcon />
                  </IconButton>
                </Tooltip>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
