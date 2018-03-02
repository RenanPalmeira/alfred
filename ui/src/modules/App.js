import React, { Component } from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListTemplate from "../containers/ListTemplate/ListTemplate"
import Template from "../containers/Template/Template";
import TemplateSend from "../containers/TemplateSend/TemplateSend";

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
                        <Route exact path="/" component={ListTemplate} />
                        <Route path="/template/create" component={Template} />
                        <Route path="/template/:templateName/send" component={TemplateSend} />
                        <Route path="/template/:templateName" component={Template} />
                        <Route component={ListTemplate} />
                    </Switch>
                </Router>
            </main>
        </div>
    );
  }
}
