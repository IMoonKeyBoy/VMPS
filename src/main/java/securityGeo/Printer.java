package securityGeo;

public class Printer {

	/**
	 * @return void
	 */
	public void printfshpinfo() {
		// TODO Auto-generated method stub

		for (int i = 0; i < MainApplication.myshpDatas.size()-500; i++) {

			//System.out.println("File:" + MainApplication.myshpDatas.get(i).getFile().getAbsolutePath());
			//System.out.println("Filetype:" + MainApplication.myshpDatas.get(i).getShptypeString());
			//
			System.out.print("Contents:" + MainApplication.myshpDatas.get(i).getShpcontentList());
			System.out.println("information:" + MainApplication.myshpDatas.get(i).information[3]);
		}
	}

	/**
	 * @return void
	 */
	public void printerInformation() {
		// TODO Auto-generated method stub
		int[] sum = new int[5];
		int[] counter = new int[5];
		for (int i = 0; i < counter.length; i++) {
			counter[i] = 0;
			sum[i] = 0;
		}
		for (int i = 0; i < MainApplication.myshpDatas.size(); i++) {

			if (MainApplication.myshpDatas.get(i).information[3] == 0) {
				counter[0]++;
				sum[0] =sum[0] +  MainApplication.myshpDatas.get(i).information[3];
			}
			if (MainApplication.myshpDatas.get(i).information[3] > 0
					&& MainApplication.myshpDatas.get(i).information[3] <= 100000) {
				counter[1]++;
				sum[1] =sum[1] +  MainApplication.myshpDatas.get(i).information[3];
			}
			if (MainApplication.myshpDatas.get(i).information[3] > 100000
					&& MainApplication.myshpDatas.get(i).information[3] <= 400000) {
				counter[2]++;
				sum[2] =sum[2] +  MainApplication.myshpDatas.get(i).information[3];
			}
			if (MainApplication.myshpDatas.get(i).information[3] > 400000
					&& MainApplication.myshpDatas.get(i).information[3] <= 700000) {
				counter[3]++;
				sum[3] =sum[3] +  MainApplication.myshpDatas.get(i).information[3];
			}
			if (MainApplication.myshpDatas.get(i).information[3] > 700000
					&& MainApplication.myshpDatas.get(i).information[3] <= 1000000) {
				counter[4]++;
				sum[4] =sum[4] +  MainApplication.myshpDatas.get(i).information[3];
			}
		}
		for (int i = 0; i < counter.length; i++) {
			System.out.println("COUNTER:" + counter[i]);
			
			System.out.println("sum: "  + sum[i]);
		}
		
	}
	/**
	 * @return void
	 */
	public void countmap() {
		// TODO Auto-generated method stub
		/*
		 * 
		 *  ~~267
			~~195
			~~138

		 * */
		int[] counter = new int[3];
		for (int i = 0; i < counter.length; i++) {
			counter[i] = 0;	
		}
		for (int i = 0; i < MainApplication.myshpDatas.size(); i++) {
			if (MainApplication.myshpDatas.get(i).shptypeString.equals("Point")) {
				counter[0]++;
			}
			if (MainApplication.myshpDatas.get(i).shptypeString.equals("MultiLineString")) {
				counter[1]++;
			}
			if (MainApplication.myshpDatas.get(i).shptypeString.equals("MultiPolygon")) {
				counter[2]++;
			}
		}
		for (int i = 0; i < counter.length; i++) {
			System.out.println("~~"+counter[i]);
		}
	}
}
