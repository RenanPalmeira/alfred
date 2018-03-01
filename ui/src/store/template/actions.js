import templateService from '../../services/TemplateService'

export function fetchTemplates() {
    return {
        type: 'SET_TEMPLATES',
        payload: templateService.fetchTemplates()
    }
}