package com.yunjing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.yunjing.util.Pagination;
@Service(value = "pageService")
public class PageService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Pagination queryForPage(String sql,int pn){
		Pagination page = new Pagination();
		// 设置要显示的页数
		page.setCurrentPage(pn);
				// 计算总记录数
		StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
		totalSQL.append(sql);
		totalSQL.append(" ) totalTable ");
				// 总记录数
		page.setTotalRows(jdbcTemplate.queryForInt(totalSQL.toString()));
				// 计算总页数
		page.setTotalPages();
				// 计算起始行数
		page.setStartIndex();
				// 计算结束行数
		page.setLastIndex();
		System.out.println("lastIndex=" + page.getLastIndex());//////////////////

		// 构造oracle数据库的分页语句
		/**
		 * StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		 * paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		 * paginationSQL.append(sql); paginationSQL.append(
		 * " ) temp where ROWNUM <= " + lastIndex); paginationSQL.append(
		 * " ) WHERE num > " + startIndex);
		 */

		// 装入结果集
		List<?> list = jdbcTemplate.queryForList(getMySQLPageSQL(sql, page.getStartIndex(), page.getNumPerPage()));
		page.setResultList(list);
		return page;
		
	}
	
	
	/**
	 * 构造MySQL数据分页SQL
	 * 
	 * @param queryString
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public String getMySQLPageSQL(String queryString, Integer startIndex, Integer pageSize) {
		String result = "";
		if (null != startIndex && null != pageSize) {
			result = queryString + " limit " + startIndex + "," + pageSize;
		} else if (null != startIndex && null == pageSize) {
			result = queryString + " limit " + startIndex;
		} else {
			result = queryString;
		}
		return result;
	}
	
	

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
