package org.processmining.plugins.workshop.helloworld;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.processmining.contexts.uitopia.annotations.UIExportPlugin;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.placebasedlpmdiscovery.model.Place;
import org.processmining.placebasedlpmdiscovery.model.serializable.PlaceSet;

@Plugin(
        name = "Export set of places into a file",
        returnLabels = {},
        returnTypes = {},
        parameterLabels = {"Set of places", "Filename"})
@UIExportPlugin(
        description = "ProM set of places (txt) files",
        extension = "txt")
public class PlaceSetExport {

    @PluginVariant(variantLabel = "Export set of places into a file", requiredParameterLabels = {0, 1})
    public void export(PluginContext context, PlaceSet placeSet, File file) {
        try {

        	FileWriter f = new FileWriter(file.getName());
        	PrintWriter out = new PrintWriter(f);
        	
            for (Place place : placeSet.getElements()) {
            	out.println(place.getShortString());
            }
            f.close();
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
