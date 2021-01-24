package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.NewsReader;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NEWSMapper {

	List<NewsReader> findAllNEWS();

	NewsReader getNEWSById(Map<String, String> map);

	int insertNEWS(NewsReader news);

	int updateNEWS(NewsReader news);

	int deleteNEWSById(Map<String, String> map);
	
	NewsReader getNEWSByTitle(Map<String, String> map);
}
