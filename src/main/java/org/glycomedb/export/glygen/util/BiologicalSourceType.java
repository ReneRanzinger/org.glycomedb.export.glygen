package org.glycomedb.export.glygen.util;

public enum BiologicalSourceType
{
    COMMON_NAME("CN"), ORGAN_TYPE("OT"), DISEASE("disease"), LIFE_STAGE("LS"), GENERAL_SPECIES(
            "GS"), GT("GT"), CELL_LINE("cell line"), STAR("*"), SPECIES_CLASS(
                    "C"), SPECIES_KINGDOM("K"), SPECIES_DOMAIN("domain"), BS("BS"), F("F"), O("O");

    private String m_key;

    /** Private constructor, see the forName methods for external use. */
    private BiologicalSourceType(String a_key)
    {
        this.m_key = a_key;
    }

    public String getKey()
    {
        return this.m_key;
    }

    /**
     * Returns the appropriate object instance for the given key.
     */
    public static BiologicalSourceType forString(String a_key)
    {
        for (BiologicalSourceType a : BiologicalSourceType.values())
        {
            if (a_key.equalsIgnoreCase(a.m_key))
            {
                return a;
            }
        }
        return null;
    }

}
