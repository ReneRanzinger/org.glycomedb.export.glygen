package org.glycomedb.export.glygen.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.opencsv.CSVWriter;

public abstract class CSVFile
{
    protected String[] m_header = null;
    protected CSVWriter m_csvWriter = null;

    protected void openFile(String a_fileNamePath) throws IOException
    {
        // first create file object for file
        File t_file = new File(a_fileNamePath);

        // create FileWriter object with file as parameter
        FileWriter t_fileWriter = new FileWriter(t_file);

        // create CSVWriter with tab as separator
        this.m_csvWriter = new CSVWriter(t_fileWriter, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

        // write the header
        this.m_csvWriter.writeNext(this.m_header);
    }

    public void closeFile() throws IOException
    {
        this.m_csvWriter.flush();
        this.m_csvWriter.close();
    }

    protected String addString(String a_string)
    {
        if (a_string == null)
        {
            return "";
        }
        return a_string;
    }

    protected String addDate(Date a_creationTime)
    {
        if (a_creationTime == null)
        {
            return "";
        }
        SimpleDateFormat t_formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        return t_formatter.format(a_creationTime);
    }

    protected String addInteger(Integer a_integer)
    {
        if (a_integer == null)
        {
            return "";
        }
        return a_integer.toString();
    }

    protected String addLong(Long a_long)
    {
        if (a_long == null)
        {
            return "";
        }
        return a_long.toString();
    }
}
