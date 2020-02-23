import React from "react";

import styled from "styled-components";
import { Headline, StyledDate } from "./GridFigureStyles";
const StyledFigure = styled.figure`
  margin: 2rem;
  padding: 2rem;
  border: 1px solid ${props => props.theme.lightgray};
  background: offwhite;
  box-shadow: 0 0 0 5px rgba(0, 0, 0, 0.03);
  position: relative;
`;

const Comment = styled.div`
  border-bottom: 1px solid ${props => props.theme.lightgray};
  padding: 0.5rem 0;
  strong {
    color: ${props => props.theme.blue};
    margin-right: 5px;
  }
`;

class GridFigure extends React.Component {
  constructor() {
    super();
    this.state = {};
  }
  renderComment(comment, i) {
    const { id, username, text } = comment;
    return (
      <Comment key={id}>
        <p>
          <strong>{username}</strong>
          <span dangerouslySetInnerHTML={{ __html: text }} />
        </p>
      </Comment>
    );
  }
  render() {
    const { title, imageSrc, comments } = this.props.data;
    return (
      <StyledFigure>
        <Headline>{title}</Headline>
        <StyledDate>2019-01-19</StyledDate>
        <div>
          <img src={imageSrc} alg={" "}></img>
          {comments.map(this.renderComment)}
        </div>
      </StyledFigure>
    );
  }
}

export default GridFigure;
