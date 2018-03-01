import React, { Component } from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import Template from "../containers/Template/Template"
import CreateTemplate from "../containers/CreateTemplate/CreateTemplate";

export default class App extends Component {
  render() {
    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-dark bg-aws">
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item">
                            <a className="nav-link" href="/"><b>Dashboard</b></a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/">AWS Templates</a>
                        </li>
                    </ul>
                </div>
            </nav>
            <main role="main" className="container">
                <Router>
                    <Switch>
                        <Route exact path="/" component={Template} />
                        <Route path="/template/create" component={CreateTemplate} />
                        <Route component={Template} />
                    </Switch>
                </Router>
            </main>
        </div>
    );
  }
}
