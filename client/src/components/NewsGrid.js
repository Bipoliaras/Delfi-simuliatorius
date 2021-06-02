import React, { useState, useEffect } from "react";
import GridFigure from "../components/GridFigure";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";

function NewsGrid() {
  const [data, setData] = useState({ items: [], error: null, isLoaded: false });

  useEffect(() => {
    function fetchData() {
      const endpoint =
        "http://localhost:9000/stories";
      fetch(endpoint)
        .then((res) => res.json())
        .then(
          (result) => {
            setData({
              isLoaded: true,
              items: result,
            });
          },
          (error) => {
            setData({
              isLoaded: true,
              error,
            });
          }
        );
    }
    fetchData();
  }, []);
  if (data.error) {
    return <div>Error: {data.error.message}</div>;
  } else if (!data.isLoaded) {
    return <div>Loading...</div>;
  } else {
    return (
      <Box m={2}>
        <Grid container spacing={2}>
          {data.items.map((article, i) => {
            return <GridFigure article={article} key={i} />;
          })}
        </Grid>
      </Box>
    );
  }
}
export default NewsGrid;
