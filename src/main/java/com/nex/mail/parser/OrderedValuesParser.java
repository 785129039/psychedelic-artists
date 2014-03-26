package com.nex.mail.parser;


public abstract class OrderedValuesParser implements EmailValuesParser{

	
	private int order = 1;
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	@Override
	public int getOrder() {
		return order;
	}
	
	@Override
	public int compareTo(EmailValuesParser arg0) {
		return new Integer(order).compareTo(new Integer(arg0.getOrder()));
	}
}
