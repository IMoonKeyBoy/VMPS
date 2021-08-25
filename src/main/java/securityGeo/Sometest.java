
package securityGeo;

public class Sometest {
	public static void main(String[] args) {

		float sum_unsec = 11783110;
		float sum_sec = 66016510;
		float sumsum = 77799620;
		
		/*
		for (int i = 1; i < 11; i++) {
		
			for (int j = 1; j < 11; j++) {
				
				float result = (float) (0.6 * i * 0.1 * j * 0.1 * sum_sec / sumsum - 0.4 * ((j * 0.1 * sum_sec+sum_unsec)/sumsum)*((j * 0.1 * sum_sec+sum_unsec)/sumsum));
				System.out.println(i+" " + j +" " + result);
			}
			
		}*/
		
		
		for (int i = 1; i < 11; i++) {
			
			for (int j = 1; j < 11; j++) {
				
				float result = (float) (0.6 * i * 0.1 * j * 0.1 * sum_sec / sumsum - 0.4 * ((j * 0.1 * sum_sec+sum_unsec)/sumsum)*((j * 0.1 * sum_sec+sum_unsec)/sumsum));
				System.out.println(i+" " + j +" " + result);
			}
		}
	}
}
