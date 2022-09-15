package org.glycomedb.export.glygen.csv;

import java.io.IOException;

public class CarbBankFile extends CSVFile
{
    public CarbBankFile(String a_fileNamePath) throws IOException
    {
        super();
        this.m_header = new String[] { "CC", "AG", "AM", "AN", "AU", "BA", "BS", "CT", "DA", "DB",
                "MT", "NC", "NT", "PA", "PM", "SB", "SC", "SI", "ST", "TI", "TN", "GlycomeDB ID",
                "GlyTouCan Acc", "BS-CN", "BS-OT", "BS-disease", "BS-LS", "BS-GS", "BS-GT", "BS-C",
                "BS-*", "BS-cell line", "BS-K", "BS-domain", "BS-BS", "BS-F", "BS-O" };
        this.openFile(a_fileNamePath);
    }

    public void write(String a_recordID, String a_aglycon, String a_method, String a_names,
            String a_author, String a_function, String a_biologicalSource, String a_citation,
            String a_date, String a_crossref, String a_glycanType, String a_nc, String a_notes,
            String a_proteinAttachement, String a_proteinName, String a_curator,
            String a_structureCode, String a_cbank, String a_synthetic, String a_title,
            String a_gName, String a_glycomeDB, String a_glyTouCan, String a_bsCN, String a_bsOT,
            String a_bsDisease, String a_bsLS, String a_bsGS, String a_bsGT, String a_bsC,
            String a_bsStar, String a_bsCellLine, String a_bsK, String a_bsDomain, String a_bsBS,
            String a_bsF, String a_bsO)
    {
        String[] t_line = new String[37];
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
        t_line[26] = this.addString(a_bsLS);
        t_line[27] = this.addString(a_bsGS);
        t_line[28] = this.addString(a_bsGT);
        t_line[29] = this.addString(a_bsC);
        t_line[30] = this.addString(a_bsStar);
        t_line[31] = this.addString(a_bsCellLine);
        t_line[32] = this.addString(a_bsK);
        t_line[33] = this.addString(a_bsDomain);
        t_line[34] = this.addString(a_bsBS);
        t_line[35] = this.addString(a_bsF);
        t_line[36] = this.addString(a_bsO);
        this.m_csvWriter.writeNext(t_line);
    }

}
