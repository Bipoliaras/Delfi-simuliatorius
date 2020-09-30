import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";

//const styledBy = (property, mapping) => (props) => mapping[props[property]];
const commentStyles = makeStyles(({ palette }) => ({
  comment: {
    fontWeight: "bold",
    color: palette.primary.main,
  },
  comment_span: {
    fontWeight: "normal",
    color: palette.text.primary,
  },
}));

const StyledComment = (props) => {
  const { comment } = props;

  const classes = commentStyles(props);
  return (
    <div>
      <Typography className={classes.comment} component="span">
        {comment.username}:&nbsp;
        <span
          className={classes.comment_span}
          dangerouslySetInnerHTML={{ __html: comment.text }}
        />
      </Typography>
    </div>
  );
};
function Comments({ comments }) {
  return (
    <>
      {comments.map((comment) => (
        <StyledComment key={comment.id} comment={comment}></StyledComment>
      ))}
    </>
  );
}

export default Comments;
