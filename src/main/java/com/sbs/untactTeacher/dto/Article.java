package com.sbs.untactTeacher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public @AllArgsConstructor
@Data
class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
}