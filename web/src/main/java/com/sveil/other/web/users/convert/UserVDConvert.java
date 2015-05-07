package com.sveil.other.web.users.convert;

import org.springframework.util.Assert;

import com.sveil.other.web.users.model.User;
import com.sveil.other.web.users.model.UserJO;

public class UserVDConvert {
	public static UserJO vo2jo(User user) {
		Assert.notNull(user);

		UserJO userJO = new UserJO();
		userJO.setId(user.getId());
		userJO.setUsername(user.getUsername());
		userJO.setPasswd(user.getPasswd());
		return userJO;
	}

	public static User jo2vo(UserJO userJO) {
		Assert.notNull(userJO);

		User user = new User();
		user.setId(userJO.getId());
		user.setUsername(userJO.getUsername());
		user.setPasswd(userJO.getPasswd());
		return user;
	}

}
