import React, { useState, useEffect } from "react";
import Comments from "./Comments";
import { makeStyles, withStyles } from "@material-ui/core/styles";
import {
  Card,
  CardHeader,
  CardMedia,
  CardContent,
  CardActions,
  Button,
  Grid,
  Typography,
} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  "@keyframes hotNews": {
    "0%": { backgroundPosition: "0% 50%" },
    "50%": { backgroundPosition: " 100% 50%" },
    "100%": { backgroundPosition: "0% 50%" },
  },
  media: {
    position: "relative",
    paddingTop: "56.25%",
  },
  hot: {
    //animation: `$hotNews 3s ease infinite`,
    //background: `linear-gradient(270deg, ${theme.palette.secondary.dark}, ${theme.palette.secondary.light})`,
    //backgroundSize: "400% 400%",
    "& .MuiTag--ribbon": {
      position: "absolute",
      top: theme.spacing(2),
      left: theme.spacing(2),
      backgroundColor: theme.palette.secondary.main,
      color: "#ffffff !important",
      padding: "2px 8px",
      boxShadow: "0 2px 12px 2px rgba(0,0,0,0.5)",
      borderTopLeftRadius: 2,
      borderBottomLeftRadius: 2,
      "&:before, &:after": {
        position: "absolute",
        right: -16,
        content: '" "',
        borderLeft: `16px solid ${theme.palette.secondary.main}`,
        //background: `linear-gradient(270deg, ${theme.palette.secondary.dark}, ${theme.palette.secondary.light})`,
      },
      "&:before": {
        top: 0,
        borderBottom: "14px solid transparent",
      },
      "&:after": {
        bottom: 0,
        borderTop: "14px solid transparent",
      },
      "& .MuiTypography-root": {
        fontWeight: "bold",
      },
    },
  },
}));

// Add bold property to CardHeader title
const StyledCardHeader = withStyles({
  title: {
    fontWeight: "bold",
  },
  subheader: {
    color: "inherit",
  },
})(CardHeader);

function GridFigure(props) {
  const { title, interest, createdOn, imageSrc, comments } = props.article;
  const [animatedClass, setAnimatedClass] = useState("");
  const classes = useStyles();
  useEffect(() => {
    const setCss = (interest) => {
      if (interest !== "HOT") {
        return;
      }
      setAnimatedClass("hot");
    };
    setCss(interest);
  });

  return (
    <Grid item xs={12} sm={6} lg={4}>
      <Card variant="outlined" className={classes[animatedClass]}>
        <StyledCardHeader title={title} subheader={createdOn} />
        <CardMedia className={classes.media} image={imageSrc} title={imageSrc}>
          <div className={"MuiTag--ribbon"}>
            <Typography color={"inherit"} className={"MuiTypography-root"}>
              {animatedClass.toLocaleUpperCase()}
            </Typography>
          </div>
        </CardMedia>
        <CardContent>
          <Comments comments={comments}></Comments>
        </CardContent>
        <CardActions disableSpacing>
          <Button color={"primary"} size="medium">
            Share
          </Button>
        </CardActions>
      </Card>
    </Grid>
  );
}

export default GridFigure;
