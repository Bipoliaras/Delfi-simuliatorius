import React from "react";
import { makeStyles, createStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import CardMedia from "@material-ui/core/CardMedia";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import CardContent from "@material-ui/core/CardContent";
import { CardActions, Box } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  root: {
    background: (props) => props.background,
    color: (props) =>
      props.interest === "HOT" ? "#fff" : theme.palette.text.primary,
  },
  comment: {
    fontWeight: "bold",
    color: theme.palette.primary.main,
    "& span": {
      fontWeight: "normal",
      color: (props) =>
        props.interest === "HOT" ? "#fff" : theme.palette.text.primary,
    },
  },
  media: {
    height: 0,
    paddingTop: "56.25%", // 16:9
  },
}));

function GridFigure(props) {
  const { title, createdOn, imageSrc, comments } = props.article;
  const classes = useStyles(props.article);
  function renderComment(comment) {
    const { id, username, text } = comment;
    return (
      <Box key={id}>
        <Typography className={classes.comment} component="span">
          {username}:&nbsp;
          <span dangerouslySetInnerHTML={{ __html: text }} />
        </Typography>
      </Box>
    );
  }
  return (
    <Grid item xs={12} sm={6} lg={4}>
      <Card className={classes.root}>
        <CardHeader title={title} subheader={createdOn} />
        <CardMedia
          className={classes.media}
          image={imageSrc}
          title={imageSrc}
        />
        <CardContent children={comments.map(renderComment)}></CardContent>
        <CardActions disableSpacing>
          <Button size="small">Share</Button>
        </CardActions>
      </Card>
    </Grid>
  );
}

export default GridFigure;
