package yaolong.base.inf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yaolong.base.inf.extension.UserInfoExtension;

/**
 *
 * @author
 *
 */
public class User implements UserInfoExtension, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5303039329194582791L;

    private Long userId;

    private Long tenantId;

    private String version;

    private String loginId;

    private String userName;

    private Map<Long, String> org = new HashMap<Long, String>();

    private Map<Long, String> position = new HashMap<Long, String>();

    private List<Long> menuRoleIds = new ArrayList<Long>();

    private List<Long> dataRoleIds = new ArrayList<Long>();

    private Map<String, Object> extension = new HashMap<String, Object>();

    public User(String loginId) {
        this.loginId = loginId;
    }

    public User(Long userId, String loginId) {
        this.userId = userId;
        this.loginId = loginId;
    }

    public User(Long userId, String loginId, String userName) {
        this.userId = userId;
        this.loginId = loginId;
        this.userName = userName;
    }

    public User(Long userId, String loginId, String userName, boolean isAdmin) {
        this.userId = userId;
        this.loginId = loginId;
        this.userName = userName;
    }

    public User(Long userId, Long tenantId, String version, String loginId, String userName) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.version = version;
        this.loginId = loginId;
        this.userName = userName;
    }

    public User(Long userId, Long tenantId, String version, String loginId, String userName, Map<Long, String> org,
            Map<Long, String> position, List<Long> menuRoleIds, List<Long> dataRoleIds, Map<String, Object> extension) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.version = version;
        this.loginId = loginId;
        this.userName = userName;
        this.org = org;
        this.position = position;
        this.menuRoleIds = menuRoleIds;
        this.dataRoleIds = dataRoleIds;
        this.extension = extension;
    }

    public User(Long userId, Long tenantId, String version, String loginId, String userName, boolean isAdmin,
            Map<Long, String> org, Map<Long, String> position, List<Long> menuRoleIds, List<Long> dataRoleIds,
            Map<String, Object> extension) {
        super();
        this.userId = userId;
        this.tenantId = tenantId;
        this.version = version;
        this.loginId = loginId;
        this.userName = userName;
        this.org = org;
        this.position = position;
        this.menuRoleIds = menuRoleIds;
        this.dataRoleIds = dataRoleIds;
        this.extension = extension;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public Long getTenantId() {
        return tenantId;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getLoginId() {
        return loginId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public Map<Long, String> getOrg() {
        return org;
    }

    @Override
    public Map<Long, String> getPosition() {
        return position;
    }

    @Override
    public List<Long> getMenuRoleIds() {
        return menuRoleIds;
    }

    @Override
    public List<Long> getDataRoleIds() {
        return dataRoleIds;
    }

    @Override
    public Object getExtension(String key) {
        return extension.get(key);
    }

    @Override
    public Object putExtension(String key, Object value) {
        return extension.put(key, value);
    }

    @Override
    public Object removeExtension(String key) {
        return extension.remove(key);
    }

    public Map<String, Object> getExtension() {
        return extension;
    }

    public void setExtension(Map<String, Object> extension) {
        this.extension = extension;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOrg(Map<Long, String> org) {
        this.org = org;
    }

    public void setOrg(Long id, String orgName) {
        this.org.put(id, orgName);
    }

    public void setPosition(Map<Long, String> position) {
        this.position = position;
    }

    public void setPosition(Long id, String positionName) {
        this.position.put(id, positionName);
    }

    public void setMenuRoleIds(List<Long> menuRoleIds) {
        this.menuRoleIds = menuRoleIds;
    }

    public void setMenuRoleId(Long menuRoleId) {
        menuRoleIds.add(menuRoleId);
    }

    public void setDataRoleIds(List<Long> dataRoleIds) {
        this.dataRoleIds = dataRoleIds;
    }

    public void setDataRoleId(Long dataRoleId) {
        dataRoleIds.add(dataRoleId);
    }

}