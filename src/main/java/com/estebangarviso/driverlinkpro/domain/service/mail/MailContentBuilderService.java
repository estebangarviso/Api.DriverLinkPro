package com.estebangarviso.driverlinkpro.domain.service.mail;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class MailContentBuilderService {

        private final TemplateEngine templateEngine;
        private String template;
        private Map<String, Object> templateValues;

        public MailContentBuilderService(TemplateEngine templateEngine) {
            this.templateEngine = templateEngine;
        }

        public String build() {
            Context context = new Context();
            context.setVariables(templateValues);
            return templateEngine.process(template, context);
        }

        public MailContentBuilderService setTemplate(String template) {
            this.template = template;
            return this;
        }

        public MailContentBuilderService addVariables(Map<String, Object> values) {
            this.templateValues = values;
            return this;
        }
}
