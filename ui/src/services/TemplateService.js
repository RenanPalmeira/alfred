import http from "axios"

class TemplateService {
    fetchTemplates() {
        return http.get("http://localhost:8080/template")
    }

    fetchTemplate(templateName) {
        return http.get(`http://localhost:8080/template/${templateName}`)
    }

    saveTemplate(data) {
        return http.post("http://localhost:8080/template", data)
    }

    updateTemplate(data) {
        return http.put(`http://localhost:8080/template/${data.template_name}`, data)
    }

    sendTemplate(data) {
        return http.post(`http://localhost:8080/template/${data.template_name}/send`, {
            context: data.context
        })
    }
}

export default new TemplateService()