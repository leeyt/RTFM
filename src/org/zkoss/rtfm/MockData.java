package org.zkoss.rtfm;

import java.util.ArrayList;

import org.zkoss.rtfm.vo.Product;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;

public class MockData {
	public static DefaultTreeModel<Product> genTreeModel() {
		DefaultTreeNode<Product> root = new DefaultTreeNode<Product>(null, mockNode(0));
		DefaultTreeModel<Product> result = new DefaultTreeModel<Product>(root);
		return result;
	}
	
	private static ArrayList<DefaultTreeNode<Product>> mockNode(int level){	
		ArrayList<DefaultTreeNode<Product>> result = new ArrayList<DefaultTreeNode<Product>>();
		for(int i=0; i<3; i++){
			if(Math.random() < Math.pow(0.5, level)){
				DefaultTreeNode<Product> root = new DefaultTreeNode<Product>(Product.mock(), mockNode(level+1));
				result.add(root);
			}
		}
		return result;
	}
}
