package yaolong.base.ar.log;

/**
*
* @author
*
*/
public interface ILogFactory {
	
	Log getLog(Class<?> clazz);
	
	Log getLog(String name);
}


