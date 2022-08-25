package org.glycomedb.export.glygen;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.glycomedb.export.glygen.csv.CarbBankFile;
import org.glycomedb.export.glygen.csv.TaxonMappingFile;
import org.glycomedb.export.glygen.persist.DBInterface;
import org.glycomedb.export.glygen.persist.om.StructureMapping;
import org.glycomedb.export.glygen.persist.om.TaxonMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App
{

    public static void main(String[] a_args)
            throws ClassNotFoundException, SQLException, IOException
    {
        DBInterface t_db = new DBInterface();

        // export structure mapping JSON
        List<StructureMapping> t_mapping = t_db.getGlyTouCanMapping();
        ObjectMapper t_mapper = new ObjectMapper();
        t_mapper.setSerializationInclusion(Include.NON_NULL);
        String t_fileNamePath = "." + File.separator + "export" + File.separator + "mapping.json";
        t_mapper.writerWithDefaultPrettyPrinter().writeValue(new File(t_fileNamePath), t_mapping);

        // export species information
        List<TaxonMapping> t_mappingTaxon = t_db.getTaxonMapping();
        t_fileNamePath = "." + File.separator + "export" + File.separator + "taxon_mapping.csv";
        TaxonMappingFile t_taxonMappingFile = new TaxonMappingFile(t_fileNamePath);
        for (TaxonMapping t_taxonMapping : t_mappingTaxon)
        {
            t_taxonMappingFile.write(t_taxonMapping);
        }
        t_taxonMappingFile.closeFile();

        // export CarbBank data
        ResultSet t_set = t_db.getCarBankData();
        t_fileNamePath = "." + File.separator + "export" + File.separator + "carbbank.csv";
        CarbBankFile t_carbbankFile = new CarbBankFile(t_fileNamePath);
        while (t_set.next())
        {
            t_carbbankFile.write(t_set.getString("cc"), t_set.getString("ag"),
                    t_set.getString("am"), t_set.getString("an"), t_set.getString("au"),
                    t_set.getString("ba"), t_set.getString("bs"), t_set.getString("ct"),
                    t_set.getString("da"), t_set.getString("db"), t_set.getString("mt"),
                    t_set.getString("nc"), t_set.getString("nt"), t_set.getString("pa"),
                    t_set.getString("pm"), t_set.getString("sb"), t_set.getString("sc"),
                    t_set.getString("si"), t_set.getString("st"), t_set.getString("ti"),
                    t_set.getString("tn"));
        }
        t_carbbankFile.closeFile();
    }

}
