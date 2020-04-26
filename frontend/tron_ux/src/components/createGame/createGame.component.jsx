import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import Radio from "@material-ui/core/Radio";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";
import InputAdornment from "@material-ui/core/InputAdornment";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
    flexWrap: "wrap",
    padding: theme.spacing(4),
    "& > *": {
      margin: theme.spacing(0),
    },
  },
  form: {
    width: '100%'
  }
}));

const CreateGame = (props) => {
  const classes = useStyles();

  const [defaultValues, setDefaultValues] = React.useState({
    visibility: "public",
    mode: "classic",
    playersAllowed: "10",
  });

  const handleChange = (property) => (event) => {
    const targetValue = event.target.value;
    if (property === "mode") {
      switch (targetValue) {
        case "battleRoyale":
          setDefaultValues({ ...defaultValues, playersAllowed: 100, [property]: targetValue });
          break;
        case "classic":
          setDefaultValues({ ...defaultValues, playersAllowed: 10, [property]: targetValue });
          break;
        default:
          setDefaultValues({ ...defaultValues, [property]: targetValue });
          break;
      }
    } else {
      setDefaultValues({ ...defaultValues, [property]: targetValue });
    }
  };

  return (
    <div className={classes.root}>
      <form onSubmit={props.handleSubmit} className={classes.form}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <TextField
              required
              id="name"
              name="name"
              label="Game name"
              fullWidth
              autoComplete="gamename"
              variant="outlined"
            />
          </Grid>
          <Grid item xs={12} sm={4}>
            <FormControl component="fieldset">
              <FormLabel component="legend">Visibility</FormLabel>
              <RadioGroup
                aria-label="visibility"
                name="visibility"
                value={defaultValues.visibility}
                onChange={handleChange("visibility")}
              >
                <FormControlLabel
                  value="public"
                  control={<Radio />}
                  label="public"
                />
                <FormControlLabel
                  value="private"
                  control={<Radio />}
                  label="private"
                />
              </RadioGroup>
            </FormControl>
          </Grid>
          <Grid item xs={12} sm={4}>
            <FormControl component="fieldset">
              <FormLabel component="legend">Game mode</FormLabel>
              <RadioGroup
                aria-label="mode"
                name="mode"
                value={defaultValues.mode}
                onChange={handleChange("mode")}
              >
                <FormControlLabel
                  value="classic"
                  control={<Radio />}
                  label="Tron classic"
                />
                <FormControlLabel
                  value="battleRoyale"
                  control={<Radio />}
                  label="Battle Royale"
                />
              </RadioGroup>
            </FormControl>
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField
              required
              id="playersAllowed"
              name="playersAllowed"
              label="Max. Players"
              fullWidth
              autoComplete="maxplayers"
              variant="outlined"
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    /{defaultValues.playersAllowed}
                  </InputAdornment>
                ),
              }}
            />
          </Grid>
        </Grid>
        <Grid container spacing={3} justify="center">
          <Grid item xs={6}>
            <Button variant="contained" color="primary" type="submit" fullWidth>
              Create
            </Button>
          </Grid>
        </Grid>
      </form>
    </div>
  );
};

export default CreateGame;