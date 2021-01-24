package com.fundzforus.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fundzforus.server.dao.mybatis.NEWSMapper;
import com.fundzforus.server.domain.NewsReader;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.exception.NewsReaderNotFoundException;

@Component
public class NEWSReaderServiceImpl implements INEWSReaderService {

	@Autowired
	private NEWSMapper newsMapper;

	@Override
	public List<NewsReader> findAllNEWS() {
		return newsMapper.findAllNEWS();
	}

	@Override
	public NewsReader getNEWSById(int id) {
		Map map = new HashMap();
		map.put("id", id);
		return newsMapper.getNEWSById(map);
	}

	@Override
	public int createNews(NewsReader newsReader) {
		if (StringUtils.isBlank(newsReader.getTitle()) || StringUtils.isBlank(newsReader.getDescription())) {
			throw new MissingMandatoryFieldsException("NEWS title and  URL can not be Empty");
		} else {
			Map parameterMap = new HashMap();
			parameterMap.put("title", newsReader.getTitle());
			NewsReader dNewsReader = newsMapper.getNEWSByTitle(parameterMap);
			if (dNewsReader != null) {
				throw new NewsReaderNotFoundException(
						"NEWS title With Name ::" + newsReader.getTitle() + " already exist");
			}

			newsReader.setCreatedBy("MOBILE_APP");
			newsReader.setUpdatedBy("MOBILE_APP");
			return newsMapper.insertNEWS(newsReader);
		}
	}

	@Override
	public int updateNews(NewsReader newsReader) {
		if (StringUtils.isBlank(newsReader.getTitle()) || StringUtils.isBlank(newsReader.getDescription())) {
			throw new MissingMandatoryFieldsException("NEWS title and  URL can not be Empty");
		} else {
			Map parameterMap = new HashMap();
			parameterMap.put("title", newsReader.getTitle());
			NewsReader dbnewsReader = newsMapper.getNEWSByTitle(parameterMap);

			if (dbnewsReader == null) {
				throw new NewsReaderNotFoundException(
						"NEWS With title ::" + newsReader.getTitle() + " does not exist");
			}
			newsReader.setId(dbnewsReader.getId());
			newsReader.setCreatedBy("MOBILE_APP");
			newsReader.setUpdatedBy("MOBILE_APP");
			return newsMapper.updateNEWS(newsReader);
		}
	}

	@Override
	public int deleteNewsById(int id) {
		if (id == 0) {
			throw new MissingMandatoryFieldsException("NEWS Id can not be Empty or Zero");
		}

		Map parameterMap = new HashMap();
		parameterMap.put("id", id);
		NewsReader newsReader = newsMapper.getNEWSById(parameterMap);
		if (newsReader != null) {
			parameterMap.put("id", newsReader.getId());
			return newsMapper.deleteNEWSById(parameterMap);
		} else {
			throw new NewsReaderNotFoundException("NEWS with ID ::" + id + " does not exist");
		}
	}
}