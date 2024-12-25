package com.product.demo.exception.handler;

public class ProductDeletedException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	
	public ProductDeletedException(String mess) {
		super(mess);
	}

}
