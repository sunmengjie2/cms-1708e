package com.sunmengjie.cms.service;

import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.entity.Link;

public interface LinkService {

	PageInfo<Link> getLink(int pageNum);

}
