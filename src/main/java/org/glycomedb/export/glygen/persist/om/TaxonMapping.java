package org.glycomedb.export.glygen.persist.om;

public class TaxonMapping
{
    private String m_resource = null;
    private String m_resourceId = null;
    private Integer m_taxon = null;
    private String m_glyTouCanId = null;
    private Integer m_id = null;

    public String getResource()
    {
        return this.m_resource;
    }

    public void setResource(String a_resource)
    {
        this.m_resource = a_resource;
    }

    public String getResourceId()
    {
        return this.m_resourceId;
    }

    public void setResourceId(String a_resourceId)
    {
        this.m_resourceId = a_resourceId;
    }

    public Integer getTaxon()
    {
        return this.m_taxon;
    }

    public void setTaxon(Integer a_taxon)
    {
        this.m_taxon = a_taxon;
    }

    public String getGlyTouCanId()
    {
        return this.m_glyTouCanId;
    }

    public void setGlyTouCanId(String a_glyTouCanId)
    {
        this.m_glyTouCanId = a_glyTouCanId;
    }

    public Integer getId()
    {
        return this.m_id;
    }

    public void setId(Integer a_id)
    {
        this.m_id = a_id;
    }
}
