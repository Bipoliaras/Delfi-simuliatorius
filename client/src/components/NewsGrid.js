import React, { useState, useEffect } from "react";
import GridFigure from "../components/GridFigure";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";

function NewsGrid() {
  const [data, setData] = useState({ items: [], error: null, isLoaded: false });

  useEffect(() => {
    function fetchData() {
      const endpointUrl = "http://31.220.53.74:8080/stories/";
      fetch(endpointUrl)
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
            article.interest === "HOT"
              ? (article.background =
                  "linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)")
              : (article.background = "");
            return <GridFigure article={article} key={i} />;
          })}
        </Grid>
      </Box>
    );
  }
}
export default NewsGrid;
