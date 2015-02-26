package com.example.mahjoon;

import java.io.Serializable;

public class TableData implements Serializable{
	private String[] name;
	private int n,maxn;
	private int[][] change;
	private int[] points;
	private boolean finish;
	public TableData(){
		name=new String[4];
		finish=false;
		maxn=5;
		n=0;
		points=new int[4];
		for (int i=0;i<4;i++) points[i]=25000;
		change=new int[maxn][4];
	}
	public void addInfo(int d[]){
		for (int i=0;i<4;i++) {
			change[n][i]=d[i];
			points[i]=points[i]+d[i];
		}
		n++;
		if (n==maxn) {
			int [][] temp=new int[maxn*2][4];
			for (int i=0;i<n;i++) for (int j=0;j<4;j++)
				temp[i][j]=change[i][j];
			change=temp;
			maxn*=2;
		}
		/*
		if (win!=now){
			now++;
			if (now==4){
				now=0;time++;
				if (time==T) fin();
			}
		}*/
	}
	public String[] getName(){
		return name;
	}
	public int[][] getChange(){
		return change;
	}
	public int[] getPoints(){
		return points;
	}
	public int getLineNum(){
		return n;
	}
	public void changeValue(int i,int j,int value){
		int old = change[i][j];
		points[j]=points[j]-old+value;
		change[i][j]=value;
	}
	public boolean isfin(){
		return finish;
	}
	public void setName(String s,String name){
		if (s=="east") this.name[0]=name; else
		if (s=="south") this.name[1]=name; else
		if (s=="west") this.name[2]=name; else
		if (s=="north") this.name[3]=name;
	}
}