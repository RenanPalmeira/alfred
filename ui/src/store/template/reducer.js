const initialState = {
    templates: [],
    editorTemplate: {
        template_name: '',
        subject_part: '',
        html_part: '',
        default_html_part: "<h1>Here your pretty HTML to this {{template}}</h1>",
    },
    sendTemplate: {
        defaultContext: "{\"key\": \"value\"}",
        context: "{\"key\": \"value\"}"
    }
}

export default function reducer(state = initialState, action) {
    switch (action.type) {

        case 'UPDATE_TEMPLATE_NAME':
            return {
                ...state,
                editorTemplate: {
                    ...state.editorTemplate,
                    template_name: action.payload
                }
            }

        case 'UPDATE_TEMPLATE_SUBJECT_PART':
            return {
                ...state,
                editorTemplate: {
                    ...state.editorTemplate,
                    subject_part: action.payload
                }
            }

        case 'UPDATE_TEMPLATE_HTML_PART':
            return {
                ...state,
                editorTemplate: {
                    ...state.editorTemplate,
                    html_part: action.payload
                }
            }

        case 'UPDATE_SEND_TEMPLATE_CONTEXT':
            return {
                ...state,
                sendTemplate: {
                    context: action.payload
                }
            }

        case 'SET_UPDATE_TEMPLATE':
        case 'SET_NEW_TEMPLATE_FULFILLED':
            console.log('go')
            return state

        case 'SET_TEMPLATE_FULFILLED':
            const { template_name, subject_part, html_part } = action.payload.data

            if (state.editorTemplate.html_part==='') {
                return {
                    ...state,
                    editorTemplate: {
                        template_name: template_name,
                        subject_part: subject_part,
                        html_part: html_part,
                        default_html_part: html_part,
                    }
                }
            }

            return {
                ...state,
                editorTemplate: {
                    template_name: template_name,
                    subject_part: subject_part,
                    html_part: html_part
                }
            }

        case 'SET_TEMPLATES_FULFILLED':
            return {
                ...state,
                templates: action.payload.data
            }

        default:
            return state;
    }
}