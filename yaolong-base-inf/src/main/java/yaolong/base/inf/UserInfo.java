package yaolong.base.inf;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface UserInfo {

    /**
     * 获取用户ID
     * 
     * @return
     */
    public Long getUserId();

    /**
     * 获取租户ID
     * 
     * @return
     */
    public Long getTenantId();

    /**
     * 获取版本
     * 
     * @return
     */
    public String getVersion();

    /**
     * 获取用户帐号
     * 
     * @return
     */
    public String getLoginId();

    /**
     * 获取用户姓名
     * 
     * @return
     */
    public String getUserName();

    /**
     * 获取用户主组织信息
     * 
     * @return
     */
    public Map<Long, String> getOrg();

    /**
     * 获取用户职位信息
     * 
     * @return
     */
    public Map<Long, String> getPosition();

    /**
     * 获取用户拥有的菜单角色
     * 
     * @return
     */
    public List<Long> getMenuRoleIds();

    /**
     * 获取用户拥有的数据角色
     * 
     * @return
     */
    public List<Long> getDataRoleIds();

    /**
     * 获取项目自定义用户属性
     * 
     * @param key
     * @return
     */
    public Object getExtension(String key);

}
