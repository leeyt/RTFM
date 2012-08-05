package org.zkoss.rtmf.vm;

import java.util.ArrayList;
import java.util.HashMap;

import org.zkoss.rtmf.vo.Product;

public class DynamicGridVM {
	public ArrayList<ArrayList<String>> getData() {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		ArrayList<Product> productList = MockDAO.getProduct();
		for(Product p : productList){
			ArrayList<String> tmpResult = new ArrayList<String>();
			tmpResult.add(p.getName());
			tmpResult.add(p.getCategory());
			
			ArrayList<String> marketList = MockDAO.getMarketBy(p);
			for(String s : marketList){
				tmpResult.add(s);
			}
			result.add(tmpResult);
		}
		return result;
	}
}

class MockDAO{
	private static ArrayList<Product> products = new ArrayList<Product>();
	static{
		for(int i=0; i<4; i++){
			Product p = new Product();
			p.setId(""+i);
			p.setCategory("category "+i);
			p.setName("name "+i);
			products.add(p);
		}
	}
	
	private static HashMap<Product, ArrayList<String>> marketData = new HashMap<Product, ArrayList<String>>();
	static{
		for(int i=0; i<products.size(); i++){
			ArrayList<String> data = new ArrayList<String>();
			for(int j=0; j<i; j++){
				data.add("market "+i);
			}
			marketData.put(products.get(i), data);
		}
	}
	
	public static ArrayList<Product> getProduct(){
		return products;
	}
	
	public static ArrayList<String> getMarketBy(Product p){
		return marketData.get(p);
	}
}