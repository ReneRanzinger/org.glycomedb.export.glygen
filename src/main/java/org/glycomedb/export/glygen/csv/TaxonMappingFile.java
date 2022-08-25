package org.glycomedb.export.glygen.csv;

import java.io.IOException;

import org.glycomedb.export.glygen.persist.om.TaxonMapping;

public class TaxonMappingFile extends CSVFile
{

    public TaxonMappingFile(String a_fileNamePath) throws IOException
    {
        super();
        this.m_header = new String[] { "id", "glytoucan", "taxon", "resource", "resource_id" };
        this.openFile(a_fileNamePath);
    }

    public void write(TaxonMapping a_entry)
    {
        String[] t_line = new String[5];
        t_line[0] = this.addInteger(a_entry.getId());
        t_line[1] = this.addString(a_entry.getGlyTouCanId());
        t_line[2] = this.addInteger(a_entry.getTaxon());
        t_line[3] = this.addString(a_entry.getResource());
        t_line[4] = this.addString(a_entry.getResourceId());
        this.m_csvWriter.writeNext(t_line);
    }

}
