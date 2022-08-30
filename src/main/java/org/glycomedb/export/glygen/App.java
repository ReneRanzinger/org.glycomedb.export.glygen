package org.glycomedb.export.glygen;

import java.io.IOException;
import java.sql.SQLException;

import org.glycomedb.export.glygen.persist.DBInterface;
import org.glycomedb.export.glygen.util.GlycomeDBExporter;

public class App
{

    public static void main(String[] a_args)
            throws ClassNotFoundException, SQLException, IOException
    {
        DBInterface t_db = new DBInterface();
        GlycomeDBExporter t_exporter = new GlycomeDBExporter(t_db);
        // export structure mapping JSON
        t_exporter.exportStructureMapping();
        // export species information
        t_exporter.exportSpeciesAnnotation();
        // export CarbBank data
        t_exporter.exportCarbBank();
    }

}
