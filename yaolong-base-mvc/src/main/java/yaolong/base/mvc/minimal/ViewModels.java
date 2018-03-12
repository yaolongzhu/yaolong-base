package yaolong.base.mvc.minimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yaolong.base.common.util.JsonUtils;

/**
 *
 * @author
 *
 */
public class ViewModels {

	List<ViewModel> datas = new ArrayList<ViewModel>();

	public List<ViewModel> getDatas() {
		return datas;
	}

	public void setData(String data) {
		List<Map<String, Object>> list = JsonUtils.parseJsonList(data);
		for (Map<String, Object> map : list) {
			ViewModel model = new ViewModel(map);
			datas.add(model);
		}
	}

	public static void main(String[] args) {
		ViewModels m = new ViewModels();
		// m.setDatas("[{\"address\":
		// \"address2\",\"name\":\"haha2\",\"email\":\"email2\"},{\"address\":\"address\",\"name\":\"haha\",\"email\":\"email\"}]");
		m.setData(
				"[{\"address\":\"address2\",\"name\":\"haha2\",\"email\":\"email2\"},{\"address\":\"address\",\"name\":\"haha\",\"email\":\"email\"}]");
	}

}
