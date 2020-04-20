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
    padding: "16px",
    "& > *": {
      margin: theme.spacing(0),
    },
  },
  form: {
    width: '100%'
  }
}));

const CreateGame = () => {
  const classes = useStyles();
  const [values, setValues] = React.useState({
    visibility: "public",
    mode: "classic",
    maxPlayerLimit: "10",
  });

  const handleChange = (prop) => (event) => {
    const targetValue = event.target.value;
    if (prop === "mode") {
      switch (targetValue) {
        case "battleroyal":
          setValues({ ...values, maxPlayerLimit: 100, [prop]: targetValue });
          break;
        case "classic":
          setValues({ ...values, maxPlayerLimit: 10, [prop]: targetValue });
          break;
        default:
          setValues({ ...values, [prop]: targetValue });
          break;
      }
    } else {
      setValues({ ...values, [prop]: targetValue });
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // form data
    for (const [key, value] of new FormData(event.target).entries()) {
      console.log("[" + key + "]" + value);
    }
  };

  return (
    <div className={classes.root}>
      <form onSubmit={handleSubmit} className={classes.form}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <TextField
              required
              id="gamename"
              name="gamename"
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
                value={values.visibility}
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
                value={values.mode}
                onChange={handleChange("mode")}
              >
                <FormControlLabel
                  value="classic"
                  control={<Radio />}
                  label="Tron classic"
                />
                <FormControlLabel
                  value="battleroyal"
                  control={<Radio />}
                  label="Battle Royal"
                />
              </RadioGroup>
            </FormControl>
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField
              required
              id="maxplayers"
              name="maxplayers"
              label="Max. Players"
              fullWidth
              autoComplete="maxplayers"
              variant="outlined"
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    /{values.maxPlayerLimit}
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
