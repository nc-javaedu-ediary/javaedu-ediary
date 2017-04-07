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
import com.ncjavaedu.ediary.model.Course;
import com.ncjavaedu.ediary.model.Lecture;
import com.ncjavaedu.ediary.model.User;
import com.ncjavaedu.ediary.services.CourseService;
import com.ncjavaedu.ediary.services.LectureService;
import com.ncjavaedu.ediary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
@RequestMapping("VIEW")
public class PortletViewController {

	@Autowired
	private UserService userSvc;
	@Autowired
	private LectureService lectureSvc;
	@Autowired
	private CourseService courseSvc;

	@RenderMapping
	public String question(Model model) {
		List<User> users = userSvc.getUsers();
		List<Lecture> lectures = lectureSvc.getLectures();
		List<Course> courses = courseSvc.getCourses();

        //TODO remove
		users = (users == null) ? new ArrayList<User>() : users;
		courses = (courses == null) ? new ArrayList<Course>() : courses;
		lectures = (lectures == null) ? new ArrayList<Lecture>() : lectures;

        Lecture l1 = new Lecture();
        l1.setClassroom("466");
        l1.setTitle("Hibernate");
        l1.setDescription("Hibernate");
        l1.setDate("31-03-2017 19:00");
        l1.setHomework("None");

        Lecture l2 = new Lecture();
        l2.setClassroom("403");
        l2.setTitle("PL/SQL");
        l2.setDescription("PL/SQL");
        l2.setDate("07-04-2017 19:00");
        l2.setHomework("None");

        Lecture l3 = new Lecture();
        l3.setClassroom("466");
        l3.setTitle("Maven");
        l3.setDescription("Maven");
        l3.setDate("14-04-2017 19:00");
        l3.setHomework("None");

		double rnd = Math.random();
		User user = new User();
		user.setFirstName("Morgan " + rnd);
		user.setLastName("Freeman");
		user.setEmail("m.freeman@gmail.com");
		user.setUniversity("ITMO");

		Course c = new Course();
		c.setTitle("Java Random " + rnd);
        Course c2 = new Course();
        c2.setTitle("Java Edu");

        c2.addLecture(l1);
        c2.addLecture(l2);
        c2.addLecture(l3);
		user.addCourse(c);
		user.addCourse(c2);
        userSvc.saveUser(user);

        users = userSvc.getUsers();
        lectures = lectureSvc.getLectures();
        courses = courseSvc.getCourses();

		model.addAttribute("releaseInfo",  ReleaseInfo.getReleaseInfo());
		model.addAttribute("users", users);
		model.addAttribute("lectures", lectures);
		model.addAttribute("courses",courses);

		return "liferayportlet/view";
	}

}