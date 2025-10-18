package mg.md2i.gedi.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ObjectFactory implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ObjectFactory.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        logger.info("✅✅✅ ObjectFactory: Contexte Spring initialisé avec succès !");
        applicationContext = context;
    }

    public static <T> T getBean(Class<T> beanClass) {
        if (applicationContext == null) {
            logger.error("❌ ERREUR FATALE : Le contexte Spring est 'null' dans ObjectFactory.");
            throw new IllegalStateException("Le contexte Spring n'a pas été injecté. " +
                    "Vérifiez la configuration du component-scan et l'ordre des listeners dans web.xml.");
        }
        return applicationContext.getBean(beanClass);
    }

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ObjectFactory getInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}