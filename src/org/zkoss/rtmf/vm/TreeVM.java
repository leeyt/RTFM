package org.zkoss.rtmf.vm;

import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.rtmf.MockData;
import org.zkoss.rtmf.vo.Product;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;

public class TreeVM{
	private DefaultTreeNode<Product> selectedProduct;
	
	public DefaultTreeNode<Product> getSelectedProduct() {
		return selectedProduct;
	}

	@NotifyChange("selectedProduct")
	public void setSelectedProduct(DefaultTreeNode<Product> selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public DefaultTreeModel<Product> getTreeModel(){
		DefaultTreeModel<Product> result = MockData.genTreeModel();
		int[] path = {0};
		result.addOpenPath(path);
		result.addSelectionPath(path);		
		return result;
	}
}
