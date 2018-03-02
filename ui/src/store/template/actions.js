import templateService from '../../services/TemplateService'

export function fetchTemplates() {
    return {
        type: 'SET_TEMPLATES',
        payload: templateService.fetchTemplates()
    }
}

export function fetchTemplate(templateName) {
    return {
        type: 'SET_TEMPLATE',
        payload: templateService.fetchTemplate(templateName)
    }
}

export function fetchUpdateTemplate(templateName) {
    return {
        type: 'SET_UPDATE_TEMPLATE',
        payload: templateService.updateTemplate(templateName)
    }
}

export function fetchNewTemplates(data) {
    return {
        type: 'SET_NEW_TEMPLATE',
        payload: templateService.saveTemplate(data)
    }
}

export function fetchSendTemplate(data) {
    return {
        type: 'SET_SEND_TEMPLATE',
        payload: templateService.sendTemplate(data)
    }
}

export function updateSendTemplateContext(value) {
    return {
        type: 'UPDATE_SEND_TEMPLATE_CONTEXT',
        payload: value
    }
}


export function updateTemplateName(value) {
    return {
        type: 'UPDATE_TEMPLATE_NAME',
        payload: value
    }
}

export function updateTemplateSubjectPart(value) {
    return {
        type: 'UPDATE_TEMPLATE_SUBJECT_PART',
        payload: value
    }
}

export function updateTemplateHtmlPart(value) {
    return {
        type: 'UPDATE_TEMPLATE_HTML_PART',
        payload: value
    }
}