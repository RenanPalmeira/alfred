import http from "axios"

class TemplateService {
    fetchTemplates() {
        return http.get("http://localhost:8080/template")
    }
}

export default new TemplateService()