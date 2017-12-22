package me.cjd.sqlbuilder.support.jfinal;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import me.cjd.sqlbuilder.core.SqlBuilder;
import me.cjd.sqlbuilder.model.SqlBuilderPara;

/**
 * SqlBuilder 查询 封装实体
 * @author Mr.cjd
 * @date 2017年4月12日
 * @param <T>
 */
public abstract class SqlBuilderModel<T extends Model<T>> extends Model<T> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * SqlBuilder 列表查询 封装方法
	 * @param sqlId sql语句在.md文件里的ID
	 * @param paras 查询参数
	 * @return 实体列表
	 */
	public List<T> sFind(String sqlId, Object... sqlParas) {
		return super.find(SqlBuilder.render(sqlId), sqlParas);
	}
	
	/**
	 * SqlBuilder 首条查询 封装方法
	 * @param sqlId sql语句在.md文件里的ID
	 * @param sqlParas 查询参数
	 * @return 数据实体
	 */
	public T sFindFirst(String sqlId, Object... sqlParas) {
		return super.findFirst(SqlBuilder.render(sqlId), sqlParas);
	}
	
	/**
	 * SqlBuilder 分页查询 封装方法
	 * @param pageNumber 当前页数
	 * @param pageSize 每页条数
	 * @param sqlId sql语句在.md文件里的ID (只需要form的部分)
	 * @param sqlParas 查询参数
	 * @return jfinal Page 分页实体
	 */
	public Page<T> sPaginate(int pageNumber, int pageSize, String sqlId, Object... paras) {
		return this.sPaginate(pageNumber, pageSize, "select * ", sqlId, paras);
	}
	
	/**
	 * SqlBuilder 分页查询 封装方法
	 * @param pageNumber 当前页数
	 * @param pageSize 每页条数
	 * @param select sql语句select部分
	 * @param sqlId sql语句在.md文件里的ID (只需要form的部分)
	 * @param sqlParas 查询参数
	 * @return jfinal Page 分页实体
	 */
	public Page<T> sPaginate(int pageNumber, int pageSize, String select, String sqlId, Object... sqlParas) {
		return super.paginate(pageNumber, pageSize, select, SqlBuilder.render(sqlId), sqlParas);
	}
	
	/**
	 * 通过json数组串进行更新的方法
	 * @param sqlId sql语句在.md文件里的ID
	 * @param jsonArrayIds json数组存放id [1,2,3]
	 * @param sqlParas 查询参数
	 * @return 受影响记录数
	 * @example delete from user as u where u.id in ${ids} 
	 */
	public int sJsonUpdate(String sqlId, String jsonArrayIds, Object... sqlParas){
		return Db.update(SqlBuilder.render(sqlId
				, new SqlBuilderPara("ids", jsonArrayIds.replaceAll("\\[(.*)\\]", "($1)")))
				, sqlParas);
	}
	
}
