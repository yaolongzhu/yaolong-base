package yaolong.base.inf;

/**
 * @author
 */
public class Audit implements java.io.Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 7407708995664941042L;

    private final Long id;

    private final Long tenantId;

    private final String name;

    public Audit(Long id, Long tenantId, String name) {
        this.id = id;
        this.tenantId = tenantId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public String getName() {
        return name;
    }

}
