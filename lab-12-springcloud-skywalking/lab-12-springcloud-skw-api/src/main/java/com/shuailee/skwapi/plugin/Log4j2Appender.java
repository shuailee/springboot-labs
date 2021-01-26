package com.shuailee.skwapi.plugin;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;

import java.io.Serializable;

/**
 * @package: com.shuailee.springbootdemo.plugin
 * @description:
 * @author: klein
 * @date: 2021-01-21 18:15
 **/
@Plugin(name = "SkywalkingAppender", category = "Core", elementType = "appender", printObject = true)
public class Log4j2Appender extends AbstractAppender {
    protected Log4j2Appender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @Override
    public void append(LogEvent logEvent) {
// 这里对log信息重新调用放到链路里
        if (Level.ERROR.equals(logEvent.getLevel())) {
            ActiveSpan.error(logEvent.getMessage().getFormattedMessage());
            ActiveSpan.error(logEvent.getThrown());
        } else if (Level.INFO.equals(logEvent.getLevel())) {
            ActiveSpan.info(logEvent.getMessage().getFormattedMessage());
        } else if (Level.DEBUG.equals(logEvent.getLevel())) {
            ActiveSpan.debug(logEvent.getMessage().getFormattedMessage());
        }
    }

    @PluginFactory
    public static Log4j2Appender createAppender(@PluginAttribute("name") String name,
                                                @PluginElement("Filter") final Filter filter,
                                                @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new Log4j2Appender(name, filter, layout, ignoreExceptions, null);
    }
}
