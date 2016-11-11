package org.jasig.cas.client.session.shiro;



import org.apache.shiro.web.filter.PathMatchingFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Abstracts out the ability to configure the filters from the initial properties provided.
 * 
 * @author zizhiguo
 *
 */
public abstract class AbstractShiroConfigurationFilter extends PathMatchingFilter {
    private static Logger logger = LoggerFactory.getLogger(AbstractShiroConfigurationFilter.class);

    private boolean ignoreInitConfiguration = false;

    /**
     * Retrieves the property from the FilterConfig.  First it checks the FilterConfig's initParameters to see if it
     * has a value.
     * If it does, it returns that, otherwise it retrieves the ServletContext's initParameters and returns that value if any.
     * <p>
     * Finally, it will check JNDI if all other methods fail.  All the JNDI properties should be stored under either java:comp/env/cas/SHORTFILTERNAME/{propertyName}
     * or java:comp/env/cas/{propertyName}
     * <p>
     * Essentially the documented order is:
     * <ol>
     *     <li>FilterConfig.getInitParameter</li>
     *     <li>ServletContext.getInitParameter</li>
     *     <li>java:comp/env/cas/SHORTFILTERNAME/{propertyName}</li>
     *     <li>java:comp/env/cas/{propertyName}</li>
     *     <li>Default Value</li>
     * </ol>
     *
     *
     * @param filterConfig the Filter Configuration.
     * @param propertyName the property to retrieve.
     * @param defaultValue the default value if the property is not found.
     * @return the property value, following the above conventions.  It will always return the more specific value (i.e.
     *  filter vs. context).
     */
    protected final String getPropertyFromInitParams( final String propertyName, final String defaultValue)  {
        final String value = getInitParam(propertyName);

        if (CommonUtils.isNotBlank(value)) {
            logger.info("Property [" + propertyName + "] loaded from FilterConfig.getInitParameter with value [" + value + "]");
            return value;
        }
        
        logger.info("Property [" + propertyName + "] not found.  Using default value [" + defaultValue + "]");
        return defaultValue;
    }
    
    protected final boolean parseBoolean(final String value) {
    	return ((value != null) && value.equalsIgnoreCase("true"));
    }
    
    public final void setIgnoreInitConfiguration(boolean ignoreInitConfiguration) {
        this.ignoreInitConfiguration = ignoreInitConfiguration;
    }

    protected final boolean isIgnoreInitConfiguration() {
        return this.ignoreInitConfiguration;
    }
}
