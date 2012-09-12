package org.zkoss.rtfm.composite;

import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zul.Textbox;

@SuppressWarnings("serial")
public class Email extends Textbox implements IdSpace{
	private String category;
	private String element;
	
	public Email() {
		System.out.println("constructor");
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		System.out.println("set category : "+category);
		this.category = category;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		System.out.println("set element : "+element);
		this.element = element;
	}
}
