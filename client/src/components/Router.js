import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import NewsGrid from '../components/NewsGrid';
import NotFound from './NotFound';

const Router = () => (
  <BrowserRouter>
    <Switch>
      <Route exact path='/' component={NewsGrid} />
      {/* <Route exact path='/headline/:headlineId' component={App} /> */}
      <Route component={NotFound} />
    </Switch>
  </BrowserRouter>
);

export default Router;
