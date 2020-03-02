import React from 'react';
import styled from 'styled-components';

const StyledFigure = styled.figure`
  margin: 2rem;
  padding: 2rem;
  border: 1px solid ${(props) => props.theme.lightgray};
  background: ${(props) => props.theme.offwhite};
  box-shadow: 0 0 0 5px rgba(0, 0, 0, 0.03);
  position: relative;
`;

const Comment = styled.div`
  border-bottom: 1px solid ${(props) => props.theme.lightgray};
  padding: 0.5rem 0;
  strong {
    color: ${(props) => props.theme.blue};
    margin-right: 5px;
  }
`;

const Headline = styled.h1`
  font-size: 4.6rem;
  font-weight: 50;
  line-height: 5rem;
  letter-spacing: 1px;
  padding: 0 0 40px;
  border-bottom: double #555;
`;

const StyledDate = styled.span`
  background: ${(props) => props.theme.offwhite};
  font-family: 'Georgia', serif;
  font-style: italic;
  font-size: 1.8rem;
  line-height: 2.2rem;
  margin: 0 0 20px 18px;
  padding: 10px 12px 8px;
  position: relative;
  bottom: 45px;
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
    const { title, createdOn, imageSrc, comments } = this.props.data;
    return (
      <StyledFigure>
        <Headline>{title}</Headline>
        <StyledDate>{createdOn}</StyledDate>
        <div>
          <img src={imageSrc} alt={' '}></img>
          {comments.map(this.renderComment)}
        </div>
      </StyledFigure>
    );
  }
}

export default GridFigure;
