package org.glycomedb.export.glygen.util;

public enum BiologicalSourceType
{
    COMMON_NAME("CN"), ORGAN_TYPE("OT"), DISEASE("disease");

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
