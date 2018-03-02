import React from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {UnControlled as CodeMirror} from 'react-codemirror2'
import {
    fetchNewTemplates,
    fetchUpdateTemplate,
    fetchTemplate,
    updateTemplateHtmlPart,
    updateTemplateName,
    updateTemplateSubjectPart
} from "../../store/template/actions";
import '../../../node_modules/codemirror/lib/codemirror.css'
import '../../../node_modules/@atlaskit/reduced-ui-pack/dist/bundle.css'
import '../../../node_modules/codemirror/mode/jinja2/jinja2'
import '../../../node_modules/codemirror/mode/htmlmixed/htmlmixed'
import '../../../node_modules/codemirror/mode/htmlembedded/htmlembedded'
import '../../../node_modules/codemirror/mode/xml/xml'

class Template extends React.Component {
    static contextTypes = {
        router: PropTypes.shape({
            history: PropTypes.shape({
                push: PropTypes.func.isRequired,
                replace: PropTypes.func.isRequired
            }).isRequired,
            staticContext: PropTypes.object
        }).isRequired
    }

    constructor() {
        super();
        this.templateSubmit = this.templateSubmit.bind(this)
        this.templateSubmitWithRedirect = this.templateSubmitWithRedirect.bind(this)
        this.changeTemplateName = this.changeTemplateName.bind(this)
        this.changeSubject = this.changeSubject.bind(this)
        this.changeHtmlPart = this.changeHtmlPart.bind(this)
    }

    componentDidMount() {
        const { dispatch } = this.props
        const { templateName } = this.context.router.route.match.params

        if (templateName) {
            dispatch(fetchTemplate(templateName))
        }
    }

    changeTemplateName (e) {
        e.preventDefault()
        const { dispatch } = this.props
        dispatch(updateTemplateName(e.target.value))
    }

    changeSubject (e) {
        e.preventDefault()
        const { dispatch } = this.props
        dispatch(updateTemplateSubjectPart(e.target.value))
    }

    changeHtmlPart (editor, data, value) {
        const { dispatch } = this.props
        dispatch(updateTemplateHtmlPart(value))
    }

    templateSubmit (e) {
        e.preventDefault()
        const { dispatch, editorTemplate } = this.props

        const emptyFieldsEditorTemplate = Object.keys(editorTemplate)
            .filter(field => editorTemplate[field]===null || editorTemplate[field]==="")
            .length

        if (emptyFieldsEditorTemplate <= 0) {
            const { templateName } = this.context.router.route.match.params

            if (templateName) {
                dispatch(fetchUpdateTemplate(editorTemplate))
            }
            else {
                dispatch(fetchNewTemplates(editorTemplate))
            }
        }
    }

    templateSubmitWithRedirect (e) {
        e.preventDefault()

        const { dispatch, editorTemplate } = this.props

        const emptyFieldsEditorTemplate = Object.keys(editorTemplate)
            .filter(field => editorTemplate[field]===null || editorTemplate[field]==="")
            .length

        if (emptyFieldsEditorTemplate <= 0) {
            const { templateName } = this.context.router.route.match.params

            if (templateName) {
                dispatch(fetchUpdateTemplate(editorTemplate))
            }
            else {
                dispatch(fetchNewTemplates(editorTemplate))
            }

            this.context.router.history.push(`/template/${editorTemplate.template_name}/send`)
        }
    }

    render() {

        const { editorTemplate } = this.props

        return (
            <div>
                <form method="POST" onSubmit={ this.templateSubmit }>
                    { this.context.router.route.match.params.templateName && <h3>Edit <i>{editorTemplate.template_name}</i></h3> }
                    { !this.context.router.route.match.params.templateName && <h3>New AWS Template</h3> }

                    { !this.context.router.route.match.params.templateName &&
                        <div className="ak-field-group">
                            <label htmlFor="TemplateName">Name</label>
                            <input
                                type="text"
                                className="ak-field-text ak-field__size-medium"
                                id="TemplateName"
                                name="TemplateName"
                                placeholder="eg. 'aPrettyTemplate', 'LastTemplateOfMyLife', 'MaybeIMixedTheTypeTemplate'"
                                value={ editorTemplate.template_name }
                                onChange={ this.changeTemplateName }
                                required=""
                            />
                        </div>}

                    <div className="ak-field-group">
                        <label htmlFor="SubjectPart">Subject</label>
                        <input
                            type="text"
                            className="ak-field-text ak-field__size-medium"
                            id="SubjectPart"
                            name="SubjectPart"
                            placeholder="eg. 'Welcome pretty client', 'Bye-bye bed client'"
                            value={ editorTemplate.subject_part }
                            onChange={ this.changeSubject }
                            required=""
                        />
                    </div>
                    <div className="ak-field-group">
                        <label>Template</label>
                        <CodeMirror
                            value={ editorTemplate.default_html_part }
                            options={{
                                mode: 'xml',
                                lineNumbers: true,
                                lineWrapping: true
                            }}
                            onChange={ this.changeHtmlPart }
                        />
                    </div>
                    <div className="ak-field-group">
                        <div className="btn-group" role="group">
                            <button type="submit" className="btn btn-success">
                                Save AWS Template
                            </button>
                            <button type="button" className="btn btn-warning" onClick={ this.templateSubmitWithRedirect }>
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

const mapStateToProps = (state) => {
    return {
        editorTemplate: state.editorTemplate
    }
}

export default connect(mapStateToProps)(Template)