import React from "react";
import GridFigure from "../components/GridFigure";
import styled from "styled-components";

const Grid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(950px, 1fr));
  margin: 0 auto;
  @media (max-width: 900px) {
    display: grid;
    grid-template-columns: 1fr;
    margin: 0 auto;
  }
`;

class NewsGrid extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      items: []
    };
  }

  componentDidMount() {
    const corsUrl = "https://cors-anywhere.herokuapp.com/";
    const endpointUrl = "http://31.220.53.74:8080/stories/";
    fetch(corsUrl + endpointUrl)
      .then(res => res.json())
      .then(
        result => {
          this.setState({
            isLoaded: true,
            items: result
          });
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        error => {
          this.setState({
            isLoaded: true,
            error
          });
        }
      );
  }
  render() {
    const { error, isLoaded, items } = this.state;
    if (error) {
      return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
      return <div>Loading...</div>;
    } else {
      return (
        <Grid>
          {items.map((article, i) => {
            return <GridFigure data={article} key={i} />;
          })}
        </Grid>
      );
    }
  }
}
export default NewsGrid;
