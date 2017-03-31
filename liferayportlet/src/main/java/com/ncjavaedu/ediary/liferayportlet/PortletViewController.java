/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.ncjavaedu.ediary.liferayportlet;

import com.liferay.portal.kernel.util.ReleaseInfo;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("VIEW")
public class PortletViewController {

	@Autowired
	private UserService service;

	@RenderMapping
	public String question(Model model) {
		List<User> users = service.getUsers();
		//TODO remove
		if (users == null || users.isEmpty()){
			users = (users == null) ? new ArrayList<User>() : users;
			User user = new User();
			user.setFirstName("Morgan");
			user.setLastName("Freeman");
			user.setEmail("m.freeman@gmail.com");
			service.saveUser(user);
			users.add(user);
		}
		model.addAttribute("releaseInfo",  ReleaseInfo.getReleaseInfo());
		model.addAttribute("users", users);

		return "liferayportlet/view";
	}

}