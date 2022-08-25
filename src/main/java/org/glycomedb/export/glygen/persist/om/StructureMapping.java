package org.glycomedb.export.glygen.persist.om;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StructureMapping
{
    private Integer m_id = null;
    private String m_glycoCt = null;
    private String m_glyTouCanId = null;

    @JsonProperty("id")
    public Integer getId()
    {
        return this.m_id;
    }

    public void setId(Integer a_id)
    {
        this.m_id = a_id;
    }

    @JsonProperty("glycoct")
    public String getGlycoCt()
    {
        return this.m_glycoCt;
    }

    public void setGlycoCt(String a_glycoCt)
    {
        this.m_glycoCt = a_glycoCt;
    }

    @JsonProperty("glytoucan")
    public String getGlyTouCanId()
    {
        return this.m_glyTouCanId;
    }

    public void setGlyTouCanId(String a_glyTouCanId)
    {
        this.m_glyTouCanId = a_glyTouCanId;
    }
}
