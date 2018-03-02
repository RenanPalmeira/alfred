import React from "react";
import {UnControlled as CodeMirror} from 'react-codemirror2'
import '../../../node_modules/codemirror/lib/codemirror.css'
import '../../../node_modules/@atlaskit/reduced-ui-pack/dist/bundle.css'
import '../../../node_modules/codemirror/mode/jinja2/jinja2'
import PropTypes from "prop-types";

export default class CreateTemplate extends React.Component {
    static contextTypes = {
        router: PropTypes.shape({
            history: PropTypes.shape({
                push: PropTypes.func.isRequired,
                replace: PropTypes.func.isRequired
            }).isRequired,
            staticContext: PropTypes.object
        }).isRequired
    }

    render() {

        return (
            <div>
                <form>
                    <h3>New AWS Template</h3>
                    <div className="ak-field-group">
                        <label htmlFor="TemplateName">Name</label>
                        <input
                            type="text"
                            className="ak-field-text ak-field__size-medium"
                            id="TemplateName"
                            name="TemplateName"
                            placeholder="eg. 'aPrettyTemplate', 'LastTemplateOfMyLife', 'MaybeIMixedTheTypeTemplate'"
                        />
                    </div>
                    <div className="ak-field-group">
                        <label htmlFor="SubjectPart">Subject</label>
                        <input
                            type="text"
                            className="ak-field-text ak-field__size-medium"
                            id="SubjectPart"
                            name="SubjectPart"
                            placeholder="eg. 'Welcome pretty client', 'Bye-bye bed client'"
                        />
                    </div>
                    <div className="ak-field-group">
                        <label>Template</label>
                        <CodeMirror
                            value='<h1>Here your pretty HTML to this {{template}}</h1>'
                            options={{
                                mode: 'jinja2',
                                lineNumbers: true
                            }}
                            onChange={(editor, data, value) => {
                            }}
                        />
                    </div>
                    <div className="ak-field-group">
                        <div className="btn-group" role="group">
                            <button type="submit" className="btn btn-success">
                                Save AWS Template
                            </button>
                            <button type="submit" className="btn btn-warning">
                                Save and Send a e-mail test
                            </button>
                            <button type="reset" className="btn btn-danger" onClick={ _ => this.context.router.history.push('/')}>
                                Cancel
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}