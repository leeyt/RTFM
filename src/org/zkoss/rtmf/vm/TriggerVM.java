package org.zkoss.rtmf.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.rtmf.vo.Product;

public class TriggerVM {
	private Product[] child;
	private int index = 1;
	
	public TriggerVM() {
		child = new Product[5];
		for (int i = 0; i < child.length; i++) {
			Product vo = new Product();
			vo.setName("name "+i);
			child[i] = vo;
		}
	}
	
	public Product[] getChild() {
		return child;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int value) {
		this.index = value;
		System.out.println("VM "+value);//Delete
	}

	@Command
	@NotifyChange("*")
	public void goto0() {
		setIndex(0);
	}
}
