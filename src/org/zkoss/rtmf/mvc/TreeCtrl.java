package org.zkoss.rtmf.mvc;

import org.zkoss.rtmf.MockData;
import org.zkoss.rtmf.vo.Product;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

@SuppressWarnings("serial")
public class TreeCtrl extends GenericForwardComposer<Div>{
	private Tree treeMVC;
	
	@Override
	public void doAfterCompose(Div div) throws Exception{
		super.doAfterCompose(div);
		treeMVC.setModel(getTreeModel());
		treeMVC.setItemRenderer(new TreeRenderer());
	}
	
	public DefaultTreeModel<Product> getTreeModel(){
		DefaultTreeModel<Product> result = MockData.genTreeModel();
		int[] path = {0};
		result.addOpenPath(path);
		result.addSelectionPath(path);		
		return result;
	}
}

class TreeRenderer implements TreeitemRenderer<DefaultTreeNode<Product>>{
	public void render(Treeitem item, DefaultTreeNode<Product> obj, int index) throws Exception {
		Product data = obj.getData();
		Treerow tr = new Treerow();
		item.appendChild(tr);
		tr.appendChild(new Treecell(data.getId()));
		tr.appendChild(new Treecell(data.getName()));
		tr.appendChild(new Treecell(data.getCategory()));
	}	
}