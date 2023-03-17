package org.processmining.plugins.workshop.helloworld;

import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;

public class MyNewHello {
        @Plugin(
                name = "My New Hello World Plugin", 
                parameterLabels = {}, 
                returnLabels = { "Hello world string" }, 
                returnTypes = { String.class }, 
                userAccessible = true, 
                help = "Produces the string: 'Hello world'"
        )
        @UITopiaVariant(
                affiliation = "RWTH", 
                author = "Narek Gevorgyan", 
                email = "narek.gevorgyan@rwth-aachen.de"
        )
        public static String helloWorld(PluginContext context) {
                return "Hello World";
        }
}
