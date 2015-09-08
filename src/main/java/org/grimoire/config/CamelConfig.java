package org.grimoire.config;

import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.component.bean.BeanProcessor;
import org.apache.camel.impl.ProcessorEndpoint;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.grimoire.ingest.ExpansionPersister;
import org.grimoire.ingest.support.CamelEndpoint;
import org.grimoire.repo.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;

@Configuration
public class CamelConfig {

    @Bean(name = "mtg-persister")
    @Autowired
    public ExpansionPersister mtgExpansionPersister(GameRepo gameRepo) {
        return new ExpansionPersister(gameRepo, GameRepo.Specs.byName("Magic: the Gathering"));
    }

    @Bean
    public CamelContextConfiguration camelContextConfiguration() {
        return new CamelContextConfig();
    }

    private static class CamelContextConfig implements CamelContextConfiguration {

        @Override
        public void beforeApplicationStart(CamelContext camelContext) {
            ApplicationContext applicationContext = camelContext.adapt(SpringCamelContext.class)
                    .getApplicationContext();
            for (Map.Entry<String, Object> entry : applicationContext
                    .getBeansWithAnnotation(CamelEndpoint.class).entrySet()) {
                addEndpoint(camelContext, entry);
            }
        }

        private static void addEndpoint(CamelContext context, Map.Entry<String, Object> entry) {
            Object bean = entry.getValue();
            CamelEndpoint annotation = AnnotationUtils.findAnnotation(bean.getClass(),
                    CamelEndpoint.class);
            AnnotationAttributes atts = AnnotationAttributes
                    .fromMap(AnnotationUtils.getAnnotationAttributes(annotation));

            String uri = atts.getString("uri");
            if (uri == null || uri.isEmpty()) uri = atts.getString("value");
            if (uri == null || uri.isEmpty()) uri = entry.getKey();

            uri = "spring:" + uri;

            try {
                context.addEndpoint(uri,
                        new ProcessorEndpoint(uri, context, new BeanProcessor(bean, context)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
