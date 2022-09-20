package org.glycomedb.export.glygen.util;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
        Integer t_counterMultiLineRecord = 0;
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
                String t_strAM = this.linearizeColumn(t_set.getString("am"));
                String t_strDB = this.linearizeColumn(t_set.getString("db"));
                String t_strAN = this.linearizeColumn(t_set.getString("an"));
                String t_strBS = t_set.getString("bs");
                String t_strMT = t_set.getString("mt");
                String t_strPA = t_set.getString("pa");
                String t_strPM = t_set.getString("pm");
                if (this.isExportableRecord(t_strBS) && this.isExportableRecord(t_strMT)
                        && this.isExportableRecord(t_strPA) && this.isExportableRecord(t_strPM))
                {
                    try
                    {
                        HashMap<BiologicalSourceType, String> t_bsField = this
                                .splitBSfield(t_strBS);
                        t_carbbankFile.write(t_set.getString("cc"), t_set.getString("ag"), t_strAM,
                                t_strAN, t_set.getString("au"), t_set.getString("ba"), t_strBS,
                                t_set.getString("ct"), t_set.getString("da"), t_strDB, t_strMT,
                                t_set.getString("nc"), t_set.getString("nt"), t_strPA, t_strPM,
                                t_set.getString("sb"), t_set.getString("sc"), t_set.getString("si"),
                                t_set.getString("st"), t_set.getString("ti"), t_set.getString("tn"),
                                t_glycomeDB, t_glyTouCan,
                                t_bsField.get(BiologicalSourceType.COMMON_NAME),
                                t_bsField.get(BiologicalSourceType.ORGAN_TYPE),
                                t_bsField.get(BiologicalSourceType.DISEASE),
                                t_bsField.get(BiologicalSourceType.LIFE_STAGE),
                                t_bsField.get(BiologicalSourceType.GENERAL_SPECIES),
                                t_bsField.get(BiologicalSourceType.GT),
                                t_bsField.get(BiologicalSourceType.SPECIES_CLASS),
                                t_bsField.get(BiologicalSourceType.STAR),
                                t_bsField.get(BiologicalSourceType.CELL_LINE),
                                t_bsField.get(BiologicalSourceType.SPECIES_KINGDOM),
                                t_bsField.get(BiologicalSourceType.SPECIES_DOMAIN),
                                t_bsField.get(BiologicalSourceType.BS),
                                t_bsField.get(BiologicalSourceType.F),
                                t_bsField.get(BiologicalSourceType.O));
                    }
                    catch (IOException e)
                    {
                        System.out.println(t_set.getString("cc") + ": " + e.getMessage());
                    }
                }
                else
                {
                    t_counterMultiLineRecord++;
                }
            }
        }
        t_carbbankFile.closeFile();
        System.out.println("\tRecord without GlycomeDB ID: " + t_counterNoGlycomeDB.toString());
        System.out.println(
                "\tNumber of structures without GlyTouCan ID: " + t_counterNull.toString());
        System.out.println(
                "\tNumber of structures with GlyTouCan Hash : " + t_counterHash.toString());
        System.out.println("\tNumber of structures with multiline columns : "
                + t_counterMultiLineRecord.toString() + "\n");
    }

    private String linearizeColumn(String a_string)
    {
        if (a_string == null)
        {
            return a_string;
        }
        String t_result = a_string.replaceAll("\\n", "|");
        t_result = t_result.replaceAll("\\r", "");
        return t_result;
    }

    private HashMap<BiologicalSourceType, String> splitBSfield(String a_strBS) throws IOException
    {
        HashMap<BiologicalSourceType, String> t_result = new HashMap<>();
        if (a_strBS == null)
        {
            return t_result;
        }
        String t_bs = a_strBS.trim();
        if (t_bs.length() != 0)
        {
            String[] t_parts = a_strBS.split("\\(");
            if (t_parts.length < 2)
            {
                throw new IOException("Unknown BS field pattern: " + a_strBS);
            }
            for (String t_subString : t_parts)
            {
                if (t_subString.trim().length() != 0)
                {
                    Integer t_index = t_subString.indexOf(")");
                    if (t_index == -1)
                    {
                        throw new IOException(
                                "Can not find closing parethesis in BS field pattern: " + a_strBS);
                    }
                    String t_type = t_subString.substring(0, t_index);
                    BiologicalSourceType t_enumType = BiologicalSourceType.forString(t_type);
                    if (t_enumType == null)
                    {
                        throw new IOException(
                                "Unknown biological source type (" + t_type + ") in: " + a_strBS);
                    }
                    if (t_result.get(t_enumType) != null)
                    {
                        throw new IOException(
                                "Dublicate biological source type (" + t_type + ") in: " + a_strBS);
                    }
                    String t_cleanedString = t_subString.substring(t_index + 1);
                    t_cleanedString = t_cleanedString.replaceAll(",", "");
                    t_result.put(t_enumType, t_cleanedString);
                }
            }
        }
        return t_result;
    }

    private boolean isExportableRecord(String a_string)
    {
        if (a_string == null)
        {
            return true;
        }
        String t_string = a_string.trim();
        if (t_string.length() == 0)
        {
            return true;
        }
        String[] t_parts = t_string.split("\\n");
        if (t_parts.length > 1)
        {
            return false;
        }
        return true;
    }

}
