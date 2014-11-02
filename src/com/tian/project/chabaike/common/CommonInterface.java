package com.tian.project.chabaike.common;

public class CommonInterface {
	public static final String URI_HOME_PAGE = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";
	public static final String URI_HEAD_LINE = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&page=%s&rows=%s";
	public static final String URI_HEAD_LINE_CONTENT = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=%s";
	public static final String URI_OTHER_CONTENT = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&page=%s&rows=%s&type=%s";
	public static final String URI_KEYWORD_SEARCH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle&page=%s&rows=%s&search=%s";
	public static final String URI_UPDATE = "http://192.168.1.100:8080/chabaike/update.xml";
	public static final String URI_APK_DOWNLOAD = "http://192.168.1.100:8080/chabaike/Android_Tian_Project_ChaBaiKe.apk";

}
