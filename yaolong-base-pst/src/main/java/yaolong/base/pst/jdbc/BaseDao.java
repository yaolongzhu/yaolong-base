package yaolong.base.pst.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Luo Guoxiong
 *
 */
public abstract class BaseDao {

	protected JdbcTemplate template;

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

}
