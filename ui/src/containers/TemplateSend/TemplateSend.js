import React from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {Controlled as CodeMirror, UnControlled as CodeMirrorEditor} from 'react-codemirror2'
import {
    fetchTemplate,
    fetchSendTemplate,
    updateSendTemplateContext
} from "../../store/template/actions";
import '../../../node_modules/codemirror/lib/codemirror.css'
import '../../../node_modules/@atlaskit/reduced-ui-pack/dist/bundle.css'
import '../../../node_modules/codemirror/mode/jinja2/jinja2'
import '../../../node_modules/codemirror/mode/htmlmixed/htmlmixed'
import '../../../node_modules/codemirror/mode/htmlembedded/htmlembedded'
import '../../../node_modules/codemirror/mode/javascript/javascript'

class TemplateSend extends React.Component {
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
        this.submitSendTemplate = this.submitSendTemplate.bind(this)
        this.changeSendTemplateContext = this.changeSendTemplateContext.bind(this)
    }

    componentDidMount() {
        const { dispatch } = this.props
        const { templateName } = this.context.router.route.match.params

        if (templateName) {
            dispatch(fetchTemplate(templateName))
        }
    }

    changeSendTemplateContext (editor, data, value) {
        const { dispatch } = this.props
        dispatch(updateSendTemplateContext(value))
    }

    submitSendTemplate (e) {
        e.preventDefault()

        const { dispatch, editorTemplate, sendTemplate } = this.props

        dispatch(fetchSendTemplate({
            template_name: editorTemplate.template_name,
            context: JSON.parse(sendTemplate.context)
        }))

        this.context.router.history.push(`/`)
    }

    render() {

        const { editorTemplate, sendTemplate } = this.props

        return (
            <div>
                <h3>Send <i>{editorTemplate.template_name}</i></h3>
                <p>
                    Subject: {editorTemplate.subject_part}
                </p>
                <CodeMirror
                    value={ editorTemplate.html_part }
                    options={{
                        mode: 'xml',
                        lineNumbers: false,
                        lineWrapping: true
                    }}
                />
                <hr/>

                <h6>Context (here the variables to send this email):</h6>

                <CodeMirrorEditor
                    value={ sendTemplate.defaultContext }
                    options={{
                        mode: 'javascript',
                        lineNumbers: true,
                        lineWrapping: true
                    }}
                    onChange={ this.changeSendTemplateContext }
                />


                <div className="btn-group" role="group">
                    <button type="button" className="btn btn-success" onClick={ this.submitSendTemplate }>
                        Send a e-mail test
                    </button>
                    <button type="reset" className="btn btn-danger" onClick={ _ => this.context.router.history.push('/')}>
                        Cancel
                    </button>
                </div>

            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        editorTemplate: state.editorTemplate,
        sendTemplate: state.sendTemplate
    }
}

export default connect(mapStateToProps)(TemplateSend)