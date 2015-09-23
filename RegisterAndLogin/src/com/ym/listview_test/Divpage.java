package com.ym.listview_test;

public class Divpage {

		private static int pageSize = 20;  
		private int currentPage;
		private int totalPage;		
		private int totalData;		
		private int firstPage;		
		private int endPage;		
		private int fromIndex;      
		private int toIndex;		
		
		public static Divpage getPage(int currentPage,int Size){
			Divpage page = new Divpage();
			int totalPage = 0;
			int fromPage = 0;
			int toPage = 0;
			
			if(Size>0){
				if(Size%pageSize==0){
					totalPage = Size/pageSize;
				}else{
					totalPage = Size/pageSize+1;
				}
				if(currentPage<=0){
					currentPage = 1;
				}else if(currentPage>=totalPage){
					currentPage = totalPage;
				}
				fromPage = (currentPage-1)*pageSize;
				toPage = fromPage+pageSize;
				if(toPage>Size){
					toPage = Size;
				}
			}else{
				currentPage = 0;
			}
			 
			page.setCurrentPage(currentPage);
			page.setTotalPage(totalPage);
			page.setTotalData(Size);
			page.setFromIndex(fromPage);
			page.setToIndex(toPage);
			return page;
		}
		public int getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public int getTotalData() {
			return totalData;
		}
		public void setTotalData(int totalData) {
			this.totalData = totalData;
		}
		public int getFromIndex() {
			return fromIndex;
		}
		public void setFromIndex(int fromIndex) {
			this.fromIndex = fromIndex;
		}
		public int getToIndex() {
			return toIndex;
		}
		public void setToIndex(int toIndex) {
			this.toIndex = toIndex;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public int getCurrentPage() {
			return currentPage;
		}
		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}
		public int getFirstPage() {
			return firstPage;
		}
		public void setFirstPage(int firstPage) {
			this.firstPage = firstPage;
		}
		public int getEndPage() {
			return endPage;
		}
		public void setEndPage(int endPage) {
			this.endPage = endPage;
		}
	

}
