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
        if (users == null || users.isEmpty()){
            users = (users == null) ? new ArrayList<User>() : users;
            User user = new User();
            user.setFirstName("Morgan");
            user.setLastName("Freeman");
            user.setEmail("m.freeman@gmail.com");
            user.setUniversity("ITMO");
//            user.addCourse(courses.get(0));
//            user.addCourse(courses.get(1));
            userSvc.saveUser(user);
            users.add(user);
        }
		if(lectures == null || lectures.isEmpty())
		{
			lectures = (lectures == null) ? new ArrayList<Lecture>() : lectures;
			Lecture lecture = new Lecture();
			lecture.setClassroom("466");
			lecture.setTitle("Spring framework");
			lecture.setDate("2017-03-31 19:00");
			lecture.setHomework("None");
			lecture.setDescription("Spring lecture");
			lectureSvc.saveLecture(lecture);
			lectures.add(lecture);
		}
		if(courses == null || courses.isEmpty())
		{
			courses = (courses == null) ? new ArrayList<Course>() : courses;
			Course course = new Course();
			course.setTitle("Java Edu");
			courseSvc.saveCourse(course);
			courses.add(course);
//			Course course2 = new Course();
//			course2.setTitle("Some course");
//			courseSvc.saveCourse(course2);
//			courses.add(course2);
		}
		model.addAttribute("releaseInfo",  ReleaseInfo.getReleaseInfo());
		model.addAttribute("users", users);
		model.addAttribute("lectures", lectures);
		model.addAttribute("courses",courses);

		return "liferayportlet/view";
	}

}