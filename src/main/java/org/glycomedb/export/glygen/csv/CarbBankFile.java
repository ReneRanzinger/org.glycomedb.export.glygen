package org.glycomedb.export.glygen.csv;

import java.io.IOException;

public class CarbBankFile extends CSVFile
{
    public CarbBankFile(String a_fileNamePath) throws IOException
    {
        super();
        this.m_header = new String[] { "CC", "AG", "AM", "AN", "AU", "BA", "BS", "CT", "DA", "DB",
                "MT", "NC", "NT", "PA", "PM", "SB", "SC", "SI", "ST", "TI", "TN", "GlycomeDB ID",
                "GlyTouCan Acc", "BS-CN", "BS-OT", "BS-disease" };
        this.openFile(a_fileNamePath);
    }

    public void write(String a_recordID, String a_aglycon, String a_method, String a_names,
            String a_author, String a_function, String a_biologicalSource, String a_citation,
            String a_date, String a_crossref, String a_glycanType, String a_nc, String a_notes,
            String a_proteinAttachement, String a_proteinName, String a_curator,
            String a_structureCode, String a_cbank, String a_synthetic, String a_title,
            String a_gName, String a_glycomeDB, String a_glyTouCan, String a_bsCN, String a_bsOT,
            String a_bsDisease)
    {
        String[] t_line = new String[26];
        t_line[0] = this.addString(a_recordID);
        t_line[1] = this.addString(a_aglycon);
        t_line[2] = this.addString(a_method);
        t_line[3] = this.addString(a_names);
        t_line[4] = this.addString(a_author);
        t_line[5] = this.addString(a_function);
        t_line[6] = this.addString(a_biologicalSource);
        t_line[7] = this.addString(a_citation);
        t_line[8] = this.addString(a_date);
        t_line[9] = this.addString(a_crossref);
        t_line[10] = this.addString(a_glycanType);
        t_line[11] = this.addString(a_nc);
        t_line[12] = this.addString(a_notes);
        t_line[13] = this.addString(a_proteinAttachement);
        t_line[14] = this.addString(a_proteinName);
        t_line[15] = this.addString(a_curator);
        t_line[16] = this.addString(a_structureCode);
        t_line[17] = this.addString(a_cbank);
        t_line[18] = this.addString(a_synthetic);
        t_line[19] = this.addString(a_title);
        t_line[20] = this.addString(a_gName);
        t_line[21] = this.addString(a_glycomeDB);
        t_line[22] = this.addString(a_glyTouCan);
        t_line[23] = this.addString(a_bsCN);
        t_line[24] = this.addString(a_bsOT);
        t_line[25] = this.addString(a_bsDisease);
        this.m_csvWriter.writeNext(t_line);
    }

}
