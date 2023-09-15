package com.estebangarviso.driverlinkpro.domain.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class MailContentBuilderService {

        @Autowired
        private TemplateEngine templateEngine;

        private String template;
        private Map<String, Object> templateValues;

        public String build() {
            Context context = new Context();
            context.setVariables(templateValues);
            return templateEngine.process(template, context);
        }

        public MailContentBuilderService setTemplate(String template) {
            this.template = template;
            return this;
        }

        public MailContentBuilderService addVariable(String key, Object value) {
            this.templateValues.put(key, value);
            return this;
        }
}
