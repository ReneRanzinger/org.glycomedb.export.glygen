package org.glycomedb.export.glygen.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.glycomedb.export.glygen.persist.om.StructureMapping;
import org.glycomedb.export.glygen.persist.om.TaxonMapping;

public class DBInterface
{
    protected Connection m_connection;

    public DBInterface() throws ClassNotFoundException, SQLException
    {

        Class.forName("org.postgresql.Driver");

        String t_strURL = "jdbc:postgresql://127.0.0.1:5432/glycomedb";
        // open database connection
        this.m_connection = DriverManager.getConnection(t_strURL, "postgres", "postgres");
    }

    public List<StructureMapping> getGlyTouCanMapping() throws SQLException
    {
        List<StructureMapping> t_result = new ArrayList<>();
        String t_query = "SELECT r1.structure_id as structure_id, r1.glyco_ct as glyco_ct, r2.glytoucan_id as glytoucan_id FROM core.structure as r1 LEFT JOIN glygen.mapping as r2 ON r1.structure_id=r2.structure_id ORDER BY r1.structure_id ASC;";
        ResultSet t_set = this.m_connection.createStatement().executeQuery(t_query);
        while (t_set.next())
        {
            StructureMapping t_mapping = new StructureMapping();
            t_mapping.setId(t_set.getInt("structure_id"));
            t_mapping.setGlycoCt(t_set.getString("glyco_ct"));
            t_mapping.setGlyTouCanId(t_set.getString("glytoucan_id"));
            t_result.add(t_mapping);
        }
        t_set.close();
        return t_result;
    }

    public List<TaxonMapping> getTaxonMapping() throws SQLException
    {
        List<TaxonMapping> t_result = new ArrayList<>();
        String t_query = "SELECT r5.*, r6.glytoucan_id FROM ("
                + "SELECT r3.*, r4.structure_id FROM ("
                + "SELECT r2.*, r1.taxon_id FROM remote_two.remote_structure_has_taxon as r1 "
                + "    LEFT JOIN remote_two.remote_structure as r2 ON r1.remote_structure_id=r2.remote_structure_id ) as r3 "
                + "    LEFT JOIN remote_two.remote_structure_has_structure as r4 ON r4.remote_structure_id=r3.remote_structure_id) as r5"
                + "    LEFT JOIN glygen.mapping as r6 ON r5.structure_id=r6.structure_id";
        ResultSet t_set = this.m_connection.createStatement().executeQuery(t_query);
        while (t_set.next())
        {
            TaxonMapping t_mapping = new TaxonMapping();
            t_mapping.setId(t_set.getInt("structure_id"));
            t_mapping.setResource(t_set.getString("resource"));
            t_mapping.setResourceId(t_set.getString("resource_id"));
            t_mapping.setTaxon(t_set.getInt("taxon_id"));
            t_mapping.setGlyTouCanId(t_set.getString("glytoucan_id"));
            t_result.add(t_mapping);
        }
        t_set.close();
        return t_result;
    }

    public ResultSet getCarBankData() throws SQLException
    {
        String t_query = "SELECT r5.*, r6.glytoucan_id FROM "
                + "(SELECT r3.*, r4.structure_id FROM " + "(SELECT * FROM "
                + "(SELECT * FROM remote_two.remote_structure WHERE resource='carbbank') as r1 "
                + "LEFT JOIN raw_carbbank.structures as r2 ON r1.resource_id=r2.cc ) as r3 "
                + "LEFT JOIN remote_two.remote_structure_has_structure as r4 ON r4.remote_structure_id=r3.remote_structure_id ) as r5 "
                + "LEFT JOIN glygen.mapping as r6 ON r5.structure_id=r6.structure_id";
        ResultSet t_set = this.m_connection.createStatement().executeQuery(t_query);
        return t_set;
    }
}
