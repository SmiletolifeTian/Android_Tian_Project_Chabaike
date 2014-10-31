package com.tian.project.chabaike.defaulthandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tian.project.chabaike.entity.Update;

public class UpdateHandler extends DefaultHandler {
	private static final int NAME = 1;
	private static final int MESSAGE = 2;
	private static final int VERSION = 3;
	private static final int VERSION_NAME = 4;
	private Update update;
	private int currentIndex = 0;
	
	public Update getUpdate(){
		return update;
	}
	
	@Override
	public void startDocument() throws SAXException {
		update = new Update();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if("name".equals(qName)){
			currentIndex = NAME;
		}else if("message".equals(qName)){
			currentIndex = MESSAGE;
		}else if("version".equals(qName)){
			currentIndex = VERSION;
		}else if("version_name".equals(qName)){
			currentIndex = VERSION_NAME;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String data = new String(ch,start,length);
		switch (currentIndex) {
		case NAME:
			update.setName(data);
			break;
		case MESSAGE:
			update.setMessage(data);
			break;
		case VERSION:
			update.setVersion(Integer.parseInt(data));
			break;
		case VERSION_NAME:
			update.setVersion_name(data);
			break;

		default:
			break;
		}
		currentIndex = 0;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
	}
	
	@Override
	public void endDocument() throws SAXException {
		
	}

}
