package org.glycomedb.export.glygen.util;

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
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GlycomeDBExporter
{
    private DBInterface m_db = null;

    public GlycomeDBExporter(DBInterface a_db)
    {
        this.m_db = a_db;
    }

    public void exportStructureMapping()
            throws SQLException, StreamWriteException, DatabindException, IOException
    {
        System.out.println("Export structure mapping (GlycomeDB -> GlyTouCan)");
        Integer t_counterNull = 0;
        Integer t_counterHash = 0;
        List<StructureMapping> t_mapping = this.m_db.getGlyTouCanMapping();
        for (StructureMapping t_structureMapping : t_mapping)
        {
            if (t_structureMapping.getGlyTouCanId() == null)
            {
                t_counterNull++;
            }
            else if (t_structureMapping.getGlyTouCanId().length() != 8)
            {
                t_counterHash++;
                t_structureMapping.setGlyTouCanId(null);
            }
        }
        ObjectMapper t_mapper = new ObjectMapper();
        t_mapper.setSerializationInclusion(Include.NON_NULL);
        String t_fileNamePath = "." + File.separator + "export" + File.separator + "mapping.json";
        t_mapper.writerWithDefaultPrettyPrinter().writeValue(new File(t_fileNamePath), t_mapping);
        System.out.println(
                "\tNumber of structures without GlyTouCan ID: " + t_counterNull.toString());
        System.out.println(
                "\tNumber of structures with GlyTouCan Hash : " + t_counterHash.toString() + "\n");
    }

    public void exportSpeciesAnnotation() throws IOException, SQLException
    {
        System.out.println("Export species annotation");
        Integer t_counterNull = 0;
        Integer t_counterHash = 0;
        List<TaxonMapping> t_mappingTaxon = this.m_db.getTaxonMapping();
        String t_fileNamePath = "." + File.separator + "export" + File.separator
                + "taxon_mapping.csv";
        TaxonMappingFile t_taxonMappingFile = new TaxonMappingFile(t_fileNamePath);
        for (TaxonMapping t_taxonMapping : t_mappingTaxon)
        {
            if (t_taxonMapping.getGlyTouCanId() == null)
            {
                t_counterNull++;
            }
            else if (t_taxonMapping.getGlyTouCanId().length() != 8)
            {
                t_counterHash++;
                t_taxonMapping.setGlyTouCanId(null);
            }
            else
            {
                t_taxonMappingFile.write(t_taxonMapping);
            }
        }
        t_taxonMappingFile.closeFile();
        System.out.println(
                "\tNumber of structures without GlyTouCan ID: " + t_counterNull.toString());
        System.out.println(
                "\tNumber of structures with GlyTouCan Hash : " + t_counterHash.toString() + "\n");
    }

    public void exportCarbBank() throws SQLException, IOException
    {
        System.out.println("Export CarbBank data");
        Integer t_counterNull = 0;
        Integer t_counterHash = 0;
        Integer t_counterNoGlycomeDB = 0;
        ResultSet t_set = this.m_db.getCarBankData();
        String t_fileNamePath = "." + File.separator + "export" + File.separator + "carbbank.csv";
        CarbBankFile t_carbbankFile = new CarbBankFile(t_fileNamePath);
        while (t_set.next())
        {
            String t_glycomeDB = t_set.getString("structure_id");
            String t_glyTouCan = t_set.getString("glytoucan_id");
            if (t_glycomeDB == null)
            {
                t_counterNoGlycomeDB++;
            }
            else if (t_glyTouCan == null)
            {
                t_counterNull++;
            }
            else if (t_glyTouCan.length() != 8)
            {
                t_counterHash++;
                t_glyTouCan = null;
            }
            else
            {
                t_carbbankFile.write(t_set.getString("cc"), t_set.getString("ag"),
                        t_set.getString("am"), t_set.getString("an"), t_set.getString("au"),
                        t_set.getString("ba"), t_set.getString("bs"), t_set.getString("ct"),
                        t_set.getString("da"), t_set.getString("db"), t_set.getString("mt"),
                        t_set.getString("nc"), t_set.getString("nt"), t_set.getString("pa"),
                        t_set.getString("pm"), t_set.getString("sb"), t_set.getString("sc"),
                        t_set.getString("si"), t_set.getString("st"), t_set.getString("ti"),
                        t_set.getString("tn"), t_glycomeDB, t_glyTouCan);
            }
        }
        t_carbbankFile.closeFile();
        System.out.println("\tRecord without GlycomeDB ID: " + t_counterNoGlycomeDB.toString());
        System.out.println(
                "\tNumber of structures without GlyTouCan ID: " + t_counterNull.toString());
        System.out.println(
                "\tNumber of structures with GlyTouCan Hash : " + t_counterHash.toString() + "\n");
    }

}
