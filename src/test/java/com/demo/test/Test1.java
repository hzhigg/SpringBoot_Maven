package com.demo.test;

public class Test1 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		long start=System.currentTimeMillis();
		long end=System.currentTimeMillis();
		while(!((end-start)/1000==15)){
			end=System.currentTimeMillis();
		}
		System.out.println("退出："+(end-start)/1000);
	}

}
