package com.fenghuo.test;

public class test {
public static void main(String[] args) {
	double Y=24.0;
	double X=0.1;
	int N=1;
	for(int i=1;i<=N;i++){
		Y=(21+i)*X+(1-X)*(Y+i);
		
	}
	int s=(int) Y;
	System.out.println(s+""+Y);
}
}
